package survivalblock.amarong.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * A non-billboard particle
 * @see net.minecraft.client.particle.BillboardParticle
 */
public abstract class DirectionalParticle extends Particle {

    protected float scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
    protected float pitch = 0;
    protected float yaw = 0;

    protected DirectionalParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    protected DirectionalParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Quaternionf quaternionf = new Quaternionf()
                .rotationYXZ(-this.yaw * MathHelper.RADIANS_PER_DEGREE, this.pitch * MathHelper.RADIANS_PER_DEGREE, 0);
        RenderSystem.disableCull();
        this.render(vertexConsumer, camera, quaternionf, tickDelta);
        /*quaternionf.rotateY((float) -Math.PI);
        this.render(vertexConsumer, camera, quaternionf, tickDelta);
         */
    }

    protected void render(VertexConsumer vertexConsumer, Camera camera, Quaternionf quaternionf, float tickSomething) {
        Vec3d vec3d = camera.getPos();
        float x = (float)(MathHelper.lerp(tickSomething, this.prevPosX, this.x) - vec3d.getX());
        float y = (float)(MathHelper.lerp(tickSomething, this.prevPosY, this.y) - vec3d.getY());
        float z = (float)(MathHelper.lerp(tickSomething, this.prevPosZ, this.z) - vec3d.getZ());
        this.render(vertexConsumer, quaternionf, x, y, z, tickSomething);
    }

    protected void render(VertexConsumer vertexConsumer, Quaternionf quaternionf, float x, float y, float z, float tickSomething) {
        float size = this.getSize(tickSomething);
        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();
        int brightness = this.getBrightness(tickSomething);
        this.render(vertexConsumer, quaternionf, x, y, z, 1.0F, -1.0F, size, maxU, maxV, brightness);
        this.render(vertexConsumer, quaternionf, x, y, z, 1.0F, 1.0F, size, maxU, minV, brightness);
        this.render(vertexConsumer, quaternionf, x, y, z, -1.0F, 1.0F, size, minU, minV, brightness);
        this.render(vertexConsumer, quaternionf, x, y, z, -1.0F, -1.0F, size, minU, maxV, brightness);
    }

    private void render(
            VertexConsumer vertexConsumer, Quaternionf quaternionf, float x, float y, float z, float f, float g, float size, float l, float u, int v
    ) {
        Vector3f vector3f = new Vector3f(f, g, 0.0F).rotate(quaternionf).mul(size).add(x, y, z);
        vertexConsumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).texture(l, u).color(this.red, this.green, this.blue, this.alpha).light(v);
    }

    public float getSize(float tickDelta) {
        return this.scale;
    }

    @Override
    public Particle scale(float scale) {
        this.scale *= scale;
        return super.scale(scale);
    }

    protected abstract float getMinU();

    protected abstract float getMaxU();

    protected abstract float getMinV();

    protected abstract float getMaxV();
}
