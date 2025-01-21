package survivalblock.amarong.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import survivalblock.amarong.common.Amarong;

public class AmarongYACLCompat {

    public static Screen create(Screen parent){
        if (!Amarong.shouldDoConfig) {
            throw new UnsupportedOperationException();
        }
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("amarong.yacl.category.main"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("amarong.yacl.category.main"))
                        .tooltip(Text.translatable("amarong.yacl.category.main.tooltip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("amarong.yacl.group.client"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.boolean.verboseLogging"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.boolean.verboseLogging.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().verboseLogging, () -> AmarongYACLCompat.HANDLER.instance().verboseLogging, newVal -> AmarongYACLCompat.HANDLER.instance().verboseLogging = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.boolean.twoHandedVerylongsword"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.boolean.twoHandedVerylongsword.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().twoHandedVerylongsword, () -> AmarongYACLCompat.HANDLER.instance().twoHandedVerylongsword, newVal -> AmarongYACLCompat.HANDLER.instance().twoHandedVerylongsword = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.boolean.noKaleidoscopeZoom"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.boolean.noKaleidoscopeZoom.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().noKaleidoscopeZoom, () -> AmarongYACLCompat.HANDLER.instance().noKaleidoscopeZoom, newVal -> AmarongYACLCompat.HANDLER.instance().noKaleidoscopeZoom = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.float.boomerangSpinMultiplier"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.float.boomerangSpinMultiplier.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().boomerangSpinMultiplier, () -> AmarongYACLCompat.HANDLER.instance().boomerangSpinMultiplier, newVal -> AmarongYACLCompat.HANDLER.instance().boomerangSpinMultiplier = newVal)
                                        .controller(FloatFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.float.staffRotationMultiplier"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.float.staffRotationMultiplier.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().staffRotationMultiplier, () -> AmarongYACLCompat.HANDLER.instance().staffRotationMultiplier, newVal -> AmarongYACLCompat.HANDLER.instance().staffRotationMultiplier = newVal)
                                        .controller(FloatFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(AmarongYACLCompat.HANDLER::save)
                .build()
                .generateScreen(parent);
    }

    public static final ConfigClassHandler<AmarongYACLCompat> HANDLER = ConfigClassHandler.createBuilder(AmarongYACLCompat.class)
            .id(Amarong.id("amarong"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("amarong.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

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
}
