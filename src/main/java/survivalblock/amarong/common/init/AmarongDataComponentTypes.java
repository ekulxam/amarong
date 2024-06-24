package survivalblock.amarong.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.common.Amarong;

public class AmarongDataComponentTypes {

    public static final ComponentType<Integer> WATERGUN = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Integer> TICKETS = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("watergun"), WATERGUN);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("tickets"), TICKETS);
    }
}
