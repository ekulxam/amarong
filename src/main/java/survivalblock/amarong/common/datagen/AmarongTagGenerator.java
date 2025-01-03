package survivalblock.amarong.common.datagen;

import folk.sisby.twirl.Twirl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import survivalblock.amarong.common.Amarong;
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
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.RAINBOW_CORE_GENERATORS).add(Items.GLOW_INK_SAC);

            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.TICKET_LAUNCHER_ENCHANTABLE).add(AmarongItems.TICKET_LAUNCHER);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.VERYLONGSWORD_ENCHANTABLE).add(AmarongItems.AMARONG_VERYLONGSWORD);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.DUCK_ENCHANTABLE).add(AmarongItems.SOMEWHAT_A_DUCK);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.HAMMER_ENCHANTABLE).add(AmarongItems.AMARONG_HAMMER);
            getOrCreateTagBuilder(ItemTags.MACE_ENCHANTABLE).add(AmarongItems.AMARONG_HAMMER);
            getOrCreateTagBuilder(ItemTags.PICKAXES).add(AmarongItems.AMARONG_HAMMER);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.TWIRL_DAMAGE).add(AmarongItems.AMARONG_HAMMER);
            Registries.ITEM.getKey(AmarongItems.AMARONG_HAMMER).ifPresent((registryKey) -> {
                getOrCreateTagBuilder(Twirl.KEEP_USE).addOptional(registryKey);
                getOrCreateTagBuilder(Twirl.KEEP_TICK).addOptional(registryKey);
            });

            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.STICK);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.DEBUG_STICK);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.END_ROD);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.BREEZE_ROD);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.BLAZE_ROD);
            getOrCreateTagBuilder(AmarongTags.AmarongItemTags.STICKS).add(Items.LIGHTNING_ROD);
        }
    }

    public static class AmarongDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
        public AmarongDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).add(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(AtmosphericDamageTypeTags.BYPASSES_CREATIVE).add(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(DamageTypeTags.CAN_BREAK_ARMOR_STAND).add(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK).add(AmarongDamageTypes.WATER_STREAM_HIT);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).add(AmarongDamageTypes.FLYING_TICKET_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(AmarongDamageTypes.FLYING_TICKET_HIT);
            getOrCreateTagBuilder(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add(AmarongDamageTypes.FLYING_TICKET_HIT);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(AtmosphericDamageTypeTags.BYPASSES_CREATIVE).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK).add(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add(AmarongDamageTypes.RAILGUN_HIT);
            // no bypasses shield for funny
            // no bypasses wolf armor because I'm not that evil
        }
    }

    public static class AmarongEnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {
        public AmarongEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT).add(AmarongEnchantments.PNEUMATIC);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT).addOptional(Enchantments.WIND_BURST);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT).add(AmarongEnchantments.PARTICLE_ACCELERATOR);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT).add(AmarongEnchantments.OBSCURE);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT).add(AmarongEnchantments.RAILGUN);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.CAPACITY_EFFECT).add(AmarongEnchantments.CAPACITY);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.VAULT_EFFECT).add(AmarongEnchantments.VAULT);

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER).add(AmarongEnchantments.PNEUMATIC);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER).add(AmarongEnchantments.PARTICLE_ACCELERATOR);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD).add(AmarongEnchantments.OBSCURE);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD).add(AmarongEnchantments.RAILGUN);
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

            getOrCreateTagBuilder(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE).add(EntityType.END_CRYSTAL);
            getOrCreateTagBuilder(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE).forceAddTag(EntityTypeTags.IMPACT_PROJECTILES);
            getOrCreateTagBuilder(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE).forceAddTag(ConventionalEntityTypeTags.BOATS);
            getOrCreateTagBuilder(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE).forceAddTag(ConventionalEntityTypeTags.MINECARTS);
        }
    }
}
