package survivalblock.amarong.common.entity;

import gay.lemmaeof.terrifictickets.TerrificTickets;
import gay.lemmaeof.terrifictickets.block.TicketAcceptorBlock;
import gay.lemmaeof.terrifictickets.block.TokenAcceptorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongGameRules;

public class FlyingTicketEntity extends PersistentProjectileEntity {

    public FlyingTicketEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlyingTicketEntity(World world, LivingEntity owner, ItemStack shotFrom) {
        super(AmarongEntityTypes.FLYING_TICKET, owner, world, TerrificTickets.TICKET.getDefaultStack(), shotFrom);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        World world = this.getWorld();
        if (!world.isClient()) {
            BlockPos pos = blockHitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof TicketAcceptorBlock)) {
                return;
            }
            if (state.contains(TicketAcceptorBlock.COST)) {
                if (state.get(TicketAcceptorBlock.COST) > 1) {
                    return;
                }
            }
            world.setBlockState(pos, state.with(TicketAcceptorBlock.ACTIVATED, true));
            world.scheduleBlockTick(pos, state.getBlock(), 10);
            world.playSound(null, pos, TerrificTickets.TICKET_ACCEPT, SoundCategory.BLOCKS, 1f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (!world.isClient()) {
            if (this.inGroundTime > 1 || this.age > 2400) {
                if (world.getGameRules().getBoolean(AmarongGameRules.FLYING_TICKETS_DROP)) {
                    this.dropStack(this.getItemStack().copyWithCount(1), 0);
                }
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return TerrificTickets.TICKET.getDefaultStack();
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        World world = this.getWorld();
        Entity entity = entityHitResult.getEntity();
        double damage = this.getDamage();
        Entity entity2 = this.getOwner();
        DamageSource damageSource = AmarongDamageTypes.create(AmarongDamageTypes.FLYING_TICKET_HIT, this, entity2 == null ? this : entity2);
        if (this.getWeaponStack() != null) {
            if (world instanceof ServerWorld serverWorld) {
                damage = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, (float) damage);
            }
        }
        if (entity.getType().isIn(EntityTypeTags.DEFLECTS_PROJECTILES) && !world.isClient()) {
            this.discard();
            return;
        }
        if (entity.damage(damageSource, (float) damage)) {
            if (entity instanceof EndermanEntity) {
                return;
            }
            if (entity instanceof LivingEntity living) {
                this.onHit(living);
            }
        }
        if (!world.isClient()) {
            if (world.getGameRules().getBoolean(AmarongGameRules.FLYING_TICKETS_DROP)) {
                this.dropStack(this.getItemStack().copyWithCount(1), 0);
            }
            this.discard();
        }
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_ARROW_HIT;
    }

    @Override
    protected double getGravity() {
        return super.getGravity() / 3;
    }
}
