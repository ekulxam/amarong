package survivalblock.amarong.common.init;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.block.AmarongCoreBlock;
import survivalblock.amarong.common.block.AmarongCoreBlockEntity;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.BlockEntityTypeRegistrant;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.BlockRegistrant;

@SuppressWarnings("UnstableApiUsage")
public sealed interface AmarongBlocks permits AmarongBlocks.Dummy {
    BlockRegistrant registrant = new BlockRegistrant(Amarong::id);
    BlockEntityTypeRegistrant beregistrant = new BlockEntityTypeRegistrant(Amarong::id);

    Block AMARONG_CORE = registrant.register("amarong_core",
            AmarongCoreBlock::new,
            AbstractBlock.Settings.copy(Blocks.HEAVY_CORE)
                    .requiresTool()
                    .mapColor(MapColor.ORANGE) // copper
                    .luminance((state) -> 10)
    );

    BlockEntityType<AmarongCoreBlockEntity> AMARONG_CORE_BLOCK_ENTITY = beregistrant.register(
            "amarong_core_block_entity",
            BlockEntityType.Builder.create(AmarongCoreBlockEntity::new, AMARONG_CORE).build()
    );

    static void init() {
        // NO-OP
    }

    record Dummy() implements AmarongBlocks {
    }
}
