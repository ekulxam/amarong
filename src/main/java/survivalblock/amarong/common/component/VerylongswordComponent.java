package survivalblock.amarong.common.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.AmarongHammerTwirlCompat;
import survivalblock.amarong.common.entity.RailgunEntity;
import survivalblock.amarong.common.init.AmarongEntityComponents;

public class VerylongswordComponent implements AutoSyncedComponent, CommonTickingComponent {

    private final PlayerEntity obj;
    public static final int OBSCURE = 200;
    private int obscureTicks = 0;

    public VerylongswordComponent(PlayerEntity player) {
        this.obj = player;
    }

    public int getObscureTicks() {
        return this.obscureTicks;
    }

    public boolean isObscured() {
        return this.getObscureTicks() > 0;
    }

    public void enterObscure() {
        if (obscureTicks != OBSCURE) {
            obscureTicks = OBSCURE;
            sync();
        }
    }

    public void deathStar(ItemStack stack) {
        World world = this.obj.getWorld();
        if (!world.isClient()) {
            RailgunEntity railgunEntity = new RailgunEntity(world, this.obj, stack);
            world.spawnEntity(railgunEntity);
        }
    }

    public void sync() {
        AmarongEntityComponents.VERYLONGSWORD_COMPONENT.sync(this.obj);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        obscureTicks = tag.getInt("ObscureTicks");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt("ObscureTicks", obscureTicks);
    }

    @Override
    public void tick() {

    }

    @Override
    public void serverTick() {
        boolean shouldSync = false;
        if (obscureTicks > 0) {
            obscureTicks--;
            if (obscureTicks > OBSCURE) obscureTicks = OBSCURE;
            shouldSync = true;
        }
        if (shouldSync) {
            sync();
        }
        CommonTickingComponent.super.serverTick();
    }
}
