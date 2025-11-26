package survivalblock.amarong.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.compat.AtmosphericMixinConfigPlugin;

public class AmarongMixinPlugin implements AtmosphericMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("compat")) {
            if (mixinClassName.contains("honque")) {
                return FabricLoader.getInstance().isModLoaded("honque");
            } else if (mixinClassName.contains("config")) {
                return Amarong.resetShouldDoConfig();
            } else if (mixinClassName.contains("twirl")) {
                return FabricLoader.getInstance().isModLoaded("twirl");
            }
        }
        return true;
    }
}
