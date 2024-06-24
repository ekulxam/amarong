package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.item.*;

import java.util.ArrayList;
import java.util.List;

public class AmarongItems {

    private static final float VERYLONGSWORD_REACH = 1.25f;

    public static List<Item> amarongItems = new ArrayList<>();

    public static final Item AMARONG_CHUNK = registerItem("amarong_chunk", new Item(new Item.Settings().maxCount(64)));
    public static final Item AMARONG_SHEET = registerItem("amarong_sheet", new Item(new Item.Settings().maxCount(64)));
    public static final Item KALEIDOSCOPE = registerItem("amarong_kaleidoscope", new KaleidescopeItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item AMARONG_VERYLONGSWORD = registerItem("amarong_verylongsword", new AmarongVerylongswordItem(AmarongToolMaterial.INSTANCE,
            (AmarongVerylongswordItem.Configuration) new AmarongVerylongswordItem.Configuration()
                    .attributeModifiers(AmarongVerylongswordItem.createAttributeModifiers(22.4F, 0.5F, VERYLONGSWORD_REACH + 0.5F, VERYLONGSWORD_REACH))));
    public static final Item TICKET_GUN = registerItem("amarong_ticket_dispenser", new TicketDispenserItem(new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.TICKETS, 0)));
    public static final Item SOMEWHAT_A_DUCK = registerItem("somewhat_a_duck", new WeirdDuckItem(new Item.Settings().maxCount(1).component(AmarongDataComponentTypes.WATERGUN, WeirdDuckItem.MAX_WATER).equipmentSlot((living, stack) -> EquipmentSlot.HEAD)));
    public static final Item AMARONG_CORE = registerBlockItem(AmarongBlocks.AMARONG_CORE, false);

    private static Item registerItem(String name, Item item) {
        amarongItems.add(item);
        return Registry.register(Registries.ITEM, Amarong.id(name), item);
    }

    @SuppressWarnings("SameParameterValue")
    private static Item registerBlockItem(Block block, boolean fireproof) {
        BlockItem item;
        if (fireproof) {
            item = new BlockItem(block, new Item.Settings().fireproof());
        } else {
            item = new BlockItem(block, new Item.Settings());
        }
        Identifier id = Registries.BLOCK.getId(block);
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        amarongItems.add(item);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static final ItemGroup AMARONG_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("amarong.itemGroup.amarong_group"))
            .icon(KALEIDOSCOPE::getDefaultStack).entries((displayContext, entries) -> {
                for (Item item : amarongItems) {
                    entries.add(item.getDefaultStack());
                }
            }).build();

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, Amarong.id("amarong_group"), AMARONG_GROUP);
    }
}
