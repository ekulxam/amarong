package survivalblock.amarong.client.compat.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import dev.isxander.yacl3.impl.controller.EnumControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.IntegerFieldControllerBuilderImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import survivalblock.amarong.common.compat.config.AmarongYACLCompat;
import survivalblock.amarong.common.compat.config.BeaconBeamDebugMode;

@Environment(EnvType.CLIENT)
public class AmarongYACLConfigScreenCreator implements AmarongConfigScreenCreator {

    @Override
    public Screen create(Screen parent) {
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
                                .option(Option.<BeaconBeamDebugMode>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.enum.debugBeaconBeams"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.enum.debugBeaconBeams.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().debugBeaconBeams, () -> AmarongYACLCompat.HANDLER.instance().debugBeaconBeams, newVal -> AmarongYACLCompat.HANDLER.instance().debugBeaconBeams = newVal)
                                        .controller(option -> new EnumControllerBuilderImpl<>(option).enumClass(BeaconBeamDebugMode.class)) // crashes when not invoking the enumClass method
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("amarong.yacl.option.integer.maxBeaconBeamIterations"))
                                        .description(OptionDescription.of(Text.translatable("amarong.yacl.option.integer.maxBeaconBeamIterations.desc")))
                                        .binding(AmarongYACLCompat.HANDLER.defaults().maxBeaconBeamIterations, () -> AmarongYACLCompat.HANDLER.instance().maxBeaconBeamIterations, newVal -> AmarongYACLCompat.HANDLER.instance().maxBeaconBeamIterations = newVal)
                                        .controller(option -> new IntegerFieldControllerBuilderImpl(option).min(10))
                                        .build())
                                .build())
                        .build())
                .save(AmarongYACLCompat.HANDLER::save)
                .build()
                .generateScreen(parent);
    }
}
