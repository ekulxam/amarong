package survivalblock.amarong.mixin.staff.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.item.AmarongStaffItem;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @WrapOperation(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ItemCooldownManager;getCooldownProgress(Lnet/minecraft/item/Item;F)F"))
    private float showCooldownForStaffStack(ItemCooldownManager instance, Item item, float tickDelta, Operation<Float> original, @Local(argsOnly = true)ItemStack stack) {
        float cooldown = original.call(instance, item, tickDelta);
        if (item instanceof AmarongStaffItem) {
            if (cooldown > 0.0F) {
                return cooldown;
            }
            return original.call(instance, ((AmarongStaffItem) item).getStaffStack(stack).getItem(), tickDelta);
        }
        return cooldown;
    }
}
