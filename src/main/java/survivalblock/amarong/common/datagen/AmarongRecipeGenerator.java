package survivalblock.amarong.common.datagen;

import gay.lemmaeof.terrifictickets.TerrificTickets;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;

import java.util.concurrent.CompletableFuture;

public class AmarongRecipeGenerator extends FabricRecipeProvider {

    public AmarongRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, AmarongItems.AMARONG_SHEET, 2).input(AmarongItems.AMARONG_CHUNK).criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_SHEET),
                FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_SHEET)).criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_CHUNK),
                FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_CHUNK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, AmarongItems.KALEIDOSCOPE).pattern(" x ").pattern(" | ").pattern(" v ")
                .input('x', AmarongItems.AMARONG_SHEET)
                .input('|', Items.SPYGLASS)
                .input('v', AmarongItems.AMARONG_CHUNK)
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_SHEET),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_SHEET))
                .criterion(FabricRecipeProvider.hasItem(Items.SPYGLASS),
                        FabricRecipeProvider.conditionsFromItem(Items.SPYGLASS))
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_CHUNK),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_CHUNK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, AmarongItems.AMARONG_VERYLONGSWORD).pattern(" xw").pattern("vcx").pattern("sv ")
                .input('s', Items.NETHERITE_SWORD)
                .input('v', Items.COPPER_INGOT)
                .input('c', AmarongItems.AMARONG_CHUNK)
                .input('x', AmarongItems.AMARONG_SHEET)
                .input('w', Items.AMETHYST_SHARD)
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_SWORD),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_SWORD))
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_SHEET),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_SHEET))
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_SHEET),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, AmarongItems.SOMEWHAT_A_DUCK).pattern(" o ").pattern("sus").pattern(" _ ")
                .input('o', Items.DISPENSER)
                .input('s', Items.GOLD_BLOCK)
                .input('u', Items.WATER_BUCKET)
                .input('_', AmarongItems.AMARONG_SHEET)
                .criterion(FabricRecipeProvider.hasItem(Items.DISPENSER),
                        FabricRecipeProvider.conditionsFromItem(Items.DISPENSER))
                .criterion(FabricRecipeProvider.hasItem(Items.GOLD_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.GOLD_BLOCK))
                .criterion(FabricRecipeProvider.hasItem(Items.WATER_BUCKET),
                        FabricRecipeProvider.conditionsFromItem(Items.WATER_BUCKET))
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_SHEET),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_SHEET))
                .offerTo(exporter);
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(AmarongBlocks.AMARONG_CORE), RecipeCategory.MISC, AmarongItems.AMARONG_CHUNK, 4)
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_CHUNK),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_CHUNK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, AmarongItems.AMARONG_CORE).pattern("xvx").pattern("vmv").pattern("xvx")
                .input('x', Items.AMETHYST_SHARD)
                .input('v', Items.COPPER_INGOT)
                .input('m', Items.NETHER_STAR)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, "amarong_core_inverted");
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, AmarongItems.AMARONG_CORE).pattern("xvx").pattern("vmv").pattern("xvx")
                .input('v', Items.AMETHYST_SHARD)
                .input('x', Items.COPPER_INGOT)
                .input('m', Items.NETHER_STAR)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, AmarongItems.AMARONG_CORE).pattern("xx").pattern("xx")
                .input('x', AmarongItems.AMARONG_CHUNK)
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_CHUNK),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_CHUNK))
                .offerTo(exporter, "amarong_core_rebuild");
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, AmarongItems.TICKET_LAUNCHER).pattern(" xo").pattern("cwx").pattern("lc ")
                .input('l', Items.LEVER)
                .input('o', Items.DISPENSER)
                .input('c', AmarongItems.AMARONG_CHUNK)
                .input('x', Items.END_ROD)
                .input('w', TerrificTickets.PASSCARD)
                .criterion(FabricRecipeProvider.hasItem(Items.LEVER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEVER))
                .criterion(FabricRecipeProvider.hasItem(Items.DISPENSER),
                        FabricRecipeProvider.conditionsFromItem(Items.DISPENSER))
                .criterion(FabricRecipeProvider.hasItem(AmarongItems.AMARONG_CHUNK),
                        FabricRecipeProvider.conditionsFromItem(AmarongItems.AMARONG_CHUNK))
                .criterion(FabricRecipeProvider.hasItem(Items.END_ROD),
                        FabricRecipeProvider.conditionsFromItem(Items.END_ROD))
                .criterion(FabricRecipeProvider.hasItem(TerrificTickets.PASSCARD),
                        FabricRecipeProvider.conditionsFromItem(TerrificTickets.PASSCARD))
                .offerTo(exporter);
        ComplexRecipeJsonBuilder.create(KaleidoscopeShaderTypeRecipe::new).offerTo(exporter, "kaleidoscope_shader_type");
    }
}
