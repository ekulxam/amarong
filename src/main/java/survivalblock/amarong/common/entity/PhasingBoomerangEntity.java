package survivalblock.amarong.common.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.component.BoomerangComponent;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityComponents;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.mixin.boomerang.ProjectileEntityAccessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PhasingBoomerangEntity extends PersistentProjectileEntity {

    public static final int MAX_AGE = 120;
    public static final int HALF_MAX_AGE = MAX_AGE / 2;
    public static final String INFINITY_KEY = "amarongBoomerangInfinity";
    public static final String SLOT_KEY = "shotFromInventorySlot";
    private int slot = 0;
    private boolean infinity;
    private final Set<Integer> hitEntities = new HashSet<>();

    public PhasingBoomerangEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public PhasingBoomerangEntity(LivingEntity owner, World world, ItemStack stack, int slot) {
        super(AmarongEntityTypes.BOOMERANG, owner, world, stack, null);
        this.slot = slot;
        this.getBoomerangComponent().setEnchanted(stack.hasGlint());
    }


    public BoomerangComponent getBoomerangComponent() {
        return AmarongEntityComponents.BOOMERANG_COMPONENT.get(this);
    }

    public boolean isReturning() {
        return this.getBoomerangComponent().isReturning();
    }

    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return AmarongItems.AMARONG_BOOMERANG.getDefaultStack();
    }

    @Override
    public void tick() {
        this.hitEntities.clear();
        super.tick();
        this.setNoClip(true);
        this.setYaw(this.age);
        World world = this.getWorld();
        if (!world.isClient()) {
            if (this.age > MAX_AGE) {
                if (!this.isReturning() || !this.isOwnerAlive() || this.distanceTo(this.getOwner()) > 32) {
                    this.dropAndDiscard();
                }
            } else if (this.age > HALF_MAX_AGE) {
                this.getBoomerangComponent().setReturning(true);
            }
            if (world instanceof ServerWorld serverWorld) {
                List<Entity> entities = new ArrayList<>();
                serverWorld.atmospheric_api$getAndAddEntitiesToCollection(entity -> entity.getBoundingBox().intersects(this.getBoundingBox()), entities);
                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        this.onEntityHit(new EntityHitResult(entity));
                    }
                }
            }
        }/* else if (world.isClient()) {
            if (this.horizontalCollision || this.verticalCollision || this.isOnGround()) {
                this.lastRenderY = this.getY();
            }
        }*/

        if (this.isReturning()) {
            if (!this.isOwnerAlive()) {
                this.dropAndDiscard();
            } else {
                Entity entity = Objects.requireNonNull(this.getOwner());
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                byte loyalty = 3; // I love tridents
                double d = 0.05 * (double) loyalty;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
            }
        }
    }

    private void dropAndDiscard() {
        if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED) {
            this.dropStack(this.asItemStack(), 0.1F);
        }
        this.discard();
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        if (!((ProjectileEntityAccessor) this).amarong$getLeftOwner()) {
            return false;
        }
        if (isOwner(player)) {
            if (player.isInCreativeMode() || this.infinity) {
                return true;
            }
            PlayerInventory inventory = player.getInventory();
            try {
                ItemStack stack = inventory.getStack(this.slot);
                if (stack == null || stack.isEmpty()) {
                    inventory.setStack(this.slot, this.asItemStack());
                    return true;
                }
                return inventory.insertStack(this.asItemStack());
            } catch (ArrayIndexOutOfBoundsException e) {
                Amarong.LOGGER.error("An error occured when picking up an Amarong Boomerang!", e);
                return inventory.insertStack(this.asItemStack());
            }
        }
        return false;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null) {
            super.onPlayerCollision(player);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(INFINITY_KEY, this.infinity);
        nbt.putInt(SLOT_KEY, this.slot);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(INFINITY_KEY)) {
            this.infinity = nbt.getBoolean(INFINITY_KEY);
        }
        if (nbt.contains(SLOT_KEY)) {
            this.slot = nbt.getInt(SLOT_KEY);
        }
    }

    protected float getDragInWater() {
        return 1.0F;
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        int id = entity.getId();
        if (hitEntities.contains(id)) {
            return;
        }
        double damage = this.getDamage();
        Entity owner = this.getOwner();
        if (Objects.equals(entity, owner)) {
            return;
        }
        hitEntities.add(id);
        boolean isOwnerNull = (owner == null);
        World world = this.getWorld();
        RegistryEntry.Reference<DamageType> reference = world.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, AmarongDamageTypes.BOOMERANG_HIT);
        DamageSource damageSource = new DamageSource(reference, this, isOwnerNull ? this : owner);
        if (entity.damage(damageSource, (float) damage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.getWorld() instanceof ServerWorld serverWorld) {
                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack());
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.knockback(livingEntity, damageSource);
                this.onHit(livingEntity);
            }
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.INTENTIONALLY_EMPTY;
    }
}
