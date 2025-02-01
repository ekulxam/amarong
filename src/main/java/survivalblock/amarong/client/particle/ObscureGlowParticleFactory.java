package survivalblock.amarong.client.particle;

import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class ObscureGlowParticleFactory extends GlowParticle.GlowFactory {

    public ObscureGlowParticleFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
    }

    @Override
    public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return super.createParticle(simpleParticleType, clientWorld, d, e, f, g, h, i);
    }
}
