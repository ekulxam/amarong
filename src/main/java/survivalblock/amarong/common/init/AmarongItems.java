package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.item.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AmarongItems {

    private static final float AMARONG_TOOL_REACH = 1.25f;

    public static final List<Item> AMARONG_ITEMS = new ArrayList<>();

    public static final Item AMARONG_CHUNK = registerItem("amarong_chunk", new Item(new Item.Settings().maxCount(64)));
    public static final Item AMARONG_SHEET = registerItem("amarong_sheet", new Item(new Item.Settings().maxCount(64)));
    public static final Item KALEIDOSCOPE = registerItem("amarong_kaleidoscope", new KaleidoscopeItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item AMARONG_VERYLONGSWORD = registerItem("amarong_verylongsword", new AmarongVerylongswordItem(AmarongToolMaterial.INSTANCE,
            new AmarongToolMaterial.Configuration()
                    .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(22.4F, 0.5F, AMARONG_TOOL_REACH + 0.5F, AMARONG_TOOL_REACH))
    ));
    public static final Item AMARONG_HAMMER = registerItem("amarong_hammer", new AmarongHammerItem(AmarongToolMaterial.INSTANCE,
            new AmarongToolMaterial.Configuration()
                    .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(6.0F, 0.6F, AMARONG_TOOL_REACH + 0.75F, AMARONG_TOOL_REACH + 0.5F))
                    .rarity(Rarity.EPIC)
    ));

    public static final Item TICKET_LAUNCHER = registerItem("amarong_ticket_dispenser", new TicketLauncherItem(new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.TICKETS, 0)));
    public static final Item SOMEWHAT_A_DUCK = registerItem("somewhat_a_duck", new SomewhatADuckItem(new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.WATERGUN, SomewhatADuckItem.MAX_WATER).equipmentSlot((living, stack) -> EquipmentSlot.HEAD)));
    public static final Item AMARONG_CORE = registerBlockItem(AmarongBlocks.AMARONG_CORE, new Item.Settings());

    public static final Item AMARONG_BOOMERANG = registerItem("amarong_boomerang", new AmarongBoomerangItem(AmarongToolMaterial.INSTANCE, new AmarongToolMaterial.Configuration()
            .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(4.0F, 1.6F, AMARONG_TOOL_REACH, AMARONG_TOOL_REACH))
    ));

    private static Item registerItem(String name, Item item) {
        AMARONG_ITEMS.add(item);
        return Registry.register(Registries.ITEM, Amarong.id(name), item);
    }

    @SuppressWarnings("SameParameterValue")
    private static Item registerBlockItem(Block block, Item.Settings settings) {
        BlockItem blockItem = new BlockItem(block, settings);
        blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        AMARONG_ITEMS.add(blockItem);
        return Registry.register(Registries.ITEM, Registries.BLOCK.getId(block), blockItem);
    }

    public static final ItemGroup AMARONG_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("amarong.itemGroup.amarong_group"))
            .icon(KALEIDOSCOPE::getDefaultStack).entries((displayContext, entries) -> {
                for (Item item : AMARONG_ITEMS) {
                    entries.add(item.getDefaultStack());
                    if (item.equals(KALEIDOSCOPE)) {
                        for (Identifier id : KaleidoscopeItem.SUPER_SECRET_SETTING_PROGRAMS) {
                            ItemStack stack = new ItemStack(KALEIDOSCOPE);
                            stack.set(AmarongDataComponentTypes.SHADER_TYPE, id);
                            entries.add(stack);
                        }
                    } else if (item.equals(AMARONG_VERYLONGSWORD)) {
                        addEnchantedStack(item, displayContext, "amarong:obscure", entries);
                        addEnchantedStack(item, displayContext, "amarong:railgun", entries);
                    } else if (item.equals(TICKET_LAUNCHER)) {
                        addEnchantedStack(item, displayContext, "amarong:pneumatic", entries);
                        addEnchantedStack(item, displayContext, "amarong:particle_accelerator", entries);
                    } else if (item.equals(SOMEWHAT_A_DUCK)) {
                        addEnchantedStack(item, displayContext, "amarong:capacity", entries);
                    } else if (item.equals(AMARONG_HAMMER)) {
                        addEnchantedStack(item, displayContext, "amarong:vault", entries);
                        if (Amarong.twirl) addEnchantedStack(item, displayContext, "twirl:twirling", entries);
                    }
                }
            }).build();

    private static void addEnchantedStack(Item item, ItemGroup.DisplayContext displayContext, String enchantmentId, ItemGroup.Entries entries) {
        try {
            ItemStack stack = new ItemStack(item);
            AtomicReference<RegistryEntry<Enchantment>> reference = new AtomicReference<>(null);
            displayContext.lookup().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).streamEntries().forEach((enchantmentReference) -> {
                if (enchantmentReference.matchesId(Identifier.of(enchantmentId))) {
                    reference.set(enchantmentReference);
                }
            });
            Objects.requireNonNull(reference.get());
            RegistryEntry<Enchantment> enchantmentEntry = reference.get();
            if (enchantmentEntry.value().isAcceptableItem(stack)) {
                stack.addEnchantment(reference.get(), reference.get().value().getMaxLevel());
                if (item.equals(AMARONG_VERYLONGSWORD)) {
                    stack.set(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE, AmarongVerylongswordItem.getMaxCharge(stack));
                } else if (item.equals(SOMEWHAT_A_DUCK)) {
                    stack.set(AmarongDataComponentTypes.WATERGUN, SomewhatADuckItem.getMaxWater(stack));
                }
                entries.add(stack);
            } else if (AmarongConfig.verboseLogging()) {
                Amarong.LOGGER.error("Avoided adding an ItemStack of {} because enchantment amarong:{} does not support that item", Registries.ITEM.getId(item), enchantmentId);
            }
        } catch (Throwable throwable) {
            try {
                if (AmarongConfig.verboseLogging()) {
                    Amarong.LOGGER.error("Unable to add an ItemStack of {} because of an error when getting enchantment {}", Registries.ITEM.getId(item), enchantmentId, throwable);
                }
            } catch (Throwable throwable1) {
                Amarong.LOGGER.error("There was an error while getting enchantment {}", enchantmentId, throwable);
                Amarong.LOGGER.error("There was an error while trying to get an item's id from the registry!", throwable1);
            }
        }
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, Amarong.id("amarong_group"), AMARONG_GROUP);
    }
}
