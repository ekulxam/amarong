package survivalblock.amarong.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;

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
        MutableText shaderText = Text.translatable("item.amarong.amarong_kaleidoscope.random");
        if (stack.contains(AmarongDataComponentTypes.SHADER_TYPE)) {
            Identifier id = stack.get(AmarongDataComponentTypes.SHADER_TYPE);
            if (id != null) {
                shaderText = Text.translatable(id.toTranslationKey());
            }
        }
        tooltip.add(shaderText.formatted(Formatting.DARK_PURPLE));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.contains(AmarongDataComponentTypes.SHADER_TYPE) || super.hasGlint(stack);
    }
}
