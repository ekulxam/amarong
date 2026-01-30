package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.item.*;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.ItemRegistrant;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.delayed.DelayedItemGroupRegistrant;

import java.util.function.Consumer;

import static survivalblock.atmosphere.atmospheric_api.not_mixin.item.CreativeTabEnchantmentAdder.addEnchantedStack;

@SuppressWarnings("UnstableApiUsage")
public sealed interface AmarongItems permits AmarongItems.Dummy {

    float AMARONG_TOOL_REACH = 1.25f;
    ItemRegistrant registrant = new ItemRegistrant(Amarong::id);
    DelayedItemGroupRegistrant itemGroupRegistrant = new DelayedItemGroupRegistrant(Amarong::id);

    Item AMARONG_CHUNK = registrant.register("amarong_chunk", Item::new, new Item.Settings().maxCount(64));

    Item AMARONG_SHEET = registrant.register("amarong_sheet", Item::new, new Item.Settings().maxCount(64));

    Item KALEIDOSCOPE = registrant.register("amarong_kaleidoscope", KaleidoscopeItem::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

    Item AMARONG_VERYLONGSWORD = registrant.register("amarong_verylongsword", settings -> new AmarongVerylongswordItem(AmarongToolMaterial.INSTANCE, settings),
            new AmarongToolMaterial.Configuration()
                    .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(22.4F, 0.4F, AMARONG_TOOL_REACH + 0.5F, AMARONG_TOOL_REACH))
    );

    Item AMARONG_HAMMER = registrant.register("amarong_hammer", settings -> new AmarongHammerItem(AmarongToolMaterial.INSTANCE, settings),
            new AmarongToolMaterial.Configuration()
                    .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(6.0F, 0.6F, AMARONG_TOOL_REACH + 0.75F, AMARONG_TOOL_REACH + 0.5F))
                    .rarity(Rarity.EPIC)
    );

    Item TICKET_LAUNCHER = registrant.register("amarong_ticket_dispenser", TicketLauncherItem::new, new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.TICKETS, 0));

    Item SOMEWHAT_A_DUCK = registrant.register("somewhat_a_duck", SomewhatADuckItem::new, new Item.Settings().maxCount(1)
            .component(AmarongDataComponentTypes.WATERGUN, SomewhatADuckItem.MAX_WATER).equipmentSlot((living, stack) -> EquipmentSlot.HEAD));

    Item AMARONG_CORE = registrant.register(AmarongBlocks.AMARONG_CORE, new Item.Settings());

    Item AMARONG_BOOMERANG = registrant.register("amarong_boomerang", settings -> new AmarongBoomerangItem(AmarongToolMaterial.INSTANCE, settings), new AmarongToolMaterial.Configuration()
            .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(4.0F, 1.6F, AMARONG_TOOL_REACH, AMARONG_TOOL_REACH))
    );

    Item AMARONG_STAFF = registrant.register("amarong_staff", AmarongStaffItem::new, new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.STAFF_STACK, ItemStack.EMPTY)
            .attributeModifiers(AmarongStaffItem.createAttributeModifiers(8f, 7.6f))
    );

    @SuppressWarnings("unused")
    ItemGroup AMARONG_GROUP = itemGroupRegistrant.register(
            "amarong_group",
            FabricItemGroup.builder()
                    .displayName(Text.translatable("amarong.itemGroup.amarong_group"))
                    .icon(KALEIDOSCOPE::getDefaultStack).entries((displayContext, entries) -> {
                        for (Item item : Registries.ITEM.streamEntries()
                                .filter(
                                        reference -> reference.registryKey()
                                                .getValue()
                                                .getNamespace()
                                                .equals(Amarong.MOD_ID)
                            ).map(RegistryEntry.Reference::value)
                            .toList()
                        ) {
                            entries.add(item.getDefaultStack());
                            if (item.equals(KALEIDOSCOPE)) {
                                for (Identifier id : KaleidoscopeItem.SUPER_SECRET_SETTING_PROGRAMS) {
                                    ItemStack stack = new ItemStack(KALEIDOSCOPE);
                                    stack.set(AmarongDataComponentTypes.SHADER_TYPE, id);
                                    entries.add(stack);
                                }
                            } else if (item.equals(AMARONG_VERYLONGSWORD)) {
                                final Consumer<ItemStack> longswordCharge = stack -> stack.set(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE, AmarongVerylongswordItem.getMaxCharge(stack));
                                addEnchantedStack(item, displayContext, "amarong:obscure", entries, longswordCharge);
                                addEnchantedStack(item, displayContext, "amarong:railgun", entries, longswordCharge);
                            } else if (item.equals(TICKET_LAUNCHER)) {
                                addEnchantedStack(item, displayContext, "amarong:pneumatic", entries);
                                addEnchantedStack(item, displayContext, "amarong:particle_accelerator", entries);
                            } else if (item.equals(SOMEWHAT_A_DUCK)) {
                                final Consumer<ItemStack> water = stack -> stack.set(AmarongDataComponentTypes.WATERGUN, SomewhatADuckItem.getMaxWater(stack));
                                addEnchantedStack(item, displayContext, "amarong:capacity", entries, water);
                            } else if (item.equals(AMARONG_HAMMER)) {
                                if (Amarong.TWIRL) addEnchantedStack(item, displayContext, "twirl:twirling", entries);
                            }
                        }
                    })
    );

    static void init() {
        itemGroupRegistrant.consumeAll();
    }

    record Dummy() implements AmarongItems {
    }
}
