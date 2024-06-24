package survivalblock.amarong.client.render;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.WaterStreamEntity;

public class WaterStreamEntityRenderer extends EntityRenderer<WaterStreamEntity> {

    private static final Identifier TEXTURE = Amarong.id("textures/entity/water_stream.png");

    public WaterStreamEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(WaterStreamEntity entity) {
        return TEXTURE;
    }
}
