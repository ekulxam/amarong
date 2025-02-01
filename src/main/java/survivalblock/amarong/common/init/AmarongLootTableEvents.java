package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import static net.minecraft.loot.LootTables.ANCIENT_CITY_CHEST;
import static net.minecraft.loot.LootTables.END_CITY_TREASURE_CHEST;
import static net.minecraft.loot.LootTables.TRIAL_CHAMBERS_SUPPLY_CHEST;

public class AmarongLootTableEvents implements LootTableEvents.Modify {

    public static final AmarongLootTableEvents INSTANCE = new AmarongLootTableEvents();

    @Override
    public void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (!source.isBuiltin()) {
            return;
        }
        RegistryWrapper.Impl<Enchantment> wrapper = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        LootPool.Builder builder = LootPool.builder();
        if (END_CITY_TREASURE_CHEST.equals(key)) {
            builder.rolls(ConstantLootNumberProvider.create(1.0F))
                    .with(ItemEntry.builder(Items.BOOK).weight(1)
                            .apply(new SetEnchantmentsLootFunction.Builder().enchantment(wrapper.getOrThrow(AmarongEnchantments.OBSCURE), ConstantLootNumberProvider.create(1.0F))))
                    .with(EmptyEntry.builder().weight(30));
            tableBuilder.pool(builder);
        } else if (ANCIENT_CITY_CHEST.equals(key)) {
            builder.rolls(ConstantLootNumberProvider.create(1.0F))
                    .with(ItemEntry.builder(Items.BOOK).weight(1)
                            .apply(new SetEnchantmentsLootFunction.Builder().enchantment(wrapper.getOrThrow(AmarongEnchantments.RAILGUN), ConstantLootNumberProvider.create(1.0F))))
                    .with(EmptyEntry.builder().weight(199));
            tableBuilder.pool(builder);
        }
    }
}
