package survivalblock.amarong.mixin.staff.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Debug(export = true)
@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Shadow public BipedEntityModel.ArmPose rightArmPose;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void positionStaffArmWhenUsing(LivingEntity living, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        if (!(living instanceof PlayerEntity player)) {
            return;
        }
        ItemStack stack = player.getMainHandStack();
        if (!(stack.getItem() instanceof AmarongStaffItem)) {
            return;
        }
        if (player.isUsingItem() && stack.equals(player.getActiveItem())) {
            this.rightArmPose = BipedEntityModel.ArmPose.CROSSBOW_HOLD;
        }
    }

    @ModifyExpressionValue(method = "positionRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F", ordinal = 0, opcode = Opcodes.GETFIELD))
    private float modifyRightArmStaffPitch(float original, LivingEntity living) {
        if (!(living instanceof PlayerEntity player)) {
            return original;
        }
        ItemStack stack = player.getMainArm().equals(Arm.RIGHT) ? player.getMainHandStack() : player.getOffHandStack();
        if (!(stack.getItem() instanceof AmarongStaffItem)) {
            return original;
        }
        return original - 0.85f;
    }

    @ModifyExpressionValue(method = "positionLeftArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F", ordinal = 0, opcode = Opcodes.GETFIELD))
    private float modifyLeftArmStaffPitch(float original, LivingEntity living) {
        if (!(living instanceof PlayerEntity player)) {
            return original;
        }
        ItemStack stack = player.getMainArm().equals(Arm.LEFT) ? player.getMainHandStack() : player.getOffHandStack();
        if (!(stack.getItem() instanceof AmarongStaffItem)) {
            return original;
        }
        return original - 0.85f;
    }
}
