package survivalblock.amarong.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.apache.logging.log4j.util.TriConsumer;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.RegistryEntryLookupContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class AmarongEnchantments {

    public static final RegistryKey<Enchantment> CAPACITY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("capacity"));
    public static final RegistryKey<Enchantment> OBSCURE = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("obscure"));
    public static final RegistryKey<Enchantment> PARTICLE_ACCELERATOR = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("particle_accelerator"));
    public static final RegistryKey<Enchantment> PNEUMATIC = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("pneumatic"));
    public static final RegistryKey<Enchantment> RAILGUN = RegistryKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("railgun"));

    public static ImmutableMap<RegistryKey<Enchantment>, Enchantment> asEnchantments(RegistryEntryLookupContainer container) {
        ImmutableMap.Builder<RegistryKey<Enchantment>, Enchantment> enchantments = ImmutableMap.builder();
        RegistryEntryLookup<Enchantment> enchantmentRegistryEntryLookup = container.get(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<Item> itemRegistryEntryLookup = container.get(RegistryKeys.ITEM);

        TriConsumer<RegistryKey<Enchantment>, Enchantment.Definition, UnaryOperator<Enchantment.Builder>> registrar = (key, definition, operator) -> {
            enchantments.put(key, operator.apply(Enchantment.builder(definition)).build(key.getValue()));
        };

        registrar.accept(
                CAPACITY,
                Enchantment.definition(
                        itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE),
                        1,
                        4,
                        Enchantment.leveledCost(1, 8),
                        Enchantment.leveledCost(100, 8),
                        1,
                        AttributeModifierSlot.MAINHAND),
                UnaryOperator.identity()
        );
        registrar.accept(
                OBSCURE,
                Enchantment.definition(
                        itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                1,
                        AttributeModifierSlot.MAINHAND),
                builder -> builder.exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD))
        );
        registrar.accept(
                PARTICLE_ACCELERATOR,
                Enchantment.definition(
                        itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND),
                builder -> builder.exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER))
        );
        registrar.accept(
                PNEUMATIC,
                Enchantment.definition(
                        itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND),
                builder -> builder.exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER))
        );
        registrar.accept(
                RAILGUN,
                Enchantment.definition(
                        itemRegistryEntryLookup.getOrThrow(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(1),
                        Enchantment.constantCost(100),
                        1,
                        AttributeModifierSlot.MAINHAND),
                builder -> builder.exclusiveSet(enchantmentRegistryEntryLookup.getOrThrow(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD))
        );
        return enchantments.build();
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        for (Map.Entry<RegistryKey<Enchantment>, Enchantment> entry : asEnchantments(new RegistryEntryLookupContainer(registry)).entrySet()) {
            registry.register(entry.getKey(), entry.getValue());
        }
    }
}
