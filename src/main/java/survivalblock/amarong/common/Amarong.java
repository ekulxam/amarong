package survivalblock.amarong.common;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.*;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;
import survivalblock.atmosphere.atmospheric_api.not_mixin.resource.AtmosphericResourceManagerHelper;

public class Amarong implements ModInitializer {

	public static final String MOD_ID = "amarong";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID.substring(0, 1).toUpperCase() + MOD_ID.substring(1).toLowerCase());

    public static final boolean YACL = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
    public static boolean configLoaded = false;
	public static final boolean TWIRL = FabricLoader.getInstance().isModLoaded("twirl");

    @Override
	public void onInitialize() {
        AmarongConfig.init();

		AmarongGameRules.init();
		AmarongDataComponentTypes.init();
		AmarongSounds.init();
		AmarongBlocks.init();
		AmarongItems.init();
		AmarongEntityTypes.init();
		KaleidoscopeShaderTypeRecipe.init();
		AmarongParticleTypes.init();

		LootTableEvents.MODIFY.register(AmarongLootTableEvents.INSTANCE);

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(Amarong::registerBuiltinDataPacks);
	}

	private static void registerBuiltinDataPacks(ModContainer modContainer) {
		AtmosphericResourceManagerHelper.registerBuiltinDataPack(AmarongUtil.EASY_CORE_DUPLICATION_PACK, modContainer, Text.translatable("dataPack.amarong.easy_core_duplication_pack.name"), ResourcePackActivationType.NORMAL);
		AtmosphericResourceManagerHelper.registerBuiltinDataPack(AmarongUtil.AMARONG_HAMMER_RECIPE_PACK, modContainer, Text.translatable("dataPack.amarong.amarong_hammer_recipe_pack.name"), ResourcePackActivationType.DEFAULT_ENABLED);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}