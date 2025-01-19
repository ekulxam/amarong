package survivalblock.amarong.client.render;

import gay.lemmaeof.terrifictickets.TerrificTickets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import survivalblock.amarong.common.entity.FlyingTicketEntity;

public class FlyingTicketEntityRenderer extends EntityRenderer<FlyingTicketEntity> {

    private static final Identifier TEXTURE = Identifier.of(TerrificTickets.MODID, "textures/item/ticket.png");

    public FlyingTicketEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FlyingTicketEntity flyingTicket, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(flyingTicket.getYaw() - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(flyingTicket.getPitch() + 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
        matrices.translate(0, -0.3f, 0.05f);
        matrices.scale(1.5f, 1.5f, 1.5f);
        MinecraftClient.getInstance().getItemRenderer().renderItem(TerrificTickets.TICKET.getDefaultStack(), ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, flyingTicket.getWorld(), 0);
        matrices.pop();
        super.render(flyingTicket, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(FlyingTicketEntity entity) {
        return TEXTURE;
    }
}
