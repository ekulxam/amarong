package survivalblock.amarong.mixin.verylongsword.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.particle.GlowParticle;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.client.particle.ObscureGlowParticleFactory;

@Mixin(GlowParticle.class)
public class GlowParticleMixin {

    @Mixin(GlowParticle.GlowFactory.class)
    public static class GlowFactoryMixin {

        @WrapOperation(method = "createParticle(Lnet/minecraft/particle/SimpleParticleType;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/GlowParticle;setColor(FFF)V", ordinal = 0))
        private void copperObscureColorsInstead(GlowParticle instance, float red, float green, float blue, Operation<Void> original) {
            if (!((GlowParticle.GlowFactory) (Object) this instanceof ObscureGlowParticleFactory)) {
                original.call(instance, red, green, blue);
                return;
            }
            original.call(instance, 0.88f, 0.48f, 0.33f);
        }

        @WrapOperation(method = "createParticle(Lnet/minecraft/particle/SimpleParticleType;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/GlowParticle;setColor(FFF)V", ordinal = 1))
        private void amethystObscureColorsInstead(GlowParticle instance, float red, float green, float blue, Operation<Void> original) {
            if (!((GlowParticle.GlowFactory) (Object) this instanceof ObscureGlowParticleFactory)) {
                original.call(instance, red, green, blue);
                return;
            }
            original.call(instance, 0.69f, 0.68f, 0.95f);
        }
    }
}
