package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.client.AmarongClient;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @WrapOperation(method = {"positionLeftArm", "positionRightArm"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/CrossbowPosing;hold(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Z)V"))
    private void doSomeCursedLongswordPosing(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed, Operation<Void> original, LivingEntity living) {
        if (living.getMainHandStack().getItem() instanceof AmarongVerylongswordItem || living.getOffHandStack().getItem() instanceof AmarongVerylongswordItem) {
            AmarongClient.verylongswordPosing = true;
        }
        original.call(holdingArm, otherArm, head, rightArmed);
        AmarongClient.verylongswordPosing = false;
    }
}
