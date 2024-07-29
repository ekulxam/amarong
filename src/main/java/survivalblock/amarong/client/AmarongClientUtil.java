package survivalblock.amarong.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

public class AmarongClientUtil {

    public static final Identifier KALEIDOSCOPE_SCOPE = Amarong.id("textures/misc/kaleidoscope_scope.png");
    public static final ModelIdentifier KALEIDOSCOPE_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
    public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
    public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));

    public static final String KALEIDOSCOPE_OVERLAY_PACK_NAME = "nokaleidoscopeoverlay";
    public static final String SMOL_VERYLONGSWORD_PACK_NAME = "smolverylongsword";

    public static boolean verylongswordPosing = false;

    public static boolean isResourcePackLoaded(MinecraftClient client, String name) {
        return client.getResourcePackManager().getEnabledIds().contains(name);
    }

    public static boolean isResourcePackLoaded(String name) {
        return isResourcePackLoaded(MinecraftClient.getInstance(), name);
    }
}
