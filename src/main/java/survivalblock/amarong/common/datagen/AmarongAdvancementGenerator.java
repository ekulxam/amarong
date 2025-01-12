package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.UsingItemCriterion;
import net.minecraft.entity.EntityType;
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
                        AmarongItems.AMARONG_CORE,
                        Text.translatable("advancements.amarong.obtain_core.title"),
                        Text.translatable("advancements.amarong.obtain_core.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
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
                        AmarongItems.AMARONG_VERYLONGSWORD,
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
        AdvancementEntry whenTicketsFly = Advancement.Builder.create().parent(obtainCore)
                .display(
                        AmarongItems.TICKET_LAUNCHER,
                        Text.translatable("advancements.amarong.when_tickets_fly.title"),
                        Text.translatable("advancements.amarong.when_tickets_fly.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("when_tickets_fly", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(Amarong.id("when_tickets_fly"));
        AdvancementEntry kaleidoscopeSpider = Advancement.Builder.create().parent(useKaleidoscope)
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.spider.title"),
                        Text.translatable("advancements.amarong.spider.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(25))
                .criterion("spider", createLookingAtEntityUsing(EntityType.SPIDER, AmarongItems.KALEIDOSCOPE))
                .build(Amarong.id("spider"));
        AdvancementEntry kaleidoscopeCreeper = Advancement.Builder.create().parent(kaleidoscopeSpider)
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.creeper.title"),
                        Text.translatable("advancements.amarong.creeper.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(50))
                .criterion("creeper", createLookingAtEntityUsing(EntityType.CREEPER, AmarongItems.KALEIDOSCOPE))
                .build(Amarong.id("creeper"));
        AdvancementEntry kaleidoscopeEnderman = Advancement.Builder.create().parent(kaleidoscopeCreeper)
                .display(
                        AmarongItems.KALEIDOSCOPE,
                        Text.translatable("advancements.amarong.invert.title"),
                        Text.translatable("advancements.amarong.invert.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("invert", createLookingAtEntityUsing(EntityType.ENDERMAN, AmarongItems.KALEIDOSCOPE))
                .build(Amarong.id("invert"));
        AdvancementEntry hammerTime = Advancement.Builder.create().parent(obtainCore)
                .display(
                        AmarongItems.AMARONG_HAMMER,
                        Text.translatable("advancements.amarong.hammer_time.title"),
                        Text.translatable("advancements.amarong.hammer_time.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("hammer_time", InventoryChangedCriterion.Conditions.items(AmarongItems.AMARONG_HAMMER))
                .build(Amarong.id("hammer_time"));
        consumer.accept(useKaleidoscope);
        consumer.accept(obtainVerylongsword);
        consumer.accept(obtainCore);
        consumer.accept(whenTicketsFly);
        consumer.accept(kaleidoscopeSpider);
        consumer.accept(kaleidoscopeCreeper);
        consumer.accept(kaleidoscopeEnderman);
        consumer.accept(hammerTime);
    }



    @SuppressWarnings("SameParameterValue")
    private static AdvancementCriterion<UsingItemCriterion.Conditions> createUsing(Item item) {
        return UsingItemCriterion.Conditions.create(EntityPredicate.Builder.create().typeSpecific(PlayerPredicate.Builder.create().build()), ItemPredicate.Builder.create().items(item));
    }

    private static AdvancementCriterion<UsingItemCriterion.Conditions> createLookingAtEntityUsing(EntityType<?> entity, Item item) {
        return UsingItemCriterion.Conditions.create(EntityPredicate.Builder.create().typeSpecific(PlayerPredicate.Builder.create().lookingAt(EntityPredicate.Builder.create().type(entity)).build()), ItemPredicate.Builder.create().items(item));
    }
}
