package survivalblock.amarong.mixin.ticketlauncher;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.amarong.common.AmarongUtil;

@Mixin(ThrowablePotionItem.class)
public class ThrowablePotionItemMixin {

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/thrown/PotionEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V", shift = At.Shift.AFTER))
    private void increasePotionVelocity(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local PotionEntity potionEntity) {
        if (AmarongUtil.increaseThrowablePotionVelocity) {
            potionEntity.setVelocity(potionEntity.getVelocity().multiply(3));
        }
    }

    @ModifyExpressionValue(method = "use", at = @At(value = "CONSTANT", args = "floatValue=-20.0"))
    private float whyRoll(float original) {
        if (AmarongUtil.increaseThrowablePotionVelocity) {
            return original / 10;
        }
        return original;
    }
}
