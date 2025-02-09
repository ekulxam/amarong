package survivalblock.amarong.mixin.verylongsword;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapOperation(method = "disablesShield", constant = @Constant(classValue = AxeItem.class))
    private boolean longswordGoBoom(Object item, Operation<Boolean> original) {
        return original.call(item) || item instanceof AmarongVerylongswordItem;
    }
}
