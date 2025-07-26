package survivalblock.amarong.mixin.staff;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Debug(export = true)
@Mixin(value = PlayerInventory.class, priority = 1000000)
public class PlayerInventoryMixin {

    @ModifyExpressionValue(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"))
    private Object getStaffStack(Object original) {
        if (!(original instanceof ItemStack stack)) {
            return original;
        }
        if (AmarongStaffItem.usingStaff && stack.getItem() instanceof AmarongStaffItem staff) {
            return staff.getStaffStack(stack);
        }
        return original;
    }

    @WrapOperation(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    private Object setStaffStack(DefaultedList<?> instance, int index, Object element, Operation<Object> original) {
        if (!(element instanceof ItemStack stack)) {
            return original.call(instance, index, element);
        }
        if (!AmarongStaffItem.usingStaff) {
            return original.call(instance, index, element);
        }
        Object obj = instance.get(index);
        if (!(obj instanceof ItemStack staff)) {
            return original.call(instance, index, element);
        }
        return staff.set(AmarongDataComponentTypes.STAFF_STACK, stack);
    }
}
