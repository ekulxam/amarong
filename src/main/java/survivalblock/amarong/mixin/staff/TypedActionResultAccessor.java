package survivalblock.amarong.mixin.staff;

import net.minecraft.util.TypedActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TypedActionResult.class)
public interface TypedActionResultAccessor<T> {

    @Mutable
    @Accessor("value")
    void amarong$setValue(T value);
}
