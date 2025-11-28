package survivalblock.amarong.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import survivalblock.amarong.common.Amarong;

public class AmarongYACLCompat implements AmarongConfig {

    public static final ConfigClassHandler<AmarongYACLCompat> HANDLER = ConfigClassHandler.createBuilder(AmarongYACLCompat.class)
            .id(Amarong.id("amarong"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("amarong.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    static AmarongConfig getYACLConfigInstance() {
        if (HANDLER.load()) {
            Amarong.configLoaded = true;
            return HANDLER.instance();
        }
        Amarong.LOGGER.warn("Amarong YACL Config could not be loaded!");
        return new AmarongConfig() {};
    }

    @SerialEntry
    public boolean verboseLogging = AmarongConfig.Defaults.VERBOSE_LOGGING;
    @SerialEntry
    public boolean twoHandedVerylongsword = AmarongConfig.Defaults.TWO_HANDED_VERYLONGSWORD;
    @SerialEntry
    public boolean noKaleidoscopeZoom = AmarongConfig.Defaults.NO_KALEIDOSCOPE_ZOOM;
    @SerialEntry
    public float boomerangSpinMultiplier = AmarongConfig.Defaults.BOOMERANG_SPIN_MULTIPLIER;
    @SerialEntry
    public float staffRotationMultiplier = AmarongConfig.Defaults.STAFF_ROTATION_MULTIPLIER;
    @SerialEntry
    public BeaconBeamDebugMode debugBeaconBeams = AmarongConfig.Defaults.DEBUG_BEACON_BEAMS;
    @SerialEntry
    public int maxBeaconBeamIterations = AmarongConfig.Defaults.MAX_BEACON_BEAM_ITERATIONS;

    @Override
    public boolean verboseLogging() {
        return this.verboseLogging;
    }

    @Override
    public boolean twoHandedVerylongsword() {
        return this.twoHandedVerylongsword;
    }

    @Override
    public boolean noKaleidoscopeZoom() {
        return this.noKaleidoscopeZoom;
    }

    @Override
    public float boomerangSpinMultiplier() {
        return this.boomerangSpinMultiplier;
    }

    @Override
    public float staffRotationMultiplier() {
        return this.staffRotationMultiplier;
    }

    @Override
    public BeaconBeamDebugMode debugBeaconBeams() {
        return this.debugBeaconBeams;
    }

    @Override
    public int maxBeaconBeamIterations() {
        return this.maxBeaconBeamIterations;
    }
}
