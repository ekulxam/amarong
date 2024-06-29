package survivalblock.amarong.common.compat.config;

import net.minecraft.client.gui.screen.Screen;
import survivalblock.amarong.common.Amarong;

public class AmarongConfig {

    private static class Defaults {
        public static boolean verboseLogging = true;
    }

    public static boolean verboseLogging() {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().verboseLogging : Defaults.verboseLogging;
    }

    public static Screen create(Screen parent) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.create(parent) : null;
    }

    public static boolean load() {
        return Amarong.shouldDoConfig && AmarongYACLCompat.HANDLER.load();
    }
}
