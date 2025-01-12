package survivalblock.amarong.common.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.mixin.boomerang.ProjectileEntityAccessor;

import java.util.Objects;

public class PhasingBoomerangEntity extends PersistentProjectileEntity {

    public static final TrackedData<Boolean> RETURNING = DataTracker.registerData(PhasingBoomerangEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int MAX_AGE = 120;
    public static final int HALF_MAX_AGE = MAX_AGE / 2;
    private int slot = 0;
    private boolean infinity;

    public PhasingBoomerangEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.dataTracker.set(RETURNING, false);
    }

    public PhasingBoomerangEntity(LivingEntity owner, World world, ItemStack stack, int slot) {
        super(AmarongEntityTypes.BOOMERANG, owner, world, stack, null);
        this.dataTracker.set(RETURNING, false);
        this.slot = slot;
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
        super.tick();
        this.setNoClip(true);
        this.setYaw(this.age);
        World world = this.getWorld();
        if (!world.isClient()) {
            if (this.age > MAX_AGE) {
                if (!this.dataTracker.get(RETURNING) || !this.isOwnerAlive() || this.distanceTo(this.getOwner()) > 32) {
                    this.dropAndDiscard();
                }
            } else if (this.age > HALF_MAX_AGE) {
                this.dataTracker.set(RETURNING, true);
            }
        }/* else if (world.isClient()) {
            if (this.horizontalCollision || this.verticalCollision || this.isOnGround()) {
                this.lastRenderY = this.getY();
            }
        }*/

        if (this.dataTracker.get(RETURNING)) {
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
            ItemStack stack = player.getInventory().getStack(slot);
            if (stack == null || stack.isEmpty()) {
                player.getInventory().setStack(slot, this.asItemStack());
                return true;
            }
            return player.getInventory().insertStack(this.asItemStack());
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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RETURNING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("amarongBoomerangInfinity", this.infinity);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("amarongBoomerangInfinity")) {
            this.infinity = nbt.getBoolean("amarongBoomerangInfinity");
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
        double damage = this.getDamage();
        Entity owner = this.getOwner();
        if (Objects.equals(entity, owner)) {
            return;
        }
        boolean isOwnerNull = (owner == null);
        DamageSource damageSource = this.getDamageSources().arrow(this, isOwnerNull ? this : owner);
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
}
