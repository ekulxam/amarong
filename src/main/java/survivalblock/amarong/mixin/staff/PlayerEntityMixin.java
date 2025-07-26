package survivalblock.amarong.mixin.staff;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Mixin(value = PlayerEntity.class, priority = 1000000)
public class PlayerEntityMixin {

    @ModifyReturnValue(method = "getEquippedStack", at = @At("RETURN"))
    private ItemStack returnStaffStack(ItemStack original) {
        if (AmarongStaffItem.usingStaff && original.getItem() instanceof AmarongStaffItem staff) {
            return staff.getStaffStack(original);
        }
        return original;
    }
}
