package survivalblock.amarong.common.init;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.block.AmarongCoreBlock;
import survivalblock.amarong.common.block.AmarongCoreBlockEntity;

public class AmarongBlocks {

    public static final Block AMARONG_CORE = registerBlock("amarong_core",
            new AmarongCoreBlock(AbstractBlock.Settings.copy(Blocks.HEAVY_CORE).
                    requiresTool()
                    .mapColor(MapColor.ORANGE) // copper
                    .luminance((state) -> 10)));

    public static final BlockEntityType<AmarongCoreBlockEntity> AMARONG_CORE_BLOCK_ENTITY = registerBlockEntity(
            "amarong_core_block_entity",
            BlockEntityType.Builder.create(AmarongCoreBlockEntity::new, AMARONG_CORE).build()
    );

    @SuppressWarnings("SameParameterValue")
    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Amarong.id(name), block);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Amarong.id(name), blockEntityType);
    }

    public static void init() {

    }
}
