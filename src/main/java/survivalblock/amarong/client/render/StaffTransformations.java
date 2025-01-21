package survivalblock.amarong.client.render;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StaffTransformations {

    private final Map<Item, AmarongStaffTransformation> transformations = new HashMap<>(256);
    private final StaffTransformationsManager manager;

    public StaffTransformations() {
        /*
        what have I done
        at this point I might scrap these resource shenanigans entirely
        all this completablefuture stuff is making my head explode
         */
        this.manager = new StaffTransformationsManager(this);
    }

    public StaffTransformationsManager getManager() {
        return this.manager;
    }

    public AmarongStaffTransformation getTransformation(ItemStack stack) {
        AmarongStaffTransformation staffTransformation = this.getTransformation(stack.getItem());
        return staffTransformation == null ? this.manager.getDefault() : staffTransformation;
    }

    @Nullable
    public AmarongStaffTransformation getTransformation(Item item) {
        return this.transformations.get(item);
    }

    public void reload() {
        this.transformations.clear();
        for (Item item : Registries.ITEM) {
            this.transformations.put(item, this.manager.getTransformation(item));
        }
    }
}
