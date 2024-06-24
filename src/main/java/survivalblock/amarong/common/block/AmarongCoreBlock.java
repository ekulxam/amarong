package survivalblock.amarong.common.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HeavyCoreBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.init.AmarongTags;

public class AmarongCoreBlock extends HeavyCoreBlock implements BlockEntityProvider {

    public AmarongCoreBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AmarongCoreBlockEntity(pos, state);
    }
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.isClient()) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AmarongCoreBlockEntity amarongCore) {
            amarongCore.setOffset(world.getTime());
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!stack.isIn(AmarongTags.AmarongItemTags.RAINBOW_CORE_GENERATORS)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockEntity blockEntity = serverWorld.getBlockEntity(pos);
        if (blockEntity instanceof AmarongCoreBlockEntity amarongCore) {
            amarongCore.setMode(amarongCore.getMode() + 1);
            Vec3d blockCenter = Vec3d.ofCenter(pos);
            serverWorld.spawnParticles(ParticleTypes.END_ROD, blockCenter.getX(), blockCenter.getY(), blockCenter.getZ(), 16, 2, 2, 2, 0);
            // player.sendMessage(Text.literal("Mode : " + amarongCore.getMode()));
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
