package survivalblock.amarong.client;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;

import java.util.ArrayList;
import java.util.List;

public class AmarongClientUtil {

    public static final ArrayList<Identifier> SUPER_SECRET_SETTING_PROGRAMS = new ArrayList<>(List.of(Identifier.ofVanilla("shaders/post/notch.json"), Identifier.ofVanilla("shaders/post/fxaa.json"), Identifier.ofVanilla("shaders/post/art.json"), Identifier.ofVanilla("shaders/post/bumpy.json"), Identifier.ofVanilla("shaders/post/blobs2.json"), Identifier.ofVanilla("shaders/post/pencil.json"), Identifier.ofVanilla("shaders/post/color_convolve.json"), Identifier.ofVanilla("shaders/post/deconverge.json"), Identifier.ofVanilla("shaders/post/flip.json"), Identifier.ofVanilla("shaders/post/invert.json"), Identifier.ofVanilla("shaders/post/ntsc.json"), Identifier.ofVanilla("shaders/post/outline.json"), Identifier.ofVanilla("shaders/post/phosphor.json"), Identifier.ofVanilla("shaders/post/scan_pincushion.json"), Identifier.ofVanilla("shaders/post/sobel.json"), Identifier.ofVanilla("shaders/post/bits.json"), Identifier.ofVanilla("shaders/post/desaturate.json"), Identifier.ofVanilla("shaders/post/green.json"), Identifier.ofVanilla("shaders/post/blur.json"), Identifier.ofVanilla("shaders/post/wobble.json"), Identifier.ofVanilla("shaders/post/blobs.json"), Identifier.ofVanilla("shaders/post/antialias.json"), Identifier.ofVanilla("shaders/post/creeper.json"), Identifier.ofVanilla("shaders/post/spider.json")));
    public static final Identifier KALEIDOSCOPE_SCOPE = Amarong.id("textures/misc/kaleidoscope_scope.png");
    public static final ModelIdentifier KALEIDOSCOPE_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
    public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
    public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));

    public static final String KALEIDOSCOPE_OVERLAY_PACK_NAME = "nokaleidoscopeoverlay";
    public static final String SMOL_VERYLONGSWORD_PACK_NAME = "smolverylongsword";

    public static boolean verylongswordPosing = false;
}
