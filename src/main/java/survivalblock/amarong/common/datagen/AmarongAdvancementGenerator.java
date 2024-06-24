package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.UsingItemCriterion;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.PlayerPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AmarongAdvancementGenerator extends FabricAdvancementProvider {
    protected AmarongAdvancementGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @SuppressWarnings("removal")
    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry obtainCore = Advancement.Builder.create().parent(Identifier.of("adventure/root"))
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.obtain_core.title"),
                        Text.translatable("advancements.amarong.obtain_core.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtain_core", InventoryChangedCriterion.Conditions.items(AmarongBlocks.AMARONG_CORE))
                .build(Amarong.id("obtain_core"));
        AdvancementEntry useKaleidoscope = Advancement.Builder.create().parent(obtainCore)
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.use_kaleidoscope.title"),
                        Text.translatable("advancements.amarong.use_kaleidoscope.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("use_kaleidoscope", createUsing(AmarongItems.KALEIDOSCOPE))
                .build(Amarong.id("use_kaleidoscope"));
        AdvancementEntry obtainVerylongsword = Advancement.Builder.create().parent(obtainCore)
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.obtain_verylongsword.title"),
                        Text.translatable("advancements.amarong.obtain_verylongsword.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtain_verylongsword", InventoryChangedCriterion.Conditions.items(AmarongItems.AMARONG_VERYLONGSWORD))
                .build(Amarong.id("obtain_verylongsword"));
        consumer.accept(useKaleidoscope);
        consumer.accept(obtainVerylongsword);
        consumer.accept(obtainCore);
    }



    @SuppressWarnings("SameParameterValue")
    private static AdvancementCriterion<UsingItemCriterion.Conditions> createUsing(Item item) {
        return UsingItemCriterion.Conditions.create(EntityPredicate.Builder.create().typeSpecific(PlayerPredicate.Builder.create().build()), ItemPredicate.Builder.create().items(item));
    }
}
