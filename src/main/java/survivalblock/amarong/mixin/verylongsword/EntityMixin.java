package survivalblock.amarong.mixin.verylongsword;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.init.AmarongEntityComponents;

@SuppressWarnings({"UnreachableCode", "ConstantValue"})
@Mixin(Entity.class)
public class EntityMixin {

    @ModifyReturnValue(method = "isInvisible", at = @At("RETURN"))
    private boolean obscuredIsInvisible(boolean original) {
        return original || ((Entity) (Object) this instanceof PlayerEntity player && AmarongEntityComponents.VERYLONGSWORD_COMPONENT.get(player).isObscured());
    }
}
