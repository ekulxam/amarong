package survivalblock.amarong.client.compat.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.client.AmarongClientUtil;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.compat.config.ConfigPackScreen;

@Environment(EnvType.CLIENT)
public class AmarongConfigScreen extends Screen {

    protected final Screen parent;
    protected ButtonWidget doneButton, yaclConfigButton, packConfigButton;

    public AmarongConfigScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    public AmarongConfigScreen(Screen parent) {
        this(Text.translatable("amarong.config.title"), parent);
    }

    @Override
    protected void init() {
        this.doneButton = this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).dimensions(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 300 + 8, 20).build()
        );
        this.yaclConfigButton = this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("amarong.yacl.category.main"), button -> {
                    if (this.client == null) {
                        return;
                    }
                    Screen screen = Amarong.shouldDoConfig ? create(this) : this;
                    //noinspection ConstantValue
                    if (screen == null || this.equals(screen)) {
                        return;
                    }
                    this.client.setScreen(screen);
                }).tooltip(getYACLFailTooltip()).dimensions(this.width / 2 - 50 - 100 - 4, this.height / 2 - 80, 300 + 8, 20).build()
        );
        this.packConfigButton = this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("amarong.config.resourcepack.title"), button -> {
                    if (this.client == null) {
                        return;
                    }
                    Screen screen = ConfigPackScreen.fromParent(this, Text.translatable("amarong.config.resourcepack.title"), AmarongClientUtil.AMARONG_RESOURCE_PACKS);
                    this.client.setScreen(screen);
                }).dimensions(this.width / 2 - 50 - 100 - 4, this.height / 2 - 50, 300 + 8, 20).build()
        );
        this.addDrawableChild(doneButton);
        this.addDrawableChild(yaclConfigButton);
        this.addDrawableChild(packConfigButton);
    }

    @Override
    public void close() {
        super.close();
        if (this.client != null) this.client.setScreen(this.parent);
    }

    public static Screen create(Screen parent) {
        return parent;
    }

    @Nullable
    public static Tooltip getYACLFailTooltip() {
        MutableText text = null;
        if (!Amarong.shouldDoConfig) {
            text = Text.stringifiedTranslatable("commands.amarongconfig.noyacl");
        } else if (!Amarong.configLoaded) {
            text = Text.stringifiedTranslatable("commands.amarongconfig.fail");
        }
        if (text == null) {
            return null;
        }
        return Tooltip.of(text.formatted(Formatting.RED));
    }
}
