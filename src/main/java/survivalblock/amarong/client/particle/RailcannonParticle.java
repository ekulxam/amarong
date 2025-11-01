package survivalblock.amarong.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import survivalblock.amarong.common.init.AmarongParticleTypes;
import survivalblock.atmosphere.atmospheric_api.not_mixin.particle.client.SpriteDirectionalParticle;

public class RailcannonParticle extends SpriteDirectionalParticle {

    private final SpriteProvider spriteProvider;

    protected RailcannonParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.maxAge = 16;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.red = f;
        this.green = f;
        this.blue = f;
        this.scale = 1.5F;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<AmarongParticleTypes.RailgunParticleEffect> {

        public Particle createParticle(AmarongParticleTypes.RailgunParticleEffect parameters, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            RailcannonParticle particle = new RailcannonParticle(clientWorld, x, y, z, this.spriteProvider);
            particle.pitch = parameters.pitch();
            particle.yaw = parameters.yaw();
            return particle;
        }
    }
}
