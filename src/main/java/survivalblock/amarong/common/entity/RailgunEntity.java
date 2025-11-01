package survivalblock.amarong.common.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientCommonPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
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
import survivalblock.amarong.common.init.*;
import survivalblock.atmosphere.atmospheric_api.not_mixin.entity.StacklessPersistentProjectile;
import survivalblock.atmosphere.atmospheric_api.not_mixin.particle.DirectionalParticleS2CPayload;

import java.util.*;
import java.util.List;

public class RailgunEntity extends Entity implements Ownable, StacklessPersistentProjectile {
    public static final int FIRING_TICKS = 40;
    public static final int MAX_ITERATIONS = 384;
    @Nullable
    protected UUID ownerUuid;
    @Nullable
    protected Entity owner;
    protected ItemStack stack;
    protected boolean hasWarned = false;

    public RailgunEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public RailgunEntity(World world, Entity owner, ItemStack stack) {
        this(AmarongEntityTypes.RAILGUN, world);
        this.setPosition(owner.getEyePos());
        this.setYaw(owner.getHeadYaw());
        this.setPitch(owner.getPitch());
        this.setOwner(owner);
        this.stack = stack.copy();
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

    public int getFiringTicks() {
        return noDelay() ? 0 : FIRING_TICKS;
    }

    public boolean noDelay() {
        if (this.stack == null || this.stack.isEmpty()) {
            return false;
        }
        return this.stack.getOrDefault(AmarongDataComponentTypes.NO_RAILGUN_DELAY, false);
    }

    @Override
    public void tick() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (this.age > getFiringTicks() + 10) {
                this.discard();
                return;
            }
            this.tickFiring(serverWorld);
        }
        super.tick();
    }

    protected void tickFiring(ServerWorld serverWorld) {
        if (!this.hasWarned && !noDelay()) {
            this.hasWarned = true;
            List<ServerPlayerEntity> players = serverWorld.getPlayers();
            RegistryEntry<SoundEvent> registryEntry = Registries.SOUND_EVENT.getEntry(AmarongSounds.RAILGUN_CHARGES);
            raycastParticle((encountered, pitch, yaw, currentRaycastPosition, iterations) -> {
                for (ServerPlayerEntity player : players) {
                    serverWorld.spawnParticles(player, ParticleTypes.END_ROD, true, currentRaycastPosition.x, currentRaycastPosition.y, currentRaycastPosition.z, 1, 0, 0, 0, 0);
                    if (player.getPos().squaredDistanceTo(currentRaycastPosition) < 25 && !encountered.contains(player)) {
                        // don't send a million playSound packets
                        player.networkHandler.sendPacket(new PlaySoundFromEntityS2CPacket(registryEntry, this.getSoundCategory(), player, 1.0F, 1.0F, this.getRandom().nextLong()));
                        encountered.add(player);
                    }
                }
            });
            return;
        }
        if (this.age < getFiringTicks()) {
            return;
        }
        List<ServerPlayerEntity> players = serverWorld.getPlayers();
        final float particleDistSquared = 512 * 512;
        final double boxRadius = 1.25;
        Set<Entity> entities = new HashSet<>(1024);
        Set<Box> boxes = new HashSet<>(100);

        raycastParticle((encountered, pitch, yaw, currentRaycastPosition, iterations) -> {
            CustomPayload payload = new DirectionalParticleS2CPayload.Builder()
                    .particleEffect(new AmarongParticleTypes.RailgunParticleEffect(pitch, yaw))
                    .pos(currentRaycastPosition)
                    .direction(pitch, yaw)
                    .deltaPos(0.1, 0.1, 0.1)
                    .force(true)
                    .build();
            Packet<ClientCommonPacketListener> packet = ServerPlayNetworking.createS2CPacket(payload);
            for (ServerPlayerEntity player : players) {
                if (player.squaredDistanceTo(currentRaycastPosition) < particleDistSquared) {
                    player.networkHandler.sendPacket(packet);
                }
            }
            Vec3d lowerCorner = currentRaycastPosition.subtract(boxRadius, boxRadius, boxRadius);
            Vec3d upperCorner = currentRaycastPosition.add(boxRadius, boxRadius, boxRadius);
            boxes.add(new Box(lowerCorner, upperCorner));
        });

        serverWorld.atmospheric_api$getAndAddEntitiesToCollection(entity -> {
            for (Box box : boxes) {
                if (entity.getBoundingBox().intersects(box)) {
                    return true;
                }
            }
            return false;
        }, entities);

        DamageSource source;
        Entity owner = this.getOwner();
        RegistryEntry.Reference<DamageType> reference = serverWorld.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, AmarongDamageTypes.RAILGUN_HIT);
        if (owner instanceof LivingEntity living) {
            source = new DamageSource(reference, this, living);
        } else {
            source = new DamageSource(reference, this);
        }

        if (owner != null) {
            entities.remove(owner);
        }
        entities.remove(this);

        final float playerDamage = this.getPlayerDamage();
        entities.forEach(entity -> {
            if (AmarongUtil.shouldDamageWithAmarong(entity)) {
                entity.damage(source, entity instanceof PlayerEntity ? playerDamage : 50);
            }
        });
    }

    private float getPlayerDamage() {
        if (this.stack == null || this.stack.isEmpty()) {
            return 17.5f;
        }
        if (AmarongUtil.shouldRetainCharge(this.stack)) {
            return 20;
        }
        return 17.5f;
    }


    /**
     * @param step what to do while raycasting
     * @return the final point of the raycast
     */
    @SuppressWarnings({"UnusedReturnValue", "deprecation"})
    public Vec3d raycastParticle(IRailgunRaycast step) {
        final float pitch = this.getPitch();
        final float yaw = this.getYaw();
        Vec3d vec3d = Vec3d.fromPolar(pitch, yaw).normalize();
        Vec3d raycast = this.getEyePos();
        int iterations = 0;
        Set<PlayerEntity> encountered = new HashSet<>();
        while (!this.getWorld().getBlockState(BlockPos.ofFloored(raycast)).isSolid()) {
            if (iterations > MAX_ITERATIONS) {
                break;
            }
            raycast = raycast.add(vec3d);
            step.step(encountered, pitch, yaw, raycast, iterations);
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
        if (nbt.contains("item", NbtElement.COMPOUND_TYPE)) {
            this.stack = ItemStack.fromNbt(this.getRegistryManager(), nbt.getCompound("item")).orElse(this.getDefaultItemStack());
        } else {
            this.stack = this.getDefaultItemStack();
        }
    }

    public ItemStack getDefaultItemStack() {
        return AmarongItems.AMARONG_VERYLONGSWORD.getDefaultStack();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
        if (this.stack != null && !this.stack.isEmpty()) {
            nbt.put("item", this.stack.encode(this.getRegistryManager()));
        }
    }

    @FunctionalInterface
    public interface IRailgunRaycast {
        void step(Set<PlayerEntity> encountered, float pitch, float yaw, Vec3d currentRaycastPosition, int iterations);
    }
}
