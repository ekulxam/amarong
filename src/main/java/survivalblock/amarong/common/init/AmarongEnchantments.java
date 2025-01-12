package survivalblock.amarong.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.RegistryEntryLookupContainer;

import java.util.HashMap;
import java.util.Map;

public class AmarongEnchantments {

    public static final RegistryKey<Enchantment> CAPACITY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("capacity"));
    public static final RegistryKey<Enchantment> OBSCURE = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("obscure"));
    public static final RegistryKey<Enchantment> PARTICLE_ACCELERATOR = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("particle_accelerator"));
    public static final RegistryKey<Enchantment> PNEUMATIC = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("pneumatic"));
    public static final RegistryKey<Enchantment> RAILGUN = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("railgun"));
    public static final RegistryKey<Enchantment> VAULT = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("vault"));

    public static ImmutableMap<RegistryKey<Enchantment>, Enchantment> asEnchantments(RegistryEntryLookupContainer container) {
        Map<RegistryKey<Enchantment>, Enchantment> enchantments = new HashMap<>();
        RegistryEntryLookup<Enchantment> enchantmentRegistryEntryLookup = container.get(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<Item> itemRegistryEntryLookup = container.get(RegistryKeys.ITEM);
        enchantments.put(CAPACITY, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE),
                        1,
                        4,
                        Enchantment.leveledCost(1, 8),
                        Enchantment.leveledCost(100, 8),
                        1,
                        AttributeModifierSlot.MAINHAND)).build(CAPACITY.getValue()));
        enchantments.put(OBSCURE, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                1,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD))
                .build(OBSCURE.getValue()));
        enchantments.put(PARTICLE_ACCELERATOR, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER))
                .build(PARTICLE_ACCELERATOR.getValue()));
        enchantments.put(PNEUMATIC, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER))
                .build(PNEUMATIC.getValue()));
        enchantments.put(RAILGUN, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD))
                .build(RAILGUN.getValue()));
        enchantments.put(VAULT, Enchantment.builder(Enchantment.definition(
                itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.HAMMER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND)).build(VAULT.getValue()));
        return ImmutableMap.copyOf(enchantments);
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        for (Map.Entry<RegistryKey<Enchantment>, Enchantment> entry : asEnchantments(new RegistryEntryLookupContainer(registry)).entrySet()) {
            registry.register(entry.getKey(), entry.getValue());
        }
    }
}
