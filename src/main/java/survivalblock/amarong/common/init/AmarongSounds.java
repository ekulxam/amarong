package survivalblock.amarong.common.init;

import net.minecraft.sound.SoundEvent;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.registrar.delayed.DelayedSoundEventRegistrant;

public sealed interface AmarongSounds permits AmarongSounds.Dummy {
    DelayedSoundEventRegistrant registrant = new DelayedSoundEventRegistrant(Amarong::id);

    SoundEvent DUCK_SQUEAKS = registrant.register("duck_squeaks");
    SoundEvent FLYING_TICKET_HITS = registrant.register("flying_ticket_hits");
    SoundEvent WATER_STREAM_HITS = registrant.register("water_stream_hits");
    SoundEvent RAILGUN_CHARGES = registrant.register("railgun_charges");

    static void init() {
        registrant.consumeAll();
    }

    record Dummy() implements AmarongSounds {
    }
}
