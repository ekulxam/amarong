package survivalblock.amarong.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
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
        return create(key, attacker.getWorld(), attacker, null);
    }

    public static DamageSource create(RegistryKey<DamageType> key, Entity source, Entity attacker) {
        return create(key, source.getWorld(), source, attacker);
    }

    public static DamageSource create(RegistryKey<DamageType> key, World world, Entity source, Entity attacker) {
        RegistryEntry<DamageType> entry = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key);
        if (attacker == null) {
            return new DamageSource(entry, source);
        }
        return new DamageSource(entry, source, attacker);
    }
}
