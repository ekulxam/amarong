package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import survivalblock.amarong.common.item.AmarongVerylongswordItem;

import static survivalblock.amarong.common.item.AmarongVerylongswordItem.OBSCURE_PULSE_RGB;
import static survivalblock.amarong.common.item.AmarongVerylongswordItem.OBSCURE_RGB;
import static survivalblock.amarong.common.item.AmarongVerylongswordItem.RAILGUN_PULSE_RGB;
import static survivalblock.amarong.common.item.AmarongVerylongswordItem.RAILGUN_RGB;

@Environment(EnvType.CLIENT)
@Debug(export = true)
@Mixin(AmarongVerylongswordItem.class)
public class AmarongVerylongswordItemMixin {

    @SuppressWarnings("DiscouragedShift")
    @ModifyReturnValue(method = "getItemBarColor", slice = @Slice(to = @At(value = "TAIL", shift = At.Shift.BEFORE)), at = @At("RETURN"))
    private int pulsingItemBar(int original, ItemStack stack) {
        boolean railgun = (original == RAILGUN_RGB);
        if (original == OBSCURE_RGB || railgun) {
            ClientWorld world = MinecraftClient.getInstance().world;
            if (world == null) return original;
            if (AmarongVerylongswordItem.getMaxCharge(stack) != AmarongVerylongswordItem.checkForReset(stack)) {
                return original;
            }
            int otherColor = railgun ? RAILGUN_PULSE_RGB : OBSCURE_PULSE_RGB;
            return ColorHelper.Argb.lerp((float) Math.abs(Math.sin(world.getTime() * 0.115)), otherColor, original);
        }
        return 0;
    }
}
