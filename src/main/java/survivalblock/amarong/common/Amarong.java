package survivalblock.amarong.common;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.amarong.common.init.*;

public class Amarong implements ModInitializer {

	public static final String MOD_ID = "amarong";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID.substring(0, 1).toUpperCase() + MOD_ID.substring(1).toLowerCase());

	@Override
	public void onInitialize() {
		AmarongDataComponentTypes.init();
		AmarongSounds.init();
		AmarongBlocks.init();
		AmarongItems.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}