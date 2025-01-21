package survivalblock.amarong.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

public class AmarongDataComponentTypes {

    public static final ComponentType<Integer> WATERGUN = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Integer> TICKETS = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Identifier> SHADER_TYPE = ComponentType.<Identifier>builder().codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC).build();
    public static final ComponentType<Integer> VERYLONGSWORD_CHARGE = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Boolean> RETAINS_CHARGE = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Boolean> NO_RAILGUN_DELAY = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<ItemStack> STAFF_STACK = ComponentType.<ItemStack>builder().codec(ItemStack.OPTIONAL_CODEC).packetCodec(ItemStack.OPTIONAL_PACKET_CODEC).build();

    public static void init() {
        // normal
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("watergun"), WATERGUN);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("tickets"), TICKETS);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("shader_type"), SHADER_TYPE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("verylongsword_charge"), VERYLONGSWORD_CHARGE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("pedestal_stack"), STAFF_STACK);

        // secret / unobtainable
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("retains_charge"), RETAINS_CHARGE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Amarong.id("no_railgun_delay"), NO_RAILGUN_DELAY);
    }
}
