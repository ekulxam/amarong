package survivalblock.amarong.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.*;
import survivalblock.amarong.common.networking.DirectionalParticleS2CPayload;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;
import survivalblock.atmosphere.atmospheric_api.not_mixin.resource.AtmosphericResourceManagerHelper;

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

		final FabricLoader fabricLoader = FabricLoader.getInstance();
		if (!resetShouldDoConfig()) {
			LOGGER.warn("YACL is not installed, so Amarong's YACL Config will not be accessible!");
		} else {
			configLoaded = AmarongConfig.load();
			if (!configLoaded) {
				LOGGER.warn("Amarong YACL Config could not be loaded!");
			}
		}
		twirl = fabricLoader.isModLoaded("twirl");

		LootTableEvents.MODIFY.register(AmarongLootTableEvents.INSTANCE);

		fabricLoader.getModContainer(MOD_ID).ifPresent(Amarong::registerBuiltinDataPacks);

		PayloadTypeRegistry.playS2C().register(DirectionalParticleS2CPayload.ID, DirectionalParticleS2CPayload.PACKET_CODEC);
	}

	private static void registerBuiltinDataPacks(ModContainer modContainer) {
		AtmosphericResourceManagerHelper.registerBuiltinDataPack(AmarongUtil.EASY_CORE_DUPLICATION_PACK, modContainer, Text.translatable("dataPack.amarong.easy_core_duplication_pack.name"), ResourcePackActivationType.NORMAL);
		AtmosphericResourceManagerHelper.registerBuiltinDataPack(AmarongUtil.AMARONG_HAMMER_RECIPE_PACK, modContainer, Text.translatable("dataPack.amarong.amarong_hammer_recipe_pack.name"), ResourcePackActivationType.DEFAULT_ENABLED);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static boolean resetShouldDoConfig() {
		shouldDoConfig = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
		return shouldDoConfig;
	}
}