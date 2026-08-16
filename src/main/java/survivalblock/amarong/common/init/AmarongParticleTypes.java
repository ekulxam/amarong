package survivalblock.amarong.common.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datafixer.AtmosphericCodecs;
import survivalblock.atmosphere.registrar.delayed.DelayedParticleTypeRegistrant;

public sealed interface AmarongParticleTypes permits AmarongParticleTypes.Dummy {
    DelayedParticleTypeRegistrant registrant = new DelayedParticleTypeRegistrant(Amarong::id);

    ParticleType<RailgunParticleEffect> RAILGUN_PARTICLE = registrant.register("railgun_particle", FabricParticleTypes.complex(RailgunParticleEffect.CODEC, RailgunParticleEffect.PACKET_CODEC));
    SimpleParticleType OBSCURE_GLOW = registrant.register("obscure_glow", FabricParticleTypes.simple(true));

    static void init() {
        registrant.consumeAll();
    }

    record RailgunParticleEffect(float pitch, float yaw) implements ParticleEffect {
        public static final MapCodec<RailgunParticleEffect> CODEC = AtmosphericCodecs.RCB.tuple(
                Codec.FLOAT.fieldOf("pitch"), parameters -> parameters.pitch,
                Codec.FLOAT.fieldOf("yaw"), parameters -> parameters.yaw,
                RailgunParticleEffect::new
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

    record Dummy() implements AmarongParticleTypes {
    }
}
