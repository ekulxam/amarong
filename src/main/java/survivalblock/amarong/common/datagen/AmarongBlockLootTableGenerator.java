package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.amarong.common.init.AmarongItems;

import java.util.concurrent.CompletableFuture;

public class AmarongBlockLootTableGenerator extends FabricBlockLootTableProvider {

    protected AmarongBlockLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(AmarongBlocks.AMARONG_CORE, drops(AmarongItems.AMARONG_CORE));
    }
}
