package survivalblock.amarong.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import survivalblock.amarong.common.Amarong;

public class AmarongSounds {

    public static final SoundEvent DUCK_SQUEAKS = SoundEvent.of(Amarong.id("duck_squeaks"));
    public static final SoundEvent WATER_STREAM_HITS = SoundEvent.of(Amarong.id("water_stream_hits"));
    public static final SoundEvent RAILGUN_CHARGES = SoundEvent.of(Amarong.id("railgun_charges"));

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, DUCK_SQUEAKS.getId(), DUCK_SQUEAKS);
        Registry.register(Registries.SOUND_EVENT, WATER_STREAM_HITS.getId(), WATER_STREAM_HITS);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_CHARGES.getId(), RAILGUN_CHARGES);
    }
}
