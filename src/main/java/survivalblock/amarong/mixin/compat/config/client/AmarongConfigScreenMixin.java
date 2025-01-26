package survivalblock.amarong.mixin.compat.config.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.client.compat.config.AmarongConfigScreen;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongYACLCompat;

@Environment(EnvType.CLIENT)
@Mixin(AmarongConfigScreen.class)
public class AmarongConfigScreenMixin {

    @ModifyReturnValue(method = "create", at = @At("RETURN"), remap = false)
    private static Screen verboseLogging(Screen original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.create(original) : original;
    }
}
