package survivalblock.amarong.mixin.verylongsword;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "CONSTANT", args = "doubleValue=64.0"))
    private static double increaseMaxInteractionRange(double original) {
        return Math.max(original, 64.0) * 4;
    }
}
