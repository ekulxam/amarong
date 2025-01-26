package survivalblock.amarong.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.amarong.common.init.AmarongBlocks;
import survivalblock.atmosphere.atmospheric_api.not_mixin.block.NonRegisterableBlock;

@SuppressWarnings("deprecation")
@ApiStatus.Internal
@ApiStatus.Experimental
public final class StainableAmarongCoreWrapper extends Block implements NonRegisterableBlock, Stainable {

    public static final StainableAmarongCoreWrapper INSTANCE = new StainableAmarongCoreWrapper(AmarongBlocks.AMARONG_CORE.getSettings());

    public StainableAmarongCoreWrapper(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.BLACK; // fallback
    }
}
