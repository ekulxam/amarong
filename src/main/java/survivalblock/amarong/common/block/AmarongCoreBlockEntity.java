package survivalblock.amarong.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.init.AmarongBlocks;

public class AmarongCoreBlockEntity extends BlockEntity {

    private static final String OFFSET_KEY = "TimeOffset";
    private static final String RAINBOW_KEY = "Prismatic";
    private long offset = -1;
    private int mode = 0; // 0 for jeb_, 1 for rainbow, 2 for trans

    public AmarongCoreBlockEntity(BlockPos pos, BlockState state) {
        super(AmarongBlocks.AMARONG_CORE_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong(OFFSET_KEY, offset);
        nbt.putInt(RAINBOW_KEY, mode);
    }

    public void setOffset(long offset) {
        if (this.offset == -1) {
            this.offset = offset;
            if (this.getWorld() instanceof ServerWorld) updateListeners();
        }
    }

    public long getOffset() {
        return this.offset;
    }

    public void setMode(int mode) {
        if (this.mode == mode) {
            return;
        }
        this.mode = (mode % 3);
        if (this.getWorld() instanceof ServerWorld) updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public int getMode() {
        return this.mode;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createNbt(registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.offset = nbt.getLong(OFFSET_KEY);
        this.mode = nbt.getInt(RAINBOW_KEY) % 3;
    }
}
