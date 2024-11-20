package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.client.AmarongClientUtil;
import survivalblock.amarong.common.compat.config.AmarongConfig;

@Mixin(CrossbowPosing.class)
public class CrossbowPosingMixin {

    // I HATE RENDERING I HATE RENDERING I HATE RENDERING I HATE RENDERING I HATE RENDERING
    // I strongly dislike rendering

    @ModifyExpressionValue(method = "hold", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F", opcode = Opcodes.GETFIELD))
    private static float whyDidIEvenNeedToMakeThisMixinAgain(float original) {
        return AmarongClientUtil.verylongswordPosing && AmarongConfig.twoHandedVerylongsword() ? 0.41f : original;
    }
}
