package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import survivalblock.amarong.client.AmarongClientUtil;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean changeMouseSmoothnessInZoom(boolean original) {
        if (!this.client.player.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE)) {
            return original;
        }
        if (AmarongConfig.noKaleidoscopeZoom()) {
            return false;
        }
        return original;
    }
}
