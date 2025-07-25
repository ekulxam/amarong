package survivalblock.amarong.common.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datafixer.AtmosphericPacketCodecs;

public record DirectionalParticleS2CPayload(ParticleEffect particleEffect, double x, double y, double z, float pitch, float yaw, double deltaX, double deltaY, double deltaZ, double velocityX, double velocityY, double velocityZ) implements CustomPayload {

    public static final CustomPayload.Id<DirectionalParticleS2CPayload> ID = new CustomPayload.Id<>(Amarong.id("directional_particle_s2c"));

    public static final PacketCodec<RegistryByteBuf, DirectionalParticleS2CPayload> PACKET_CODEC = AtmosphericPacketCodecs.tuple(
            ParticleTypes.PACKET_CODEC, payload -> payload.particleEffect,
            PacketCodecs.DOUBLE, payload -> payload.x,
            PacketCodecs.DOUBLE, payload -> payload.y,
            PacketCodecs.DOUBLE, payload -> payload.z,
            PacketCodecs.FLOAT, payload -> payload.pitch,
            PacketCodecs.FLOAT, payload -> payload.yaw,
            PacketCodecs.DOUBLE, payload -> payload.deltaX,
            PacketCodecs.DOUBLE, payload -> payload.deltaY,
            PacketCodecs.DOUBLE, payload -> payload.deltaZ,
            PacketCodecs.DOUBLE, payload -> payload.velocityX,
            PacketCodecs.DOUBLE, payload -> payload.velocityY,
            PacketCodecs.DOUBLE, payload -> payload.velocityZ,
            DirectionalParticleS2CPayload::new
    );

    public DirectionalParticleS2CPayload(ParticleEffect particleEffect, double x, double y, double z, float pitch, float yaw, double deltaX, double deltaY, double deltaZ) {
        this(particleEffect, x, y, z, pitch, yaw, deltaX, deltaY, deltaZ, 0, 0, 0);
    }

    @Override
    public Id<? extends DirectionalParticleS2CPayload> getId() {
        return ID;
    }
}
