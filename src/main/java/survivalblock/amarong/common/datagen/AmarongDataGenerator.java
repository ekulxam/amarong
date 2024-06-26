package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AmarongDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(AmarongModelGenerator::new);
		pack.addProvider(AmarongEnUsLangGenerator::new);
		pack.addProvider(AmarongRecipeGenerator::new);
		pack.addProvider(AmarongAdvancementGenerator::new);
		pack.addProvider(AmarongBlockLootTableGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongItemTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongBlockTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongDamageTypeTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongEnchantmentTagGenerator::new);
	}
}
