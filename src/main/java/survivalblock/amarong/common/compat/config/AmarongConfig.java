package survivalblock.amarong.common.compat.config;

import survivalblock.amarong.common.Amarong;

public interface AmarongConfig {

    AmarongConfig INSTANCE = getInstance();

    default boolean verboseLogging() {
        return Defaults.VERBOSE_LOGGING;
    }

    default boolean twoHandedVerylongsword() {
        return Defaults.TWO_HANDED_VERYLONGSWORD;
    }

    default boolean noKaleidoscopeZoom() {
        return Defaults.NO_KALEIDOSCOPE_ZOOM;
    }

    default float boomerangSpinMultiplier() {
        return Defaults.BOOMERANG_SPIN_MULTIPLIER;
    }

    default float staffRotationMultiplier() {
        return Defaults.STAFF_ROTATION_MULTIPLIER;
    }

    default BeaconBeamDebugMode debugBeaconBeams() {
        return Defaults.DEBUG_BEACON_BEAMS;
    }

    default int maxBeaconBeamIterations() {
        return Defaults.MAX_BEACON_BEAM_ITERATIONS;
    }

    static AmarongConfig getInstance() {
        if (Amarong.YACL) {
            return AmarongYACLCompat.getYACLConfigInstance();
        }
        Amarong.LOGGER.warn("YACL is not installed, so Amarong's YACL Config will not be accessible!");
        return new AmarongConfig() {};
    }

    static void init() {
        // NO-OP
    }

    final class Defaults {
        private Defaults() {
        }

        public static final boolean VERBOSE_LOGGING = true;
        public static final boolean TWO_HANDED_VERYLONGSWORD = true;
        public static final boolean NO_KALEIDOSCOPE_ZOOM = false;
        public static final float BOOMERANG_SPIN_MULTIPLIER = 12;
        public static final float STAFF_ROTATION_MULTIPLIER = 1.5f;
        public static final BeaconBeamDebugMode DEBUG_BEACON_BEAMS = BeaconBeamDebugMode.NEVER;
        public static final int MAX_BEACON_BEAM_ITERATIONS = 256;
    }
}
