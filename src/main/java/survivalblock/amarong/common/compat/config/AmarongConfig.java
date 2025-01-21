package survivalblock.amarong.common.compat.config;

import net.minecraft.client.gui.screen.Screen;

public class AmarongConfig {

    public static final class Defaults {
        public static final boolean VERBOSE_LOGGING = true;
        public static final boolean TWO_HANDED_VERYLONGSWORD = true;
        public static final boolean NO_KALEIDOSCOPE_ZOOM = false;
        public static final float BOOMERANG_SPIN_MULTIPLIER = 12;
        public static final float STAFF_ROTATION_MULTIPLIER = 1.5f;
    }

    public static boolean verboseLogging() {
        return Defaults.VERBOSE_LOGGING;
    }

    public static boolean twoHandedVerylongsword() {
        return Defaults.TWO_HANDED_VERYLONGSWORD;
    }

    public static boolean noKaleidoscopeZoom() {
        return Defaults.NO_KALEIDOSCOPE_ZOOM;
    }

    public static float boomerangSpinMultiplier() {
        return Defaults.BOOMERANG_SPIN_MULTIPLIER;
    }

    public static float staffRotationMultiplier() {
        return Defaults.STAFF_ROTATION_MULTIPLIER;
    }

    @SuppressWarnings("unused")
    public static Screen create(Screen parent) {
        return null;
    }

    public static boolean load() {
        return false;
    }
}
