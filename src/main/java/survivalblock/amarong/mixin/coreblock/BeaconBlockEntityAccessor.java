package survivalblock.amarong.mixin.coreblock;

import net.minecraft.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor {

    @Accessor("field_19178")
    List<BeaconBlockEntity.BeamSegment> amarong$getBeamSegments();

    @Mixin(BeaconBlockEntity.BeamSegment.class)
    interface BeamSegmentAccessor {
        @Invoker("increaseHeight")
        void amarong$invokeIncreaseHeight();
    }
}
