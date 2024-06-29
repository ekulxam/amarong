package survivalblock.amarong.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.FlyingTicketEntity;
import survivalblock.amarong.common.entity.RailgunEntity;
import survivalblock.amarong.common.entity.WaterStreamEntity;

public class AmarongEntityTypes {

    public static final EntityType<WaterStreamEntity> WATER_STREAM = registerEntity("water_stream", SpawnGroup.MISC, WaterStreamEntity::new, EntityDimensions.changing(0.5F, 0.5F), 0.13F, 4, 20, false);
    public static final EntityType<FlyingTicketEntity> FLYING_TICKET = registerEntity("flying_ticket", SpawnGroup.MISC, FlyingTicketEntity::new, EntityDimensions.changing(0.6F, 0.2F), 0.1F, 4, 20, false);
    public static final EntityType<RailgunEntity> RAILGUN = registerEntity("railgun", SpawnGroup.MISC, RailgunEntity::new, EntityDimensions.changing(0.5F, 0.5F), false);

    @SuppressWarnings({"SameParameterValue", "unused"})
    private static <T extends Entity> EntityType<T> registerEntity(String name, SpawnGroup group, EntityType.EntityFactory<T> factory, EntityDimensions dimensions, boolean fireImmune) {
        EntityType.Builder<T> builder = EntityType.Builder.create(factory, group).dimensions(dimensions.width(), dimensions.height());
        if (fireImmune) {
            builder = builder.makeFireImmune();
        }
        return Registry.register(Registries.ENTITY_TYPE, Amarong.id(name),
                builder.build());
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private static <T extends Entity> EntityType<T> registerEntity(String name, SpawnGroup group, EntityType.EntityFactory<T> factory, EntityDimensions dimensions, float eyeHeight, boolean fireImmune) {
        EntityType.Builder<T> builder = EntityType.Builder.create(factory, group).dimensions(dimensions.width(), dimensions.height()).eyeHeight(eyeHeight);
        if (fireImmune) {
            builder = builder.makeFireImmune();
        }
        return Registry.register(Registries.ENTITY_TYPE, Amarong.id(name),
                builder.build());
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private static <T extends Entity> EntityType<T> registerEntity(String name, SpawnGroup group, EntityType.EntityFactory<T> factory, EntityDimensions dimensions, float eyeHeight, int maxTrackingRange, int trackingTickInterval, boolean fireImmune) {
        EntityType.Builder<T> builder = EntityType.Builder.create(factory, group).dimensions(dimensions.width(), dimensions.height()).eyeHeight(eyeHeight).maxTrackingRange(maxTrackingRange).trackingTickInterval(trackingTickInterval);
        if (fireImmune) {
            builder = builder.makeFireImmune();
        }
        return Registry.register(Registries.ENTITY_TYPE, Amarong.id(name),
                builder.build());
    }

    public static void init() {
    }
}
