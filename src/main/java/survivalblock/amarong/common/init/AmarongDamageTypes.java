package survivalblock.amarong.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.*;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;

public class AmarongDamageTypes {

    public static final RegistryKey<DamageType> WATER_STREAM_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("water_stream_hit"));
    public static final RegistryKey<DamageType> FLYING_TICKET_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("flying_ticket_hit"));
    public static final RegistryKey<DamageType> RAILGUN_HIT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Amarong.id("railgun_hit"));

    public static DamageSource create(RegistryKey<DamageType> key, Entity attacker) {
        return new DamageSource(getDamageTypeEntryFromKey(key, attacker.getWorld()), attacker);
    }

    public static DamageSource create(RegistryKey<DamageType> key, Entity source, Entity attacker) {
        return new DamageSource(getDamageTypeEntryFromKey(key, attacker.getWorld()), source, attacker);
    }

    public static RegistryEntry<DamageType> getDamageTypeEntryFromKey(RegistryKey<DamageType> key, World world) {
        return world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key);
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(WATER_STREAM_HIT, new DamageType("amarong.flying_ticket_hit", 0.1F));
        damageTypeRegisterable.register(FLYING_TICKET_HIT, new DamageType("amarong.railgun_hit", 0.1F));
        damageTypeRegisterable.register(RAILGUN_HIT, new DamageType("amarong.water_stream_hit", 0.3F));
    }
}
