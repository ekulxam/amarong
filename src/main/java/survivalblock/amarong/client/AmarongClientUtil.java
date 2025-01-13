package survivalblock.amarong.client;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

// could just be called AmarongClientConstants at this point
public class AmarongClientUtil {

    public static final ModelIdentifier KALEIDOSCOPE_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
    public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
    public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));
    public static final ModelIdentifier BOOMERANG_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_boomerang_inventory"));

    public static final Identifier NO_KALEIDOSCOPE_OVERLAY_PACK = Amarong.id("nokaleidoscopeoverlay");
    public static final Identifier SMOL_VERYLONGSWORD_PACK = Amarong.id("smolverylongsword");
    public static final Identifier OLD_TICKET_LAUNCHER_PACK = Amarong.id("oldticketlauncher");
    public static final Identifier AMETHYST_HANDLE_TICKET_LAUNCHER_PACK = Amarong.id("amethysthandleticketlauncher");
}
