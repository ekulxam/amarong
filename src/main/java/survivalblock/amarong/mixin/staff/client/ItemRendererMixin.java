package survivalblock.amarong.mixin.staff.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.client.AmarongClient;
import survivalblock.amarong.client.render.AmarongStaffTransformation;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.item.AmarongStaffItem;
import survivalblock.atmosphere.atmospheric_api.not_mixin.util.AtmosphericUtil;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow public abstract void renderItem(ItemStack stack, ModelTransformationMode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, @Nullable World world, int seed);

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V", shift = At.Shift.AFTER))
    private void renderStaffItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (!(stack.getItem() instanceof AmarongStaffItem staff)) {
            return;
        }
        ItemStack otherStack = staff.getItemStackFromComponents(stack);
        if (otherStack == null || otherStack.isEmpty()) {
            return;
        }
        matrices.push();
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        matrices.translate(0.5F, 1.75F, 0.5F);
        if (otherStack.getItem() instanceof ToolItem) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        }
        final boolean gui = ModelTransformationMode.GUI.equals(renderMode);
        boolean ground = false;
        AmarongStaffTransformation staffTransformation = AmarongClient.STAFF_TRANSFORMATIONS_MANAGER.getTransformation(otherStack);
        ModelTransformationMode mode = gui ? ModelTransformationMode.FIXED : staffTransformation.getMode();
        if (!gui) {
            ground = ModelTransformationMode.GROUND.equals(renderMode);
            PlayerEntity player = client.player;
            if (player != null) {
                if (amarong$isModeLeftHanded(renderMode) && amarong$isModeRightHanded(mode)) {
                    leftHanded = !leftHanded;
                } else if (amarong$isModeRightHanded(renderMode) && amarong$isModeLeftHanded(mode)) {
                    leftHanded = !leftHanded;
                }
            }
        }
        if (world != null) {
            amarong$rotateStaffItem(world, otherStack, gui, ground, leftHanded, matrices, staffTransformation);
        } else {
            staffTransformation.apply(leftHanded, matrices);
        }
        this.renderItem(otherStack, mode, light, overlay, matrices, vertexConsumers, world, 0);
        matrices.pop();
    }

    @Unique
    private void amarong$rotateStaffItem(ClientWorld world, ItemStack otherStack, boolean isModeGUI, boolean isModeGround, boolean leftHanded, MatrixStack matrices, AmarongStaffTransformation staffTransformation) {
        float multiplier = AmarongConfig.staffRotationMultiplier();
        if (AtmosphericUtil.isBasicallyEqual(multiplier, 0.0F)) {
            return;
        }
        final float time = world.getTime() * multiplier;
        final boolean spin = staffTransformation.spins();
        final boolean complexSpin = staffTransformation.usesComplexSpin(otherStack);
        if (isModeGUI || (spin && !complexSpin && !isModeGround)) {
            if (isModeGUI) {
                staffTransformation.applyWithoutRotation(leftHanded, matrices);
            } else {
                staffTransformation.apply(leftHanded, matrices);
            }
            final float timeMod360 = time % 360;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(timeMod360));
            return;
        }
        staffTransformation.apply(leftHanded, matrices);
        if (complexSpin) {
            final float timeMod360 = time % 360;
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(timeMod360));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(timeMod360));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(timeMod360));
            return;
        }
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
    }

    @Unique
    private boolean amarong$isModeLeftHanded(ModelTransformationMode mode) {
        return ModelTransformationMode.THIRD_PERSON_LEFT_HAND.equals(mode) || ModelTransformationMode.FIRST_PERSON_LEFT_HAND.equals(mode);
    }

    @Unique
    private boolean amarong$isModeRightHanded(ModelTransformationMode mode) {
        return ModelTransformationMode.THIRD_PERSON_RIGHT_HAND.equals(mode) || ModelTransformationMode.FIRST_PERSON_RIGHT_HAND.equals(mode);
    }
}
