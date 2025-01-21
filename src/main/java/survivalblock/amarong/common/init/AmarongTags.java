package survivalblock.amarong.common.init;

import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.amarong.common.Amarong;

public class AmarongTags {

    public static class AmarongBlockTags {
        public static final TagKey<Block> AMARONG_HAMMER_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Amarong.id("amarong_hammer_mineable"));
    }

    public static class AmarongItemTags {
        public static final TagKey<Item> RAINBOW_CORE_GENERATORS = TagKey.of(RegistryKeys.ITEM, Amarong.id("rainbow_core_generators"));

        public static final TagKey<Item> TICKET_LAUNCHER_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Amarong.id("enchantable/ticket_launcher"));
        public static final TagKey<Item> VERYLONGSWORD_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Amarong.id("enchantable/verylongsword"));
        public static final TagKey<Item> DUCK_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Amarong.id("enchantable/somewhat_a_duck"));
        public static final TagKey<Item> HAMMER_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Amarong.id("enchantable/hammer"));

        public static final TagKey<Item> STICKS = TagKey.of(RegistryKeys.ITEM, Amarong.id("sticks"));

        public static final TagKey<Item> TWIRL_DAMAGE = TagKey.of(RegistryKeys.ITEM, Amarong.id("twirl_damage"));

        public static final TagKey<Item> STAFF_UNUSABLE = TagKey.of(RegistryKeys.ITEM, Amarong.id("staff_unusable"));
    }

    public static class AmarongDataComponentTypeTags {
        public static final TagKey<ComponentType<?>> STAFF_IGNORE = TagKey.of(RegistryKeys.DATA_COMPONENT_TYPE, Amarong.id("staff_ignore"));
    }

    public static class AmarongEnchantmentTags {
        public static final TagKey<Enchantment> PNUEMATIC_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("pnuematic_effect"));
        public static final TagKey<Enchantment> PARTICLE_ACCELERATOR_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("particle_accelerator_effect"));

        public static final TagKey<Enchantment> OBSCURE_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("obscure_effect"));
        public static final TagKey<Enchantment> RAILGUN_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("railgun_effect"));

        public static final TagKey<Enchantment> CAPACITY_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("capacity_effect"));

        public static final TagKey<Enchantment> VAULT_EFFECT = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("vault_effect"));

        public static final TagKey<Enchantment> EXCLUSIVE_SET_TICKET_LAUNCHER = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("exclusive_set/ticket_launcher"));
        public static final TagKey<Enchantment> EXCLUSIVE_SET_VERYLONGSWORD = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("exclusive_set/verylongsword"));

        public static final TagKey<Enchantment> POWER_LIKE = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("power_like"));
        public static final TagKey<Enchantment> INFINITY_LIKE = TagKey.of(RegistryKeys.ENCHANTMENT, Amarong.id("infinity_like"));

    }

    public static class AmarongEntityTypeTags {
        public static final TagKey<EntityType<?>> AMARONG_HITTABLE = TagKey.of(RegistryKeys.ENTITY_TYPE, Amarong.id("amarong_hittable"));
    }

}
