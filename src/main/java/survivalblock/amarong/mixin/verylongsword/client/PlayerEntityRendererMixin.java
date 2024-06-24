package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", shift = At.Shift.BEFORE), cancellable = true)
    private static void crossbowVerylongsword(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir, @Local ItemStack stack){
        if (stack.getItem() instanceof AmarongVerylongswordItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
