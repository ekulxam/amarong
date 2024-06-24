package survivalblock.amarong.mixin.verylongsword;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @ModifyReturnValue(method = "disablesShield", at = @At("RETURN"))
    private boolean longswordGoBoom(boolean original) {
        return original || this.getWeaponStack().getItem() instanceof AmarongVerylongswordItem;
    }
}
