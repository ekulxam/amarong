package survivalblock.amarong.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;

public class PhasingBoomerangEntityRenderer extends EntityRenderer<PhasingBoomerangEntity> {

    private static final Identifier TEXTURE = Amarong.id("textures/item/amarong_boomerang.png");

    public PhasingBoomerangEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(PhasingBoomerangEntity boomerang, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(boomerang.getYaw(tickDelta) * AmarongConfig.INSTANCE.boomerangSpinMultiplier()));
        ItemStack stack = boomerang.getItemStack().copy();
        if (boomerang.getBoomerangComponent().isEnchanted()) {
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        }
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, boomerang.getWorld(), 0);
        matrices.pop();
        super.render(boomerang, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(PhasingBoomerangEntity entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(PhasingBoomerangEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
