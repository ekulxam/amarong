package survivalblock.amarong.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;
import survivalblock.amarong.common.init.AmarongGameRules;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.AlternateModelItem;

public class AmarongBoomerangItem extends SwordItem implements AlternateModelItem {

    public AmarongBoomerangItem(ToolMaterial toolMaterial, AmarongToolMaterial.Configuration configuration) {
        super(toolMaterial, configuration);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            PhasingBoomerangEntity boomerang = new PhasingBoomerangEntity(user, world, stack.copyWithCount(1), user.getInventory().getSlotWithStack(stack));
            boomerang.setVelocity(Vec3d.fromPolar(user.getPitch(), user.getYaw()));
            boomerang.setDamage(world.getGameRules().get(AmarongGameRules.BOOMERANG_DAMAGE).get());
            if (user.isInCreativeMode()) {
                boomerang.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(boomerang);
            user.getItemCooldownManager().set(stack.getItem(), 20);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            stack.decrementUnlessCreative(stack.getCount(), user);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }
}
