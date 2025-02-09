package survivalblock.amarong.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.*;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import survivalblock.amarong.common.Amarong;

import java.util.HashMap;
import java.util.Map;

public class AmarongDamageTypes {

    public static final RegistryKey<DamageType> WATER_STREAM_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("water_stream_hit"));
    public static final RegistryKey<DamageType> FLYING_TICKET_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("flying_ticket_hit"));
    public static final RegistryKey<DamageType> RAILGUN_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("railgun_hit"));
    public static final RegistryKey<DamageType> BOOMERANG_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("boomerang_hit"));

    /**
     * Creates a map with the {@link RegistryKey<DamageType>}s as keys and {@link DamageType}s as values
     * @return an {@link ImmutableMap}
     */
    public static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        ImmutableMap.Builder<RegistryKey<DamageType>, DamageType> damageTypes = ImmutableMap.builder();
        damageTypes.put(WATER_STREAM_HIT, new DamageType("amarong.water_stream_hit", 0.1F));
        damageTypes.put(FLYING_TICKET_HIT, new DamageType("amarong.flying_ticket_hit", 0.1F));
        damageTypes.put(RAILGUN_HIT, new DamageType("amarong.railgun_hit", 0.3F));
        damageTypes.put(BOOMERANG_HIT, new DamageType("amarong.boomerang_hit", 0.1F));
        return damageTypes.build();
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        asDamageTypes().forEach(damageTypeRegisterable::register);
    }
}
