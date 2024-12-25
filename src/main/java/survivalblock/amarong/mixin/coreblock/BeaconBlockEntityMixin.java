package survivalblock.amarong.mixin.coreblock;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalLongRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.init.AmarongBlocks;

import java.util.List;

@Debug(export = true)
@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void captureWorldTime(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci, @Share("time") LocalLongRef longReference) {
        longReference.set(world.getTime());
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private static Block captureBlock(Block original, @Share("block") LocalRef<Block> blockReference) {
        blockReference.set(original);
        return original;
    }

    @ModifyVariable(method = "tick", at = @At(value = "LOAD", ordinal = 6), index = 8)
    private static BeaconBlockEntity.BeamSegment doSomeHackyInstanceofBypass(BeaconBlockEntity.BeamSegment original, World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, @Share("block") LocalRef<Block> blockReference, @Local(ordinal = 1) BlockPos blockPos, @Share("time") LocalLongRef longReference) {
        Block block = blockReference.get();
        if (block == null) {
            return original;
        }
        if (block == AmarongBlocks.AMARONG_CORE || block.equals(AmarongBlocks.AMARONG_CORE)) {
            int color = AmarongUtil.getCoreColor(world, blockPos, longReference.get());
            if (getBeamSegments(blockEntity).size() <= 1) {
                original = new BeaconBlockEntity.BeamSegment(color);
                getBeamSegments(blockEntity).add(original);
            } else if (original != null) {
                if (color == original.getColor()) {
                    ((BeaconBlockEntityAccessor.BeamSegmentAccessor) original).amarong$invokeIncreaseHeight();
                } else {
                    original = new BeaconBlockEntity.BeamSegment(ColorHelper.Argb.averageArgb(original.getColor(), color));
                    getBeamSegments(blockEntity).add(original);
                }
            }
        }
        return original;
    }

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BeaconBlockEntity$BeamSegment;increaseHeight()V", ordinal = 1))
    private static boolean doNotIncreaseHeightIfAmarongCore(BeaconBlockEntity.BeamSegment instance, @Share("block") LocalRef<Block> blockReference) {
        Block block = blockReference.get();
        if (block == null) {
            return true;
        }
        if (block.equals(AmarongBlocks.AMARONG_CORE)) {
            return false;
        }
        return true;
    }

    @Unique
    private static List<BeaconBlockEntity.BeamSegment> getBeamSegments(BeaconBlockEntity blockEntity) {
        return ((BeaconBlockEntityAccessor) blockEntity).amarong$getBeamSegments();
    }
}
