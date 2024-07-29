package survivalblock.amarong.common.compat.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
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
                                .build())
                        .build())
                .save(() -> AmarongYACLCompat.HANDLER.save())
                .build()
                .generateScreen(parent);
    }

    public static ConfigClassHandler<AmarongYACLCompat> HANDLER = ConfigClassHandler.createBuilder(AmarongYACLCompat.class)
            .id(Amarong.id("amarong"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("amarong.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean verboseLogging = true;
    @SerialEntry
    public boolean twoHandedVerylongsword = true;
    @SerialEntry
    public boolean noKaleidoscopeZoom = false;
}
