package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.common.Amarong;

public class AmarongParticleTypes {
    public static final SimpleParticleType RAILGUN_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, Amarong.id("railgun_particle"), RAILGUN_PARTICLE);
    }

}
