package survivalblock.amarong.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class KaleidescopeItem extends SpyglassItem {

    public KaleidescopeItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return Math.min(Math.abs(super.getMaxUseTime(stack, user)) * 2, 72000);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.amarong.amarong_kaleidoscope.headaches").formatted(Formatting.DARK_GRAY));
    }
}
