package survivalblock.amarong.common.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;
import survivalblock.amarong.common.init.AmarongEntityComponents;

public class BoomerangComponent implements AutoSyncedComponent {

    public static final String ENCHANTED_KEY = "enchanted";
    public static final String RETURNING_KEY = "returning";
    private final PhasingBoomerangEntity obj;
    private boolean enchanted = false;
    private boolean returning = false;

    public BoomerangComponent(PhasingBoomerangEntity boomerang) {
        this.obj = boomerang;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        if (tag.contains(ENCHANTED_KEY)) {
            this.enchanted = tag.getBoolean(ENCHANTED_KEY);
        }
        if (tag.contains(RETURNING_KEY)) {
            this.returning = tag.getBoolean(RETURNING_KEY);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean(ENCHANTED_KEY, this.enchanted);
        tag.putBoolean(RETURNING_KEY, this.returning);
    }

    public boolean isEnchanted() {
        return this.enchanted;
    }

    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
        AmarongEntityComponents.BOOMERANG_COMPONENT.sync(this.obj);
    }

    public boolean isReturning() {
        return this.returning;
    }

    public void setReturning(boolean returning) {
        this.returning = returning;
        AmarongEntityComponents.BOOMERANG_COMPONENT.sync(this.obj);
    }
}
