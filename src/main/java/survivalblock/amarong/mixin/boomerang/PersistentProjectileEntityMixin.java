package survivalblock.amarong.mixin.boomerang;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;

@Debug(export = true)
@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @ModifyVariable(method = "tick", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/util/hit/EntityHitResult;getEntity()Lnet/minecraft/entity/Entity;"), index = 1)
    private boolean modifyNoClipValueBeforeEntityHit(boolean value, @Share("isNoClipBool") LocalBooleanRef localBooleanRef) {
        localBooleanRef.set(value);
        if ((PersistentProjectileEntity) (Object) this instanceof PhasingBoomerangEntity) {
            return false;
        }
        return value;
    }

    @ModifyVariable(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;velocityDirty:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER), index = 1)
    private boolean modifyNoClipValueAfterEntityHit(boolean value, @Share("isNoClipBool") LocalBooleanRef localBooleanRef) {
        if ((PersistentProjectileEntity) (Object) this instanceof PhasingBoomerangEntity) {
            return value || localBooleanRef.get();
        }
        return value;
    }
}
