package survivalblock.amarong.common.init;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.dynamic.EnchantmentRegistrant;

public sealed interface AmarongEnchantments permits AmarongEnchantments.Dummy {

    EnchantmentRegistrant registrant = new EnchantmentRegistrant(Amarong::id);

    RegistryKey<Enchantment> CAPACITY = registrant.register(
            "capacity",
            creator -> creator
                    .define(
                            Enchantment.definition(
                                    creator.supportedItems(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE),
                                    1,
                                    4,
                                    Enchantment.leveledCost(1, 8),
                                    Enchantment.leveledCost(100, 8),
                                    1,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
    );
    RegistryKey<Enchantment> OBSCURE = registrant.register(
            "obscure",
            creator -> creator
                    .define(
                            Enchantment.definition(
                                    creator.supportedItems(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                                    1,
                                    1,
                                    Enchantment.constantCost(1),
                                    Enchantment.constantCost(100),
                                    1,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .exclusiveSet(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD)
    );
    RegistryKey<Enchantment> PARTICLE_ACCELERATOR = registrant.register(
            "particle_accelerator",
            creator -> creator
                    .define(
                            Enchantment.definition(
                                    creator.supportedItems(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                                    1,
                                    1,
                                    Enchantment.constantCost(1),
                                    Enchantment.constantCost(100),
                                    1,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .exclusiveSet(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER)
    );
    RegistryKey<Enchantment> PNEUMATIC = registrant.register(
            "pneumatic",
            creator -> creator
                    .define(
                            Enchantment.definition(
                                    creator.supportedItems(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE),
                                    1,
                                    1,
                                    Enchantment.constantCost(1),
                                    Enchantment.constantCost(100),
                                    1,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .exclusiveSet(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER)
    );
    RegistryKey<Enchantment> RAILGUN = registrant.register(
            "railgun",
            creator -> creator
                    .define(
                            Enchantment.definition(
                                    creator.supportedItems(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE),
                                    1,
                                    1,
                                    Enchantment.constantCost(1),
                                    Enchantment.constantCost(100),
                                    1,
                                    AttributeModifierSlot.MAINHAND
                            )
                    )
                    .exclusiveSet(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD)
    );

    record Dummy() implements AmarongEnchantments {
    }
}
