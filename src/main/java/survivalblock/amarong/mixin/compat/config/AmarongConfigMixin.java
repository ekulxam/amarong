package survivalblock.amarong.mixin.compat.config;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.compat.config.AmarongYACLCompat;
import survivalblock.amarong.common.compat.config.BeaconBeamDebugMode;

@Environment(EnvType.CLIENT)
@Mixin(value = AmarongConfig.class, remap = false)
public class AmarongConfigMixin {

    @ModifyReturnValue(method = "verboseLogging", at = @At("RETURN"))
    private static boolean verboseLogging(boolean original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().verboseLogging : original;
    }

    @ModifyReturnValue(method = "twoHandedVerylongsword", at = @At("RETURN"))
    private static boolean twoHandedVerylongsword(boolean original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().twoHandedVerylongsword : original;
    }

    @ModifyReturnValue(method = "noKaleidoscopeZoom", at = @At("RETURN"))
    private static boolean noKaleidoscopeZoom(boolean original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().noKaleidoscopeZoom : original;
    }

    @ModifyReturnValue(method = "boomerangSpinMultiplier", at = @At("RETURN"))
    private static float boomerangSpinMultiplier(float original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().boomerangSpinMultiplier : original;
    }

    @ModifyReturnValue(method = "staffRotationMultiplier", at = @At("RETURN"))
    private static float staffRotationMultiplier(float original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().staffRotationMultiplier : original;
    }

    @ModifyReturnValue(method = "debugBeaconBeams", at = @At("RETURN"))
    private static BeaconBeamDebugMode debugBeaconBeams(BeaconBeamDebugMode original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().debugBeaconBeams : original;
    }

    @ModifyReturnValue(method = "maxBeaconBeamIterations", at = @At("RETURN"))
    private static int debugBeaconBeams(int original) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().maxBeaconBeamIterations : original;
    }

    @ModifyReturnValue(method = "load", at = @At("RETURN"))
    private static boolean load(boolean original) {
        return Amarong.shouldDoConfig && AmarongYACLCompat.HANDLER.load();
    }
}
