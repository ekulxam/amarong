package survivalblock.amarong.mixin.compat.honque;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import symbolics.division.honque.TheFunny;

@Mixin(TheFunny.class)
public interface TheFunnyAccessor {

    @Accessor(value = "INSTANT_DEATH_CHANCE", remap = false)
    float amarong$getInstantDeathChance();
}
