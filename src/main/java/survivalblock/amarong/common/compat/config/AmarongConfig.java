package survivalblock.amarong.common.compat.config;

import net.minecraft.client.gui.screen.Screen;
import survivalblock.amarong.common.Amarong;

public class AmarongConfig {

    private static class Defaults {
        public static boolean verboseLogging = true;
        public static boolean twoHandedVerylongsword = true;
        public static boolean noKaleidoscopeZoom = false;
    }

    public static boolean verboseLogging() {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().verboseLogging : Defaults.verboseLogging;
    }

    public static boolean twoHandedVerylongsword() {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.HANDLER.instance().twoHandedVerylongsword : Defaults.twoHandedVerylongsword;
    }

    public static boolean noKaleidoscopeZoom() {
        return Amarong.shouldDoConfig ?AmarongYACLCompat.HANDLER.instance().noKaleidoscopeZoom : Defaults.noKaleidoscopeZoom;
    }

    public static Screen create(Screen parent) {
        return Amarong.shouldDoConfig ? AmarongYACLCompat.create(parent) : null;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean load() {
        return Amarong.shouldDoConfig && AmarongYACLCompat.HANDLER.load();
    }
}
