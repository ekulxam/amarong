package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongEntityComponents;
import survivalblock.amarong.common.init.AmarongItems;

import static survivalblock.amarong.client.AmarongClientUtil.SUPER_SECRET_SETTING_PROGRAMS;

@Mixin(value = GameRenderer.class, priority = 5000)
public abstract class GameRendererMixin {

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
                    Identifier shaderId = SUPER_SECRET_SETTING_PROGRAMS.get(MathHelper.nextInt(player.getRandom(), 0, SUPER_SECRET_SETTING_PROGRAMS.size() - 1));
                    ItemStack stack = player.getActiveItem();
                    if (stack.getItem().equals(AmarongItems.KALEIDOSCOPE) && stack.contains(AmarongDataComponentTypes.SHADER_TYPE)) {
                        shaderId = stack.get(AmarongDataComponentTypes.SHADER_TYPE);
                    }
                    try {
                        this.loadPostProcessor(shaderId); // phosphor and wobble look coolest in my opinion
                    } catch (Throwable throwable) {
                        if (AmarongConfig.verboseLogging()) {
                            Amarong.LOGGER.error("Failed to load shader due to an exception!", throwable);
                        }
                    }
                    if (AmarongConfig.verboseLogging()) {
                        Amarong.LOGGER.info("Loading shader {}", shaderId);
                    }
                });
            }
            return false;
        }
        return true;
    }

}
