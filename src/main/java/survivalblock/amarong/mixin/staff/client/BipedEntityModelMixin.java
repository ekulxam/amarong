package survivalblock.amarong.mixin.staff.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @ModifyExpressionValue(method = "positionRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F", ordinal = 0, opcode = Opcodes.GETFIELD))
    private float modifyRightArmStaffPitch(float original, LivingEntity living) {
        if (!(living instanceof PlayerEntity player)) {
            return original;
        }
        ItemStack stack = player.getMainArm().equals(Arm.RIGHT) ? player.getMainHandStack() : player.getOffHandStack();
        if (!(stack.getItem() instanceof AmarongStaffItem)) {
            return original;
        }
        return amarong$modifyArmStaffPitch(original);
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
        return amarong$modifyArmStaffPitch(original);
    }

    @Unique
    private float amarong$modifyArmStaffPitch(float original) {
        return original - 0.825f;
    }
}
