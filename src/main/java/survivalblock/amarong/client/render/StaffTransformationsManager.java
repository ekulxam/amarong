package survivalblock.amarong.client.render;

import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.Amarong;
import survivalblock.atmosphere.atmospheric_api.not_mixin.resource.AtmosphericResourceReader;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class StaffTransformationsManager extends AtmosphericResourceReader<AmarongStaffTransformation> {

    public static final String STAFF_TRANSFORMATIONS_DIRECTORY = "amarong_staff_transformations";

    private Map<Item, AmarongStaffTransformation> transformations = new HashMap<>(256);

    public StaffTransformationsManager() {
        this("Failed to load amarong staff transformation {}",
                AmarongStaffTransformation.CODEC,
                ResourceFinder.json(STAFF_TRANSFORMATIONS_DIRECTORY));
    }

    protected StaffTransformationsManager(String errorMessage, Codec<AmarongStaffTransformation> codec, ResourceFinder resourceFinder) {
        super(errorMessage, codec, resourceFinder);
    }

    public AmarongStaffTransformation getTransformation(ItemStack stack) {
        AmarongStaffTransformation staffTransformation = this.getTransformation(stack.getItem());
        return staffTransformation == null ? AmarongStaffTransformation.DEFAULT : staffTransformation;
    }

    @Nullable
    public AmarongStaffTransformation getTransformation(Item item) {
        return this.transformations.get(item);
    }

    private AmarongStaffTransformation getTransformationInternal(Identifier itemId, Map<Identifier, AmarongStaffTransformation> staffTransformations) {
        // for some reason, the ids have the format <namespace>:amarong_staff_transformations/<path>.json and I don't know how to fix that right now so I'm using this workaround
        // hopefully string operations are fast
        Identifier id = Identifier.of(itemId.getNamespace(), STAFF_TRANSFORMATIONS_DIRECTORY + "/" + itemId.getPath() + ".json");
        return staffTransformations.get(id);
    }

    protected void upload(Map<Identifier, AmarongStaffTransformation> staffTransformations, Profiler profiler) {
        profiler.startTick();
        profiler.push("upload");
        Map<Item, AmarongStaffTransformation> hashMap = new HashMap<>();
        for (Item item : Registries.ITEM) {
            AmarongStaffTransformation staffTransformation = this.getTransformationInternal(Registries.ITEM.getId(item), staffTransformations);
            if (staffTransformation == null) {
                continue;
            }
            hashMap.put(item, staffTransformation);
        }
        this.transformations = hashMap;
        profiler.pop();
        profiler.endTick();
    }

    @Override
    public Identifier getFabricId() {
        return Amarong.id("staff_transformations");
    }
}
