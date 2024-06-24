package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongEntityComponents;

@Mixin(value = GameRenderer.class, priority = 5000)
public abstract class GameRendererMixin {

    @Unique
    private static final Identifier[] SUPER_SECRET_SETTING_PROGRAMS = new Identifier[]{Identifier.ofVanilla("shaders/post/notch.json"), Identifier.ofVanilla("shaders/post/fxaa.json"), Identifier.ofVanilla("shaders/post/art.json"), Identifier.ofVanilla("shaders/post/bumpy.json"), Identifier.ofVanilla("shaders/post/blobs2.json"), Identifier.ofVanilla("shaders/post/pencil.json"), Identifier.ofVanilla("shaders/post/color_convolve.json"), Identifier.ofVanilla("shaders/post/deconverge.json"), Identifier.ofVanilla("shaders/post/flip.json"), Identifier.ofVanilla("shaders/post/invert.json"), Identifier.ofVanilla("shaders/post/ntsc.json"), Identifier.ofVanilla("shaders/post/outline.json"), Identifier.ofVanilla("shaders/post/phosphor.json"), Identifier.ofVanilla("shaders/post/scan_pincushion.json"), Identifier.ofVanilla("shaders/post/sobel.json"), Identifier.ofVanilla("shaders/post/bits.json"), Identifier.ofVanilla("shaders/post/desaturate.json"), Identifier.ofVanilla("shaders/post/green.json"), Identifier.ofVanilla("shaders/post/blur.json"), Identifier.ofVanilla("shaders/post/wobble.json"), Identifier.ofVanilla("shaders/post/blobs.json"), Identifier.ofVanilla("shaders/post/antialias.json"), Identifier.ofVanilla("shaders/post/creeper.json"), Identifier.ofVanilla("shaders/post/spider.json")};
    @Shadow
    protected abstract void loadPostProcessor(Identifier id);

    @WrapOperation(method = "onCameraEntitySet", constant = @Constant(classValue = EndermanEntity.class))
    private boolean notSoSuperSecretSettingsAnymore(Object object, Operation<Boolean> original, Entity entity) {
        boolean isAnEnderman = original.call(object);
        if (!isAnEnderman) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) {
                return false;
            }
            if (entity instanceof PlayerEntity player && AmarongEntityComponents.KALEIDOSCOPE_SHADER.get(player).isShaderApplied()) {
                client.execute(() -> {
                    Identifier shaderId = SUPER_SECRET_SETTING_PROGRAMS[MathHelper.nextInt(player.getRandom(), 0, SUPER_SECRET_SETTING_PROGRAMS.length - 1)];
                    this.loadPostProcessor(shaderId); // phosphor and wobble look coolest in my opinion
                    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                        Amarong.LOGGER.info("Loading shader {}", shaderId);
                    }
                });
            }
            return false;
        }
        return true;
    }

}
