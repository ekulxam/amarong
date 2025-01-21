package survivalblock.amarong.client.render.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.RailgunEntity;

public class RailgunEntityRenderer extends EntityRenderer<RailgunEntity> {

    private static final Identifier TEXTURE = Amarong.id("textures/entity/water_stream.png");

    public RailgunEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RailgunEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(RailgunEntity entity) {
        return TEXTURE;
    }
}
