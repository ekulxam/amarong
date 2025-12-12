package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.AtmosphericDynamicRegistriesProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AmarongDynamicRegistriesGenerator extends AtmosphericDynamicRegistriesProvider {

    public AmarongDynamicRegistriesGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, List.of(RegistryKeys.DAMAGE_TYPE, RegistryKeys.ENCHANTMENT), registriesFuture);
    }
}
