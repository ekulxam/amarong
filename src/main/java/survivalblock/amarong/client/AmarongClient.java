package survivalblock.amarong.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.text.Text;
import survivalblock.amarong.client.render.FlyingTicketEntityRenderer;
import survivalblock.amarong.client.render.WaterStreamEntityRenderer;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongEntityTypes;

public class AmarongClient implements ClientModInitializer {

	public static boolean verylongswordPosing = false;

	public static final ModelIdentifier KALEIDOSCOPE = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope"));
	public static final ModelIdentifier KALEIDOSCOPE_IN_HAND = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_kaleidoscope_in_hand"));
	public static final ModelIdentifier VERYLONGSWORD_INVENTORY = ModelIdentifier.ofInventoryVariant(Amarong.id("amarong_verylongsword_inventory"));
	public static final String KALEIDOSCOPE_OVERLAY_PACK_NAME = "nokaleidoscopeoverlay";

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(AmarongEntityTypes.WATER_STREAM, WaterStreamEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.FLYING_TICKET, FlyingTicketEntityRenderer::new);

		FabricLoader.getInstance().getModContainer(Amarong.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(Amarong.id(KALEIDOSCOPE_OVERLAY_PACK_NAME), modContainer, Text.translatable("resourcePack.amarong.nokaleidoscopeoverlay.name"), ResourcePackActivationType.NORMAL);
		});
	}
}