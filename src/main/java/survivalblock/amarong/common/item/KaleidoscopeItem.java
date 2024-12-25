package survivalblock.amarong.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.IAmASpyglassItem;

import java.util.ArrayList;
import java.util.List;

public class KaleidoscopeItem extends SpyglassItem implements IAmASpyglassItem {

    public static final ArrayList<Identifier> SUPER_SECRET_SETTING_PROGRAMS = new ArrayList<>(List.of(Identifier.ofVanilla("shaders/post/notch.json"), Identifier.ofVanilla("shaders/post/fxaa.json"), Identifier.ofVanilla("shaders/post/art.json"), Identifier.ofVanilla("shaders/post/bumpy.json"), Identifier.ofVanilla("shaders/post/blobs2.json"), Identifier.ofVanilla("shaders/post/pencil.json"), Identifier.ofVanilla("shaders/post/color_convolve.json"), Identifier.ofVanilla("shaders/post/deconverge.json"), Identifier.ofVanilla("shaders/post/flip.json"), Identifier.ofVanilla("shaders/post/invert.json"), Identifier.ofVanilla("shaders/post/ntsc.json"), Identifier.ofVanilla("shaders/post/outline.json"), Identifier.ofVanilla("shaders/post/phosphor.json"), Identifier.ofVanilla("shaders/post/scan_pincushion.json"), Identifier.ofVanilla("shaders/post/sobel.json"), Identifier.ofVanilla("shaders/post/bits.json"), Identifier.ofVanilla("shaders/post/desaturate.json"), Identifier.ofVanilla("shaders/post/green.json"), Identifier.ofVanilla("shaders/post/blur.json"), Identifier.ofVanilla("shaders/post/wobble.json"), Identifier.ofVanilla("shaders/post/blobs.json"), Identifier.ofVanilla("shaders/post/antialias.json"), Identifier.ofVanilla("shaders/post/creeper.json"), Identifier.ofVanilla("shaders/post/spider.json")));
    public static final Identifier KALEIDOSCOPE_SCOPE = Amarong.id("textures/misc/kaleidoscope_scope.png");

    public KaleidoscopeItem(Settings settings) {
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

    @Override
    public Identifier getOverlay(ItemStack stack) {
        return KALEIDOSCOPE_SCOPE;
    }
}
