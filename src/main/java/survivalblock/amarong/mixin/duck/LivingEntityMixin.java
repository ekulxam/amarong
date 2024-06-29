package survivalblock.amarong.mixin.duck;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.init.AmarongSounds;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyExpressionValue(method = {"playHurtSound", "onDamaged"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent hurtQuack(SoundEvent original) {
        return this.getEquippedStack(EquipmentSlot.HEAD).getItem().equals(AmarongItems.SOMEWHAT_A_DUCK) ? AmarongSounds.DUCK_SQUEAKS : original;
    }
}
