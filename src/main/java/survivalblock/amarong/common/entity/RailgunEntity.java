package survivalblock.amarong.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongParticleTypes;
import survivalblock.amarong.common.init.AmarongSounds;

import java.util.*;
import java.util.List;

public class RailgunEntity extends Entity implements Ownable {
    public static final int WARNING_TICKS = 4 * 20;
    public static final int MAX_ITERATIONS = 384;
    @Nullable
    private UUID ownerUuid;
    @Nullable
    private Entity owner;

    public RailgunEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public RailgunEntity(World world, PlayerEntity owner) {
        this(AmarongEntityTypes.RAILGUN, world);
        this.setPosition(owner.getEyePos());
        this.setYaw(owner.getHeadYaw());
        this.setPitch(owner.getPitch());
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.owner = entity;
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else {
            if (this.ownerUuid != null) {
                World var2 = this.getWorld();
                if (var2 instanceof ServerWorld serverWorld) {
                    this.owner = serverWorld.getEntity(this.ownerUuid);
                    return this.owner;
                }
            }

            return null;
        }
    }


    @Override
    public void tick() {
        World world = this.getWorld();
        if (this.age > WARNING_TICKS + 10 && !world.isClient()) {
            this.discard();
            return;
        }
        if (this.age == 4) {
            raycastParticle((world1, encountered, pitchYaw, currentRaycastPosition, x, y, z, iterations) -> {
                if (world1 instanceof ServerWorld serverWorld) {
                    List<ServerPlayerEntity> players = serverWorld.getPlayers();
                    for (ServerPlayerEntity player : players) {
                        serverWorld.spawnParticles(player, ParticleTypes.END_ROD, true, x, y, z, 1, 0, 0, 0, 0);
                        if (player.getPos().squaredDistanceTo(currentRaycastPosition) < 25 && !encountered.contains(player)) {
                            Entity owner = this.getOwner();
                            RegistryEntry<SoundEvent> registryEntry = Registries.SOUND_EVENT.getEntry(AmarongSounds.RAILGUN_CHARGES);
                            player.networkHandler.sendPacket(new PlaySoundFromEntityS2CPacket(registryEntry, owner == null ? this.getSoundCategory() : owner.getSoundCategory(), player, 1.0F, 1.0F, this.getRandom().nextLong()));
                            encountered.add(player);
                        }
                    }
                }
            });
        } else if (this.age >= WARNING_TICKS && !world.isClient()) {
            List<Entity> entities = new ArrayList<>();
            raycastParticle((world1, encountered, pitchYaw, currentRaycastPosition, x, y, z, iterations) -> {
                if (world1 instanceof ServerWorld serverWorld) {
                    List<ServerPlayerEntity> players = serverWorld.getPlayers();
                    for (ServerPlayerEntity player : players) {
                        serverWorld.spawnParticles(player, AmarongParticleTypes.RAILGUN_PARTICLE, true, x, y, z, 1, 0.1, 0.1, 0.1, 0);
                    }
                    final double boxRadius = 0.5;
                    Vec3d lowerCorner = currentRaycastPosition.subtract(boxRadius, boxRadius, boxRadius);
                    Vec3d upperCorner = currentRaycastPosition.add(boxRadius, boxRadius, boxRadius);
                    Box box = new Box(lowerCorner, upperCorner);
                    box.expand(2);
                    AmarongUtil.getEntitiesWithScaling(serverWorld, entity -> AmarongUtil.getProperlyScaledBox(entity).intersects(box), entities);
                }
            });
            DamageSource source;
            Entity owner = this.getOwner();
            if (owner instanceof LivingEntity living) {
                source = AmarongDamageTypes.create(AmarongDamageTypes.RAILGUN_HIT, this, living);
            } else {
                source = AmarongDamageTypes.create(AmarongDamageTypes.RAILGUN_HIT, this);
            }
            //noinspection RedundantCollectionOperation
            if (owner != null && entities.contains(owner)) {
                entities.remove(owner);
            }
            entities.forEach(entity -> entity.damage(source, entity instanceof PlayerEntity ? 10 : 50));
        }
        super.tick();
    }


    /**
     * @param method what to do while raycasting
     * @return the final point of the raycast
     */
    @SuppressWarnings("UnusedReturnValue")
    public Vec3d raycastParticle(RailgunRender method) {
        Vec3d vec3d = Vec3d.fromPolar(this.getPitch(), this.getYaw()).normalize();
        Vec3d raycast = this.getEyePos();
        int iterations = 0;
        Set<PlayerEntity> encountered = new HashSet<>();
        while (!this.getWorld().getBlockState(BlockPos.ofFloored(raycast)).isSolid()) {
            if (iterations > MAX_ITERATIONS) {
                break;
            }
            raycast = raycast.add(vec3d);
            double x = raycast.getX();
            double y = raycast.getY();
            double z = raycast.getZ();
            method.invoke(this.getWorld(), encountered, vec3d, raycast, x, y, z, iterations);
            iterations++;
        }
        return raycast;
    }

    @Override
    public void copyFrom(Entity original) {
        super.copyFrom(original);
        if (original instanceof RailgunEntity railgunEntity) {
            this.setOwner(railgunEntity.getOwner());
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
            this.owner = null;
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }
}
