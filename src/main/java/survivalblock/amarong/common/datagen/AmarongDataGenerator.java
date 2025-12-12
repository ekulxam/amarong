package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.amarong.common.init.AmarongEnchantments;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.FabricDataPackGenerator;

public class AmarongDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(AmarongModelGenerator::new);
		pack.addProvider(AmarongEnUsLangGenerator::new);
		pack.addProvider(AmarongRecipeGenerator::new);
		pack.addProvider(AmarongAdvancementGenerator::new);
		pack.addProvider(AmarongBlockLootTableGenerator::new);
		pack.addProvider(AmarongDynamicRegistriesGenerator::new);
		pack.addProvider(AmarongEnchantmentGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongBlockTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongItemTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongDamageTypeTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongDataComponentTypeTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongEnchantmentTagGenerator::new);
		pack.addProvider(AmarongTagGenerator.AmarongEntityTypeTagGenerator::new);
		FabricDataGenerator.Pack easyCoreDuplicationPack = FabricDataPackGenerator.createBuiltinDataPack(fabricDataGenerator, AmarongUtil.EASY_CORE_DUPLICATION_PACK);
		easyCoreDuplicationPack.addProvider(AmarongRecipeGenerator.EasyCoreDuplicationRecipeGenerator::new);
		FabricDataGenerator.Pack amarongCoreRecipePack = FabricDataPackGenerator.createBuiltinDataPack(fabricDataGenerator, AmarongUtil.AMARONG_HAMMER_RECIPE_PACK);
		amarongCoreRecipePack.addProvider(AmarongRecipeGenerator.AmarongHammerRecipeGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, AmarongDamageTypes::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, AmarongEnchantments::bootstrap);
	}
}
