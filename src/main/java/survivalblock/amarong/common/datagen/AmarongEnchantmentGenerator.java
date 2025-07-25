package survivalblock.amarong.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.AtmosphericDynamicRegistryProvider;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datagen.RegistryEntryLookupContainer;

import java.util.concurrent.CompletableFuture;

public class AmarongEnchantmentGenerator extends AtmosphericDynamicRegistryProvider<Enchantment> {

    public AmarongEnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryEntryLookupContainer container) {
        registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT)
                .streamEntries()
                .filter(ref ->
                        Amarong.MOD_ID.equals(ref.registryKey().getValue().getNamespace()))
                .forEachOrdered(ref ->
                        entries.add(ref.registryKey(), ref.value()));
        /*for (Map.Entry<RegistryKey<Enchantment>, Enchantment> entry : AmarongEnchantments.asEnchantments(container).entrySet()) {
            entries.add(entry.getKey(), entry.getValue());
        }*/
    }
}
