package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongItems;

public class AmarongModelGenerator extends FabricModelProvider {

    public AmarongModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(AmarongBlocks.AMARONG_CORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AmarongItems.KALEIDOSCOPE, Models.GENERATED);
        itemModelGenerator.register(AmarongItems.AMARONG_SHEET, Models.GENERATED);
        itemModelGenerator.register(AmarongItems.AMARONG_CHUNK, Models.GENERATED);
    }
}
