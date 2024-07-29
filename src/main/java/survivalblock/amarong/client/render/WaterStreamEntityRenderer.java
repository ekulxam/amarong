package survivalblock.amarong.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.WaterStreamEntity;

public class WaterStreamEntityRenderer extends EntityRenderer<WaterStreamEntity> {

    private static final Identifier TEXTURE = Amarong.id("textures/entity/water_stream.png");

    public WaterStreamEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(WaterStreamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.isSubmergedInWater() && !(entity.horizontalCollision || entity.verticalCollision)) {
            entity.spawnParticles(ParticleTypes.BUBBLE);
        } else if (entity.horizontalCollision || entity.verticalCollision || entity.isOnGround() || entity.getGroundTime() > 0) {
            entity.spawnParticles(ParticleTypes.RAIN);
        } else if (!entity.isOnGround() && !(entity.getGroundTime() > 0)) {
            entity.spawnParticles(ParticleTypes.UNDERWATER);
        }
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(WaterStreamEntity entity) {
        return TEXTURE;
    }
}
