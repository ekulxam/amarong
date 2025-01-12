package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.init.AmarongDamageTypes;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.AtmosphericDynamicRegistryProvider;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.RegistryEntryLookupContainer;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AmarongDamageTypeGenerator extends AtmosphericDynamicRegistryProvider<DamageType> {

    public AmarongDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryEntryLookupContainer container) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : AmarongDamageTypes.asDamageTypes().entrySet()) {
            entries.add(entry.getKey(), entry.getValue());
        }
    }
}
