package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin {

    @ModifyExpressionValue(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean isOfKaleidoscope(boolean original, @Local(argsOnly = true) ItemStack stack) {
        return original || stack.isOf(AmarongItems.KALEIDOSCOPE);
    }
}
