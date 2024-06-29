package survivalblock.amarong.common.datagen;

import folk.sisby.twirl.Twirl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.init.AmarongTags;

import java.util.concurrent.CompletableFuture;

public class AmarongTagGenerator {

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

    public static class AmarongBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
        public AmarongBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(AmarongBlocks.AMARONG_CORE);
            getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(AmarongBlocks.AMARONG_CORE);
        }
    }

    public static class AmarongDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
        public AmarongDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).addOptional(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).addOptional(AmarongDamageTypes.WATER_STREAM_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).addOptional(AmarongDamageTypes.FLYING_TICKET_HIT);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).addOptional(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).addOptional(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).addOptional(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).addOptional(AmarongDamageTypes.RAILGUN_HIT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).addOptional(AmarongDamageTypes.RAILGUN_HIT);
            // no bypasses shield because funny
        }
    }

    public static class AmarongEnchantmentTagGenerator extends FabricTagProvider.EnchantmentTagProvider {
        public AmarongEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT).addOptional(Amarong.id("pnuematic"));
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT).addOptional(Enchantments.WIND_BURST);
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT).addOptional(Amarong.id("particle_accelerator"));

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT).addOptional(Amarong.id("obscure"));
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT).addOptional(Amarong.id("railgun"));

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.CAPACITY_EFFECT).addOptional(Amarong.id("capacity"));

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.VAULT_EFFECT).addOptional(Amarong.id("vault"));

            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER).addOptional(Amarong.id("pnuematic"));
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_TICKET_LAUNCHER).addOptional(Amarong.id("particle_accelerator"));
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD).addOptional(Amarong.id("obscure"));
            getOrCreateTagBuilder(AmarongTags.AmarongEnchantmentTags.EXCLUSIVE_SET_VERYLONGSWORD).addOptional(Amarong.id("railgun"));
        }
    }
}
