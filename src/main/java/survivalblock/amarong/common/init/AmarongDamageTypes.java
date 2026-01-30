package survivalblock.amarong.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.*;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import survivalblock.amarong.common.Amarong;

public sealed interface AmarongDamageTypes permits AmarongDamageTypes.Dummy {

    RegistryKey<DamageType> WATER_STREAM_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("water_stream_hit"));
    RegistryKey<DamageType> FLYING_TICKET_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("flying_ticket_hit"));
    RegistryKey<DamageType> RAILGUN_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("railgun_hit"));
    RegistryKey<DamageType> BOOMERANG_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("boomerang_hit"));

    /**
     * Creates a map with the {@link RegistryKey<DamageType>}s as keys and {@link DamageType}s as values
     * @return an {@link ImmutableMap}
     */
    static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        ImmutableMap.Builder<RegistryKey<DamageType>, DamageType> damageTypes = ImmutableMap.builder();
        damageTypes.put(WATER_STREAM_HIT, new DamageType("amarong.water_stream_hit", 0.1F));
        damageTypes.put(FLYING_TICKET_HIT, new DamageType("amarong.flying_ticket_hit", 0.1F));
        damageTypes.put(RAILGUN_HIT, new DamageType("amarong.railgun_hit", 0.3F));
        damageTypes.put(BOOMERANG_HIT, new DamageType("amarong.boomerang_hit", 0.1F));
        return damageTypes.build();
    }

    static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        asDamageTypes().forEach(damageTypeRegisterable::register);
    }

    record Dummy() implements AmarongDamageTypes {
    }
}
