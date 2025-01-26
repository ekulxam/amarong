package survivalblock.amarong.client;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

// could just be called AmarongClientConstants at this point
public class AmarongClientUtil {

    public static final Set<String> AMARONG_RESOURCE_PACKS;

    public static final ModelIdentifier KALEIDOSCOPE_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
    public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
    public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));
    public static final ModelIdentifier BOOMERANG_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_boomerang_inventory"));

    public static final Identifier NO_KALEIDOSCOPE_OVERLAY_PACK = Amarong.id("nokaleidoscopeoverlay");
    public static final Identifier SMOL_VERYLONGSWORD_PACK = Amarong.id("smolverylongsword");
    public static final Identifier OLD_TICKET_LAUNCHER_PACK = Amarong.id("oldticketlauncher");
    public static final Identifier AMETHYST_HANDLE_TICKET_LAUNCHER_PACK = Amarong.id("amethysthandleticketlauncher");
    public static final Identifier OLD_CHUNK_AND_SHEET = Amarong.id("oldchunkandsheet");

    static {
        ArrayList<Identifier> list = new ArrayList<>();
        list.add(NO_KALEIDOSCOPE_OVERLAY_PACK);
        list.add(SMOL_VERYLONGSWORD_PACK);
        list.add(OLD_TICKET_LAUNCHER_PACK);
        list.add(AMETHYST_HANDLE_TICKET_LAUNCHER_PACK);
        list.add(OLD_CHUNK_AND_SHEET);
        AMARONG_RESOURCE_PACKS = ImmutableSet.copyOf(list.stream().map(Identifier::toString).iterator());
    }
}
