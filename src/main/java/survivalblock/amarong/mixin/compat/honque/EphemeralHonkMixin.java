package survivalblock.amarong.mixin.compat.honque;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.common.item.AmarongHammerItem;
import symbolics.division.honque.magic.EphemeralHonk;

@Mixin(EphemeralHonk.class)
public class EphemeralHonkMixin {

    /*
    @Inject(method = "poof", at = @At("HEAD"), cancellable = true)
    private static void cancelUnpoof(Entity victim, CallbackInfo ci) {
        if (victim instanceof PlayerEntity player && player.getMainHandStack().getItem() instanceof AmarongHammerItem) {
            ci.cancel();;
        }
    }

     */
}
