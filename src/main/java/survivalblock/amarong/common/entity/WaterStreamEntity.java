package survivalblock.amarong.common.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongSounds;

public class WaterStreamEntity extends PersistentProjectileEntity {

    private static final float BASE_DAMAGE = 0.25f;

    public WaterStreamEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public WaterStreamEntity(World world, LivingEntity owner, ItemStack shotFrom) {
        super(AmarongEntityTypes.WATER_STREAM, owner, world, ItemStack.EMPTY, shotFrom);
        try {
            this.setDamage(BASE_DAMAGE + (EnchantmentHelper.getLevel(this.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.POWER), shotFrom) / 5f));
        } catch (IllegalStateException illegalStateException) {
            this.setDamage(BASE_DAMAGE);
        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (world.isClient()) {
            if (this.isSubmergedInWater() && !(this.horizontalCollision || this.verticalCollision)) {
                spawnParticles(ParticleTypes.BUBBLE);
            } else if (this.horizontalCollision || this.verticalCollision || this.isOnGround() || this.inGroundTime > 0) {
                spawnParticles(ParticleTypes.RAIN);
            } else if (!this.isOnGround() && !(this.inGroundTime > 0)) {
                spawnParticles(ParticleTypes.UNDERWATER);
            }
        } else {
            if (this.inGroundTime > 4) {
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ItemStack.EMPTY;
    }

    private void spawnParticles(ParticleEffect particleEffect) {
        World world = this.getWorld();
        if (!world.isClient()) {
            return;
        }
        final double maxOffset = 0.1;
        for (int i = 0; i < 3; i++) {
            Random random = world.getRandom();
            world.addImportantParticle(particleEffect,
                    this.getX() + MathHelper.nextDouble(random, -maxOffset, maxOffset),
                    this.getY() + MathHelper.nextDouble(random, -maxOffset, maxOffset),
                    this.getZ() + MathHelper.nextDouble(random, -maxOffset, maxOffset), 0, 0,0);
        }
    }

    @Override
    protected float getDragInWater() {
        return 0.99f;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        double damage = this.getDamage();
        if (entity instanceof LivingEntity living && living.hurtByWater()) {
            damage *= 4.0F;
        }
        World world = this.getWorld();
        if (entity.getType().isIn(EntityTypeTags.DEFLECTS_PROJECTILES) && !world.isClient()) {
            this.discard();
            return;
        }
        Entity entity2 = this.getOwner();
        DamageSource damageSource = AmarongDamageTypes.create(AmarongDamageTypes.WATER_STREAM_HIT, this, entity2 == null ? this : entity2);

        if (entity.damage(damageSource, (float) damage)) {
            if (entity instanceof EndermanEntity) {
                return;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.onHit(livingEntity);
            }
        }
        if (!world.isClient()) this.discard();
    }

    @Override
    protected SoundEvent getHitSound() {
        return AmarongSounds.WATER_STREAM_HITS;
    }
}
