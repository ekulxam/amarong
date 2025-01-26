package survivalblock.amarong.mixin.coreblock;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalLongRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.block.StainableAmarongCoreWrapper;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.compat.config.BeaconBeamDebugMode;
import survivalblock.amarong.common.init.AmarongBlocks;

//@Debug(export = true)
@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @Unique
    private static long amarong$prevWorldTime = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private static void captureWorldTime(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci, @Share("time") LocalLongRef longReference) {
        longReference.set(world.getTime());
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private static Block captureBlock(Block original, @Share("block") LocalRef<Block> blockReference) {
        blockReference.set(original);
        if (AmarongBlocks.AMARONG_CORE.equals(original)) {
            return StainableAmarongCoreWrapper.INSTANCE;
        }
        return original;
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "intValue=10"))
    private static int amarongCoreChange(int original, @Local(argsOnly = true) World world) {
        return world.isClient() ? AmarongConfig.maxBeaconBeamIterations() : original;
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getEntityColor()I"))
    private static int rainbowInstead(int original, @Local(argsOnly = true) World world, @Local(ordinal = 1) BlockPos blockPos, @Share("block") LocalRef<Block> blockReference, @Share("time") LocalLongRef longReference) {
        if (AmarongBlocks.AMARONG_CORE.equals(blockReference.get())) {
            return AmarongUtil.getCoreColor(world, blockPos, longReference.get());
        }
        return original;
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/block/entity/BeaconBlockEntity;beamSegments:Ljava/util/List;", opcode = Opcodes.PUTFIELD))
    private static void injectWhenBeamSegmentsAreSet(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci) {
        if (!world.isClient()) {
            return;
        }
        if (!AmarongConfig.verboseLogging()) {
            return;
        }
        long time = world.getTime();
        amarong$logBeaconTick(time);
        amarong$prevWorldTime = time;
    }

    @Unique
    private static void amarong$logBeaconTick(long time) {
        BeaconBeamDebugMode debugMode = AmarongConfig.debugBeaconBeams();
        if (BeaconBeamDebugMode.NEVER.equals(debugMode)) {
            return;
        }
        long difference = time - amarong$prevWorldTime;
        if (BeaconBeamDebugMode.ABNORMAL_ONLY.equals(debugMode)) {
            if (difference == 1) {
                return;
            }
        }
        Amarong.LOGGER.info("Beam segments set! World time was {}, prevTime was {}, difference was {}", time, amarong$prevWorldTime, difference);
    }

    /*
    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/block/entity/BeaconBlockEntity;minY:I", opcode = Opcodes.GETFIELD))
    private int alwaysAllowChangeForCore(int original) {

        return original;
    }
     */
}
