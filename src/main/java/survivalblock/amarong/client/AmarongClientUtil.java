package survivalblock.amarong.client;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

public class AmarongClientUtil {

    public static final ModelIdentifier KALEIDOSCOPE_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
    public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
    public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));

    public static final Identifier NO_KALEIDOSCOPE_OVERLAY_PACK = Amarong.id("nokaleidoscopeoverlay");
    public static final Identifier SMOL_VERYLONGSWORD_PACK = Amarong.id("smolverylongsword");
}
