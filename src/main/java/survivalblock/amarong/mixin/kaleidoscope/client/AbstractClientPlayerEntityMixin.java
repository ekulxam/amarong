package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.client.AmarongClientUtil;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.AmarongItems;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyExpressionValue(method = "getFovMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean turnOffZoom(boolean original) {
        if (!this.getActiveItem().isOf(AmarongItems.KALEIDOSCOPE)) {
            return original;
        }
        if (AmarongConfig.noKaleidoscopeZoom()) {
            return false;
        }
        return original;
    }
}
