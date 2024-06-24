package survivalblock.amarong.common.entity;

import gay.lemmaeof.terrifictickets.TerrificTickets;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEntityTypes;

public class FlyingTicketEntity extends PersistentProjectileEntity {

    public FlyingTicketEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlyingTicketEntity(World world, LivingEntity owner, ItemStack shotFrom) {
        super(AmarongEntityTypes.FLYING_TICKET, owner, world, TerrificTickets.TICKET.getDefaultStack(), shotFrom);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (this.inGroundTime > 1) {
                this.dropStack(this.getItemStack().copyWithCount(1), 0);
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
            this.dropStack(this.getItemStack().copyWithCount(1), 0);
            this.discard();
        }
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ENTITY_ARROW_HIT;
    }
}
