package survivalblock.amarong.mixin.duck;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @Shadow private ItemStack stack;

    @WrapOperation(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;encode(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/nbt/NbtElement;"))
    private NbtElement doNotEncodeIfStackIsEmpty(ItemStack instance, RegistryWrapper.WrapperLookup registries, Operation<NbtElement> original) {
        if (this.stack == null || this.stack.isEmpty()) {
            return new NbtCompound();
        }
        return original.call(instance, registries);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "RETURN"))
    private void removeItemFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.stack == null || this.stack.isEmpty()) {
            nbt.remove("item");
        }
    }
}
