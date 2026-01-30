package survivalblock.amarong.common.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.entity.FlyingTicketEntity;
import survivalblock.amarong.common.entity.PhasingBoomerangEntity;
import survivalblock.amarong.common.entity.RailgunEntity;
import survivalblock.amarong.common.entity.WaterStreamEntity;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.EntityTypeRegistrant;

@SuppressWarnings("UnstableApiUsage")
public sealed interface AmarongEntityTypes permits AmarongEntityTypes.Dummy {
    EntityTypeRegistrant registrant = new EntityTypeRegistrant(Amarong::id);

    EntityType<WaterStreamEntity> WATER_STREAM = registrant.register("water_stream", EntityType.Builder.<WaterStreamEntity>create(WaterStreamEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20).makeFireImmune());
    EntityType<FlyingTicketEntity> FLYING_TICKET = registrant.register("flying_ticket", EntityType.Builder.<FlyingTicketEntity>create(FlyingTicketEntity::new, SpawnGroup.MISC).dimensions(0.6F, 0.2F).eyeHeight(0.1F).maxTrackingRange(4).trackingTickInterval(20));
    EntityType<RailgunEntity> RAILGUN = registrant.register("railgun", EntityType.Builder.<RailgunEntity>create(RailgunEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).disableSummon().makeFireImmune());
    EntityType<PhasingBoomerangEntity> BOOMERANG = registrant.register("amarong_boomerang", EntityType.Builder.<PhasingBoomerangEntity>create(PhasingBoomerangEntity::new, SpawnGroup.MISC).dimensions(1F, 0.0625F).trackingTickInterval(20).makeFireImmune());

    static void init() {
        // NO-OP
    }

    record Dummy() implements AmarongEntityTypes {
    }
}
