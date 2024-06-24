package survivalblock.amarong.mixin.models.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.amarong.client.AmarongClient;
import survivalblock.amarong.common.init.AmarongItems;

@Debug(export = true)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow @Final private ItemModels models;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1),
            index = 8, argsOnly = true)
    private BakedModel getKaleidoscopeModel(BakedModel value, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(AmarongItems.KALEIDOSCOPE)) {
            return this.models.getModelManager().getModel(AmarongClient.KALEIDOSCOPE);
        }
        return value;
    }

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            index = 8, argsOnly = true)
    private BakedModel getVerylongswordInventory(BakedModel value, @Local(argsOnly = true) ModelTransformationMode renderMode, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(AmarongItems.AMARONG_VERYLONGSWORD) && renderMode.equals(ModelTransformationMode.GUI)) {
            return this.models.getModelManager().getModel(AmarongClient.VERYLONGSWORD_INVENTORY);
        }
        return value;
    }

    @ModifyExpressionValue(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;"))
    private BakedModel getKaleidoscopeHandheldModel(BakedModel original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(AmarongItems.KALEIDOSCOPE)) {
            return this.models.getModelManager().getModel(AmarongClient.KALEIDOSCOPE_IN_HAND);
        }
        return original;
    }
}
