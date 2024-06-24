package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
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
        }
    }

    public static class AmarongBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
        public AmarongBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(AmarongBlocks.AMARONG_CORE);
            getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_STONE_TOOL).add(AmarongBlocks.AMARONG_CORE);
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
        }
    }
}
