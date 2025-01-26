package survivalblock.amarong.common.compat.config;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum BeaconBeamDebugMode implements StringIdentifiable, TranslatableOption {

    NEVER("never"),
    ALWAYS("always"),
    ABNORMAL_ONLY("abnormal_only");

    private final String name;
    private final String translationKey;

    @SuppressWarnings("deprecation")
    public static final EnumCodec<BeaconBeamDebugMode> CODEC = StringIdentifiable.createCodec(BeaconBeamDebugMode::values);
    private static final IntFunction<BeaconBeamDebugMode> BY_ID = ValueLists.createIdToValueFunction(BeaconBeamDebugMode::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);

    BeaconBeamDebugMode(final String name) {
        this.name = name;
        this.translationKey = "amarong.yacl.option.enum.debugBeaconBeams." + name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    @SuppressWarnings("unused")
    public static BeaconBeamDebugMode getType(int type) {
        return BY_ID.apply(type);
    }

    @SuppressWarnings("unused")
    public static BeaconBeamDebugMode getType(String name) {
        return CODEC.byId(name, NEVER);
    }

    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }
}
