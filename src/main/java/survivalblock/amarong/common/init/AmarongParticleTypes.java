package survivalblock.amarong.common.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.client.render.AmarongStaffTransformation;
import survivalblock.amarong.common.Amarong;

public class AmarongParticleTypes {
    public static final ParticleType<RailgunParticleEffect> RAILGUN_PARTICLE = FabricParticleTypes.complex(RailgunParticleEffect.CODEC, RailgunParticleEffect.PACKET_CODEC);
    public static final SimpleParticleType OBSCURE_GLOW = FabricParticleTypes.simple(true);

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, Amarong.id("railgun_particle"), RAILGUN_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Amarong.id("obscure_glow"), OBSCURE_GLOW);
    }

    public record RailgunParticleEffect(float pitch, float yaw) implements ParticleEffect {
        public static final MapCodec<RailgunParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.FLOAT.fieldOf("pitch").forGetter(parameters -> parameters.pitch),
                                Codec.FLOAT.fieldOf("yaw").forGetter(parameters -> parameters.yaw)
                        )
                        .apply(instance, RailgunParticleEffect::new)
        );
        public static final PacketCodec<RegistryByteBuf, RailgunParticleEffect> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.FLOAT, parameters -> parameters.pitch,
                PacketCodecs.FLOAT, parameters -> parameters.yaw,
                RailgunParticleEffect::new
        );

        @Override
        public ParticleType<RailgunParticleEffect> getType() {
            return RAILGUN_PARTICLE;
        }
    }
}
