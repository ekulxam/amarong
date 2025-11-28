package survivalblock.amarong.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.AmarongHammerTwirlCompat;
import survivalblock.amarong.common.init.AmarongTags;

public class AmarongHammerItem extends MiningToolItem {

    public AmarongHammerItem(ToolMaterial material, AmarongToolMaterial.Configuration settings) {
        super(material, AmarongTags.AmarongBlockTags.AMARONG_HAMMER_MINEABLE, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return Items.MACE.postHit(stack, target, attacker);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Items.MACE.postDamageEntity(stack, target, attacker);
        World world = attacker.getWorld();
        if (!world.isClient()) {
            Vec3d pos = attacker.getPos();
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0F,  0.1F + world.random.nextFloat() * 1.2F);
        }
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        return Items.MACE.getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (Amarong.TWIRL) {
            if (AmarongHammerTwirlCompat.hasTwirl(stack)) {
                user.setCurrentHand(hand);
                return TypedActionResult.success(stack);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (Amarong.TWIRL && user instanceof ServerPlayerEntity serverPlayer) {
            AmarongHammerTwirlCompat.whack(serverPlayer);
        }
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (state.isIn(AmarongTags.AmarongBlockTags.AMARONG_HAMMER_MINEABLE)) {
            return true;
        }
        return !miner.isCreative();
    }
}
