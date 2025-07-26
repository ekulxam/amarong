package survivalblock.amarong.mixin.staff;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract Hand getActiveHand();

    // begin credit, taken from Crossbow Scoping (All Rights Reserved copyright ekulxam) and repurposed for Amarong
    @ModifyExpressionValue(method = {"tickActiveItemStack", "consumeItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack replaceWithStaffStack(ItemStack original) {
        if (!(original.getItem() instanceof AmarongStaffItem staff)) {
            return original;
        }
        ItemStack stackInComponents = staff.getStaffStack(original);
        if (!stackInComponents.isEmpty()) {
            return stackInComponents;
        }
        return original;
    }
    // end credit

    @WrapOperation(method = "stopUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onStoppedUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V"))
    private void onStoppedUsingStaff(ItemStack instance, World world, LivingEntity user, int remainingUseTicks, Operation<Void> original) {
        ItemStack realActiveStack = this.getStackInHand(this.getActiveHand());
        if (realActiveStack.getItem() instanceof AmarongStaffItem staff && ItemStack.areEqual(instance, staff.getStaffStack(realActiveStack))) {
            original.call(realActiveStack, world, user, remainingUseTicks);
            return;
        }
        original.call(instance, world, user, remainingUseTicks);
    }

    @WrapOperation(method = "stopUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isUsedOnRelease()Z"))
    private boolean useStaffOnRelease(ItemStack instance, Operation<Boolean> original) {
        ItemStack realActiveStack = this.getStackInHand(this.getActiveHand());
        if (realActiveStack.getItem() instanceof AmarongStaffItem staff && ItemStack.areEqual(instance, staff.getStaffStack(realActiveStack))) {
            return original.call(realActiveStack);
        }
        return original.call(instance);
    }

    @WrapOperation(method = "consumeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack finishUsingStaff(ItemStack instance, World world, LivingEntity user, Operation<ItemStack> original) {
        ItemStack realActiveStack = this.getStackInHand(this.getActiveHand());
        if (realActiveStack.getItem() instanceof AmarongStaffItem staff && ItemStack.areEqual(instance, staff.getStaffStack(realActiveStack))) {
            return original.call(realActiveStack, world, user);
        }
        return original.call(instance, world, user);
    }
}
