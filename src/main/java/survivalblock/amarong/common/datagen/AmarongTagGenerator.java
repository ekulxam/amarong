package survivalblock.amarong.common.datagen;

import folk.sisby.twirl.Twirl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import survivalblock.amarong.common.init.*;
import survivalblock.atmosphere.atmospheric_api.not_mixin.damage_type.AtmosphericDamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class AmarongTagGenerator {

    public static class AmarongBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
        public AmarongBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(AmarongBlocks.AMARONG_CORE);
            getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(AmarongBlocks.AMARONG_CORE);

            getOrCreateTagBuilder(AmarongTags.AmarongBlockTags.AMARONG_HAMMER_MINEABLE).addTag(BlockTags.PICKAXE_MINEABLE);
        }
    }

    public static class AmarongItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public AmarongItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(ItemTags.SWORDS).add(AmarongItems.AMARONG_VERYLONGSWORD);

            getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE).add(AmarongItems.AMARONG_BOOMERANG);
            getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(AmarongItems.AMARONG_BOOMERANG);

            FabricTagProvider<Item>.FabricTagBuilder meleeWeapons = getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS);
            meleeWeapons.add(AmarongItems.AMARONG_BOOMERANG);
            meleeWeapons.add(AmarongItems.AMARONG_STAFF);

            FabricTagProvider<Item>.FabricTagBuilder rangedWeapons = getOrCreateTagBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS);
            rangedWeapons.add(AmarongItems.SOMEWHAT_A_DUCK);
            rangedWeapons.add(AmarongItems.TICKET_LAUNCHER);

            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.RAINBOW_CORE_GENERATORS).add(Items.GLOW_INK_SAC);

            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE).add(AmarongItems.TICKET_LAUNCHER);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE).add(AmarongItems.AMARONG_VERYLONGSWORD);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE).add(AmarongItems.SOMEWHAT_A_DUCK);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.HAMMER_ENCHANTABLE).add(AmarongItems.AMARONG_HAMMER);
            getOrCreateTagBuilder(ItemTags.MACE_ENCHANTABLE).add(AmarongItems.AMARONG_HAMMER);

            FabricTagProvider<Item>.FabricTagBuilder enchantables = getOrCreateTagBuilder(ConventionalItemTags.ENCHANTABLES);
            enchantables.addTag(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE);
            enchantables.addTag(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE);
            enchantables.addTag(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE);
            enchantables.addTag(AmarongTags.AmarongItemTags.HAMMER_ENCHANTABLE);

            getOrCreateTagBuilder(ItemTags.PICKAXES).add(AmarongItems.AMARONG_HAMMER);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.TWIRL_DAMAGE).add(AmarongItems.AMARONG_HAMMER);

            Registries.ITEM.getKey(AmarongItems.AMARONG_HAMMER).ifPresent((registryKey) -> {
                getOrCreateTagBuilder(Twirl.KEEP_USE).addOptional(registryKey);
                getOrCreateTagBuilder(Twirl.KEEP_TICK).addOptional(registryKey);
            });

            FabricTagProvider<Item>.FabricTagBuilder sticks = getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS);
            sticks.add(Items.STICK);
            sticks.add(Items.DEBUG_STICK);
            sticks.add(Items.END_ROD);
            sticks.add(Items.BREEZE_ROD);
            sticks.add(Items.BLAZE_ROD);
            sticks.add(Items.LIGHTNING_ROD);
        }
    }

    public static class AmarongDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
        public AmarongDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            FabricTagProvider<DamageType>.FabricTagBuilder bypassesArmor = getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR);
            FabricTagProvider<DamageType>.FabricTagBuilder bypassesCooldown = getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN);
            FabricTagProvider<DamageType>.FabricTagBuilder bypassesCreative = getOrCreateTagBuilder(AtmosphericDamageTypeTags.BYPASSES_CREATIVE);
            FabricTagProvider<DamageType>.FabricTagBuilder canBreakArmorStand = getOrCreateTagBuilder(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
            FabricTagProvider<DamageType>.FabricTagBuilder noKnockback = getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK);
            FabricTagProvider<DamageType>.FabricTagBuilder bypassesResistance = getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE);
            FabricTagProvider<DamageType>.FabricTagBuilder alwaysKillsArmorStands = getOrCreateTagBuilder(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);

            bypassesArmor.add(AmarongDamageTypes.WATER_STREAM_HIT);
            bypassesCooldown.add(AmarongDamageTypes.WATER_STREAM_HIT);
            bypassesCreative.add(AmarongDamageTypes.WATER_STREAM_HIT);
            canBreakArmorStand.add(AmarongDamageTypes.WATER_STREAM_HIT);
            noKnockback.add(AmarongDamageTypes.WATER_STREAM_HIT);

            bypassesCooldown.add(AmarongDamageTypes.FLYING_TICKET_HIT);
            bypassesResistance.add(AmarongDamageTypes.FLYING_TICKET_HIT);
            alwaysKillsArmorStands.add(AmarongDamageTypes.FLYING_TICKET_HIT);

            bypassesArmor.add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(AmarongDamageTypes.RAILGUN_HIT);
            bypassesCreative.add(AmarongDamageTypes.RAILGUN_HIT);
            bypassesResistance.add(AmarongDamageTypes.RAILGUN_HIT);
            noKnockback.add(AmarongDamageTypes.RAILGUN_HIT);
            alwaysKillsArmorStands.add(AmarongDamageTypes.RAILGUN_HIT);
            // no bypasses shield for funny
            // no bypasses wolf armor because I'm not that evil
        }
    }

    public static class AmarongDataComponentTypeTagGenerator extends FabricTagProvider<ComponentType<?>> {
        public AmarongDataComponentTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DATA_COMPONENT_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            FabricTagProvider<ComponentType<?>>.FabricTagBuilder staffIgnore = getOrCreateTagBuilder(AmarongTags.AmarongDataComponentTypeTags.STAFF_IGNORE);
            staffIgnore.add(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            staffIgnore.add(DataComponentTypes.CUSTOM_MODEL_DATA);
            staffIgnore.add(DataComponentTypes.CUSTOM_NAME);
            staffIgnore.add(DataComponentTypes.ITEM_NAME);
            staffIgnore.add(DataComponentTypes.RECIPES);
            staffIgnore.add(DataComponentTypes.DAMAGE);
            staffIgnore.add(DataComponentTypes.ENCHANTMENTS);
            staffIgnore.add(DataComponentTypes.FIRE_RESISTANT);
            staffIgnore.add(DataComponentTypes.MAX_DAMAGE);
            staffIgnore.add(DataComponentTypes.MAX_STACK_SIZE);
            staffIgnore.add(DataComponentTypes.BUNDLE_CONTENTS);
            staffIgnore.add(DataComponentTypes.CUSTOM_DATA);
            staffIgnore.add(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP);
            staffIgnore.add(DataComponentTypes.HIDE_TOOLTIP);
            staffIgnore.add(DataComponentTypes.FOOD);
            staffIgnore.add(DataComponentTypes.TOOL);
            staffIgnore.add(DataComponentTypes.UNBREAKABLE);
        }
    }

    public static class AmarongEnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {
        public AmarongEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            FabricTagProvider<Enchantment>.FabricTagBuilder pneumatic = getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT);
            pneumatic.add(AmarongEnchantments.PNEUMATIC);
            pneumatic.addOptional(Enchantments.WIND_BURST);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT).add(AmarongEnchantments.PARTICLE_ACCELERATOR);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT).add(AmarongEnchantments.OBSCURE);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT).add(AmarongEnchantments.RAILGUN);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.CAPACITY_EFFECT).add(AmarongEnchantments.CAPACITY);

            FabricTagProvider<Enchantment>.FabricTagBuilder ticketLauncherExclusiveSet = getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER);
            ticketLauncherExclusiveSet.add(AmarongEnchantments.PNEUMATIC);
            ticketLauncherExclusiveSet.add(AmarongEnchantments.PARTICLE_ACCELERATOR);
            FabricTagProvider<Enchantment>.FabricTagBuilder verylongswordExclusiveSet = getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD);
            verylongswordExclusiveSet.add(AmarongEnchantments.OBSCURE);
            verylongswordExclusiveSet.add(AmarongEnchantments.RAILGUN);

            FabricTagProvider<Enchantment>.FabricTagBuilder nonTreasure = getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE);
            FabricTagProvider<Enchantment>.FabricTagBuilder treasure = getOrCreateTagBuilder(EnchantmentTags.TREASURE);
            nonTreasure.add(AmarongEnchantments.PNEUMATIC);
            nonTreasure.add(AmarongEnchantments.PARTICLE_ACCELERATOR);
            treasure.add(AmarongEnchantments.OBSCURE);
            treasure.add(AmarongEnchantments.RAILGUN);
            nonTreasure.add(AmarongEnchantments.CAPACITY);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.POWER_LIKE).add(Enchantments.POWER);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.INFINITY_LIKE).add(Enchantments.INFINITY);
        }
    }

    public static class AmarongEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
        public AmarongEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(AmarongEntityTypes.FLYING_TICKET);
            getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(AmarongEntityTypes.WATER_STREAM);

            FabricTagProvider<EntityType<?>>.FabricTagBuilder amarongHittable = getOrCreateTagBuilder(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE);
            amarongHittable.add(EntityType.END_CRYSTAL);
            amarongHittable.forceAddTag(EntityTypeTags.IMPACT_PROJECTILES);
            amarongHittable.forceAddTag(ConventionalEntityTypeTags.BOATS);
            amarongHittable.forceAddTag(ConventionalEntityTypeTags.MINECARTS);
        }
    }
}
