package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @ModifyExpressionValue(method = "getUsingItemHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private static boolean usingVerylongswordRenderType(boolean original, @Local ItemStack stack){
        return original || (stack.getItem() instanceof AmarongVerylongswordItem && AmarongConfig.twoHandedVerylongsword());
    }

    @ModifyExpressionValue(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 3))
    private static boolean verylongswordIsACrossbowToo(boolean original, @Local(ordinal = 0) ItemStack itemStack, @Local(ordinal = 1) ItemStack itemStack2){
        return original || ((itemStack.getItem() instanceof AmarongVerylongswordItem || itemStack2.getItem() instanceof AmarongVerylongswordItem) && AmarongConfig.twoHandedVerylongsword());
    }

    @ModifyExpressionValue(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;isChargedCrossbow(Lnet/minecraft/item/ItemStack;)Z"))
    private static boolean mainHandVerylongsword(boolean original, @Local(ordinal = 0) ItemStack itemStack){
        return original || (itemStack.getItem() instanceof AmarongVerylongswordItem && AmarongConfig.twoHandedVerylongsword());
    }

    @ModifyExpressionValue(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private boolean renderFirstPersonVerylongsword(boolean original, @Local(argsOnly = true) ItemStack stack){
        return original || (stack.getItem() instanceof AmarongVerylongswordItem && AmarongConfig.twoHandedVerylongsword());
    }

    @ModifyExpressionValue(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean noVerylongswordDrawbackAnimation(boolean original, @Local(argsOnly = true) ItemStack stack){
        return !(stack.getItem() instanceof AmarongVerylongswordItem && AmarongConfig.twoHandedVerylongsword()) && original;
    }
}
