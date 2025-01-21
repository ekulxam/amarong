package survivalblock.amarong.mixin.staff;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemUsageContext.class)
public interface ItemUsageContextAccessor {

    @Mutable
    @Accessor("stack")
    void amarong$setStack(ItemStack stack);
}
