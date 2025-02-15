package survivalblock.amarong.common.entity;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.NotNull;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongSounds;
import survivalblock.amarong.common.init.AmarongTags;
import survivalblock.atmosphere.atmospheric_api.not_mixin.entity.StacklessPersistentProjectile;

public class WaterStreamEntity extends PersistentProjectileEntity implements StacklessPersistentProjectile {

    private static final float BASE_DAMAGE = 0.25f;

    public WaterStreamEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public WaterStreamEntity(World world, LivingEntity owner, @NotNull ItemStack shotFrom) {
        super(AmarongEntityTypes.WATER_STREAM, owner, world, ItemStack.EMPTY, shotFrom);
        try {
            this.setDamage(BASE_DAMAGE + (shotFrom.atmospheric_api$getAbsoluteLevel(AmarongTags.AmarongEnchantmentTags.POWER_LIKE) / 5f));
        } catch (Exception e) {
            this.setDamage(BASE_DAMAGE);
        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (!world.isClient()) {
            this.extinguish();
            if (this.getGroundTime() > 4) {
                this.discard();
            }
        }
    }

    public int getGroundTime() {
        return this.inGroundTime;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ItemStack.EMPTY;
    }

    public void spawnParticles(ParticleEffect particleEffect) {
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
        entity.extinguishWithSound();
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
        DamageSource damageSource = new DamageSource(world.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, AmarongDamageTypes.WATER_STREAM_HIT), this, entity2 == null ? this : entity2);

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

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockPos blockPos2 = blockPos.offset(direction);
            this.extinguishFire(blockPos2);
            this.extinguishFire(blockPos2.offset(direction.getOpposite()));
            for (Direction direction2 : Direction.Type.HORIZONTAL) {
                this.extinguishFire(blockPos2.offset(direction2));
            }
        }
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    protected boolean canModifyWorld(BlockPos pos) {
        if (this.isOwnerAlive()) {
            Entity owner = this.getOwner();
            if (!(owner instanceof PlayerEntity player)) {
                return true;
            }
            World world = this.getWorld();
            return world.canPlayerModifyAt(player, pos) && player.canModifyBlocks();
        }
        return true;
    }

    protected void extinguishFire(BlockPos pos) {
        if (!this.canModifyWorld(pos)) {
            return;
        }
        World world = this.getWorld();
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(BlockTags.FIRE)) {
            world.breakBlock(pos, false, this);
        } else if (AbstractCandleBlock.isLitCandle(blockState)) {
            AbstractCandleBlock.extinguish(null, blockState, world, pos);
        } else if (CampfireBlock.isLitCampfire(blockState)) {
            world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, pos, 0);
            CampfireBlock.extinguish(this.getOwner(), world, pos, blockState);
            world.setBlockState(pos, blockState.with(CampfireBlock.LIT, false));
        }
    }
}
