package survivalblock.amarong.mixin.compat.twirl.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import folk.sisby.twirl.Twirl;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.init.AmarongTags;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean noSlowdown(boolean original){
        return impedesMovement(original);
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean doesNotDisableSprinting(boolean original){
        return impedesMovement(original);
    }

    @Unique
    private boolean impedesMovement(boolean other){
        ItemStack stack = this.getActiveItem();
        if (stack.isIn(AmarongTags.AmarongItemTags.TWIRL_DAMAGE) && AmarongUtil.getLevel(stack, Twirl.TWIRLING) > 0) {
            return false;
        }
        return other;
    }
}
