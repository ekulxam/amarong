package survivalblock.amarong.common.init;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.registrar.dynamic.DamageTypeRegistrant;

public sealed interface AmarongDamageTypes permits AmarongDamageTypes.Dummy {

    DamageTypeRegistrant registrant = new DamageTypeRegistrant(Amarong::id);

    RegistryKey<DamageType> WATER_STREAM_HIT = registrant.register(
            "water_stream_hit",
            new DamageType("amarong.water_stream_hit", 0.1F)
    );
    RegistryKey<DamageType> FLYING_TICKET_HIT = registrant.register(
            "flying_ticket_hit",
            new DamageType("amarong.flying_ticket_hit", 0.1F)
    );
    RegistryKey<DamageType> RAILGUN_HIT = registrant.register(
            "railgun_hit",
            new DamageType("amarong.railgun_hit", 0.3F)
    );
    RegistryKey<DamageType> BOOMERANG_HIT = registrant.register(
            "boomerang_hit",
            new DamageType("amarong.boomerang_hit", 0.1F)
    );

    record Dummy() implements AmarongDamageTypes {
    }
}
