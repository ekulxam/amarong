package survivalblock.amarong.mixin.staff;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder {

    @Shadow public abstract Item getItem();

    @Shadow public abstract ComponentMap getComponents();

    @Shadow public abstract ComponentMap getDefaultComponents();

    @ModifyReturnValue(method = "getComponents", at = @At("RETURN"))
    private ComponentMap includeHeldItemComponents(final ComponentMap original) {
        if (!(this.getItem() instanceof AmarongStaffItem)) {
            return original;
        }
        if (ComponentMap.EMPTY.equals(original)) {
            return original;
        }
        final ItemStack stack = original.get(AmarongDataComponentTypes.STAFF_STACK);
        if (stack == null || stack.isEmpty()) {
            return original;
        }
        final ComponentMap other = stack.getComponents();
        return new AmarongStaffItem.CombinedComponentMap(original, other);
    }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private <T> void setOtherStackComponent(ComponentType<? super T> type, @Nullable T value, CallbackInfoReturnable<T> cir) {
        if (!(this.getItem() instanceof AmarongStaffItem)) {
            return;
        }
        final ItemStack stack = this.get(AmarongDataComponentTypes.STAFF_STACK);
        if (stack == null || stack.isEmpty()) {
            return;
        }
        if (!this.getDefaultComponents().contains(type)) {
            cir.setReturnValue(stack.set(type, value));
            return;
        }
    }

    @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
    private void changeRarity(CallbackInfoReturnable<Rarity> cir) {
        if (!(this.getItem() instanceof AmarongStaffItem staff)) {
            return;
        }
        cir.setReturnValue(staff.getItemStackFromComponents((ItemStack) (Object) this).getRarity());
    }
}
