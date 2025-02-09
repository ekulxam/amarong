package survivalblock.amarong.mixin.hammer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(MaceItem.class)
public class MaceItemMixin {

    @Unique
    private final Random amarong$random = Random.create();

    @WrapOperation(method = "postHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private void playHammerSmashSound(ServerWorld instance, PlayerEntity player, double x, double y, double z, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch, Operation<Void> original, @Local(argsOnly = true)ItemStack stack) {
        if (!stack.isOf(AmarongItems.AMARONG_HAMMER)) {
            original.call(instance, player, x, y, z, soundEvent, soundCategory, volume, pitch);
            return;
        }
        original.call(instance, player, x, y, z, soundEvent, soundCategory, volume, 2.0F);
        original.call(instance, player, x, y, z, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, soundCategory, volume, 1.0F + MathHelper.nextBetween(amarong$random, 0.2F, 1.2F));
    }
}
