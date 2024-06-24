package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.client.AmarongClient;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(value = InGameHud.class, priority = 0)
public class InGameHudMixin {

    @Unique
    private static final Identifier KALEIDOSCOPE_SCOPE = Amarong.id("textures/misc/kaleidoscope_scope.png");

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void noKaleidoscopeOverlay(DrawContext context, float scale, CallbackInfo ci) {
        if (!this.client.player.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE)) {
            return;
        }
        if (this.client.getResourcePackManager().getEnabledIds().contains("amarong:" + AmarongClient.KALEIDOSCOPE_OVERLAY_PACK_NAME)) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "renderSpyglassOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;SPYGLASS_SCOPE:Lnet/minecraft/util/Identifier;"))
    private Identifier renderKaleidoscopeOverlayInstead(Identifier original) {
        return this.client.player.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE) ? KALEIDOSCOPE_SCOPE : original;
    }
}
