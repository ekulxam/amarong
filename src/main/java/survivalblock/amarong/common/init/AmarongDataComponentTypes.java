package survivalblock.amarong.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.delayed.DelayedDataComponentTypeRegistrant;

@SuppressWarnings("UnstableApiUsage")
public sealed interface AmarongDataComponentTypes permits AmarongDataComponentTypes.Dummy {
    DelayedDataComponentTypeRegistrant registrant = new DelayedDataComponentTypeRegistrant(Amarong::id);

    ComponentType<Integer> WATERGUN = registrant.register("watergun", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
    ComponentType<Integer> TICKETS = registrant.register("tickets", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
    ComponentType<Identifier> SHADER_TYPE = registrant.register("shader_type", ComponentType.<Identifier>builder().codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
    ComponentType<Integer> VERYLONGSWORD_CHARGE = registrant.register("verylongsword_charge", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
    ComponentType<ItemStack> STAFF_STACK = registrant.register("staff_stack", ComponentType.<ItemStack>builder().codec(ItemStack.OPTIONAL_CODEC).packetCodec(ItemStack.OPTIONAL_PACKET_CODEC));

    ComponentType<Boolean> RETAINS_CHARGE = registrant.register("retains_charge", ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));
    ComponentType<Boolean> NO_RAILGUN_DELAY = registrant.register("no_railgun_delay", ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));

    static void init() {
        registrant.consumeAll();
    }

    record Dummy() implements AmarongDataComponentTypes {
    }
}
