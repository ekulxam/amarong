package survivalblock.amarong.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.*;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;

public class Amarong implements ModInitializer {

	public static final String MOD_ID = "amarong";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID.substring(0, 1).toUpperCase() + MOD_ID.substring(1).toLowerCase());
	public static boolean shouldDoConfig = false;
	public static boolean configLoaded = false;
	public static boolean twirl = false;

    @Override
	public void onInitialize() {
		AmarongGameRules.init();
		AmarongDataComponentTypes.init();
		AmarongSounds.init();
		AmarongBlocks.init();
		AmarongItems.init();
		AmarongEntityTypes.init();
		KaleidoscopeShaderTypeRecipe.init();
		AmarongParticleTypes.init();
		if (!resetShouldDoConfig()) {
			LOGGER.warn("YACL is not installed, so Amarong's Config will not be accessible!");
		} else {
			configLoaded = AmarongConfig.load();
			if (!configLoaded) {
				LOGGER.warn("Amarong Config could not be loaded!");
			}
		}
		twirl = FabricLoader.getInstance().isModLoaded("twirl");
		LootTableEvents.MODIFY.register(AmarongLootTableEvents.INSTANCE);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static boolean resetShouldDoConfig() {
		shouldDoConfig = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
		return shouldDoConfig;
	}
}