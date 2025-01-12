package survivalblock.amarong.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.FlyingTicketEntity;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;
import survivalblock.amarong.common.entity.RailgunEntity;
import survivalblock.amarong.common.entity.WaterStreamEntity;

public class AmarongEntityTypes {

    public static final EntityType<WaterStreamEntity> WATER_STREAM = registerEntity("water_stream", EntityType.Builder.<WaterStreamEntity>create(WaterStreamEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune());
    public static final EntityType<FlyingTicketEntity> FLYING_TICKET = registerEntity("flying_ticket", EntityType.Builder.<FlyingTicketEntity>create(FlyingTicketEntity::new, SpawnGroup.MISC).dimensions(0.6F, 0.2F).eyeHeight(0.1F).maxTrackingRange(4).trackingTickInterval(20));
    public static final EntityType<RailgunEntity> RAILGUN = registerEntity("railgun", EntityType.Builder.<RailgunEntity>create(RailgunEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).disableSummon().makeFireImmune());
    public static final EntityType<PhasingBoomerangEntity> BOOMERANG = registerEntity("amarong_boomerang", EntityType.Builder.<PhasingBoomerangEntity>create(PhasingBoomerangEntity::new, SpawnGroup.MISC).dimensions(1F, 0.0625F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune());

    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Amarong.id(name), builder.build());
    }

    public static void init() {
    }
}
