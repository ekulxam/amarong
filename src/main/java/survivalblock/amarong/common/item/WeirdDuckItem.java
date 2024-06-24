package survivalblock.amarong.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import survivalblock.amarong.common.entity.WaterStreamEntity;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongSounds;

import java.awt.*;
import java.util.List;

public class WeirdDuckItem extends Item {

    public static final int MAX_WATER = 100;
    public static final Color WATER_COLOR = new Color(56, 208, 242);

    public WeirdDuckItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) {
            return TypedActionResult.pass(stack);
        }
        int water = checkForReset(stack);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);
        if (!blockHitResult.getType().equals(HitResult.Type.BLOCK) || water >= MAX_WATER) {
            return prepareToShoot(world, user, hand, water, stack);
        }
        BlockPos blockPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getSide();
        BlockPos blockPos2 = blockPos.offset(direction);
        if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, stack)) {
            BlockState blockState = world.getBlockState(blockPos);
            boolean waterlogged= blockState.getProperties().contains(Properties.WATERLOGGED) && blockState.get(Properties.WATERLOGGED);
            Fluid fluid = blockState.getFluidState().getFluid();
            boolean isWater = fluid.equals(Fluids.WATER) || fluid.equals(Fluids.FLOWING_WATER);
            if (waterlogged || isWater) {
                stack.set(AmarongDataComponentTypes.WATERGUN, MathHelper.clamp(10 + water, 0, MAX_WATER));
                playDuckSound(world, user);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                return TypedActionResult.success(stack);
            }
        }
        return prepareToShoot(world, user, hand, water, stack);
    }

    private @NotNull TypedActionResult<ItemStack> prepareToShoot(World world, PlayerEntity user, Hand hand, int water, ItemStack stack) {
        if (water <= 0) {
            stack.set(AmarongDataComponentTypes.WATERGUN, 0);
            return TypedActionResult.pass(stack);
        }
        user.setCurrentHand(hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        playDuckSound(world, user);
        return TypedActionResult.success(stack);
    }

    public static void playDuckSound(World world, PlayerEntity user) {
        world.playSound(null, user.getBlockPos(), AmarongSounds.DUCK_SQUEAKS, user.getSoundCategory(), 1.0f, 0.8f + MathHelper.nextFloat(world.getRandom(), 0, 0.35f));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient()) {
            return;
        }
        int water = checkForReset(stack);
        if (water <= 0) {
            stack.set(AmarongDataComponentTypes.WATERGUN, 0);
            user.stopUsingItem();
            return;
        }
        stack.set(AmarongDataComponentTypes.WATERGUN, MathHelper.clamp(water - 1, 0, MAX_WATER));
        WaterStreamEntity waterStream = new WaterStreamEntity(world, user, stack);
        waterStream.setVelocity(Vec3d.fromPolar(user.getPitch(), user.getYaw()).multiply(2));
        world.spawnEntity(waterStream);
    }

    public static int checkForReset(ItemStack stack) {
        if (!stack.contains(AmarongDataComponentTypes.WATERGUN)) {
            stack.set(AmarongDataComponentTypes.WATERGUN, 0);
        }
        return stack.get(AmarongDataComponentTypes.WATERGUN);
    }


    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return checkForReset(stack) > 0;
    }

    /**
     * custom override to display how many souls there are via item bar
     * @param stack itemStack that should display the bar
     * @return souls * 13.0f / maxSouls
     */
    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((checkForReset(stack) * 13.0f) / MAX_WATER);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return WATER_COLOR.getRGB();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.amarong.somewhat_a_duck.water", checkForReset(stack), MAX_WATER).withColor(WATER_COLOR.getRGB()));
    }
}
