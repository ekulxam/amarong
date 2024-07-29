package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.client.AmarongClientUtil;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(value = InGameHud.class, priority = 0)
public class InGameHudMixin {


    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void noKaleidoscopeOverlay(DrawContext context, float scale, CallbackInfo ci) {
        if (!this.client.player.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE)) {
            return;
        }
        if (AmarongClientUtil.isResourcePackLoaded(this.client, "amarong:" + AmarongClientUtil.KALEIDOSCOPE_OVERLAY_PACK_NAME)) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "renderSpyglassOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;SPYGLASS_SCOPE:Lnet/minecraft/util/Identifier;"))
    private Identifier renderKaleidoscopeOverlayInstead(Identifier original) {
        return this.client.player.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE) ? AmarongClientUtil.KALEIDOSCOPE_SCOPE : original;
    }
}
