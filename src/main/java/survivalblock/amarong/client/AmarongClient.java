package survivalblock.amarong.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.text.Text;
import survivalblock.amarong.client.render.FlyingTicketEntityRenderer;
import survivalblock.amarong.client.render.PhasingBoomerangEntityRenderer;
import survivalblock.amarong.client.render.RailgunEntityRenderer;
import survivalblock.amarong.client.render.WaterStreamEntityRenderer;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.init.AmarongParticleTypes;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.client.AlternateItemModelRegistry;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.client.AtmosphericSpecialItemRenderHandler;

public class AmarongClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(AmarongEntityTypes.WATER_STREAM, WaterStreamEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.FLYING_TICKET, FlyingTicketEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.RAILGUN, RailgunEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.BOOMERANG, PhasingBoomerangEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(AmarongParticleTypes.RAILGUN_PARTICLE, SonicBoomParticle.Factory::new);

		AlternateItemModelRegistry.register(AmarongItems.AMARONG_VERYLONGSWORD, AmarongClientUtil.VERYLONGSWORD_INVENTORY);
		AlternateItemModelRegistry.register(AmarongItems.AMARONG_BOOMERANG, AmarongClientUtil.BOOMERANG_INVENTORY);
		AlternateItemModelRegistry.register(AmarongItems.KALEIDOSCOPE, AmarongClientUtil.KALEIDOSCOPE_INVENTORY);
		AlternateItemModelRegistry.registerSpyglass(AmarongItems.KALEIDOSCOPE, AmarongClientUtil.KALEIDOSCOPE_IN_HAND);

		FabricLoader.getInstance().getModContainer(Amarong.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(AmarongClientUtil.NO_KALEIDOSCOPE_OVERLAY_PACK, modContainer, Text.translatable("resourcePack.amarong.nokaleidoscopeoverlay.name"), ResourcePackActivationType.NORMAL);
			ResourceManagerHelper.registerBuiltinResourcePack(AmarongClientUtil.SMOL_VERYLONGSWORD_PACK, modContainer, Text.translatable("resourcePack.amarong.smolverylongsword.name"), ResourcePackActivationType.NORMAL);
		});

		AtmosphericSpecialItemRenderHandler.handleShouldZoomIn(AmarongItems.KALEIDOSCOPE, stack -> !AmarongConfig.noKaleidoscopeZoom());
		AtmosphericSpecialItemRenderHandler.handleShouldRenderOverlay(AmarongItems.KALEIDOSCOPE, stack -> !MinecraftClient.getInstance().atmospheric_api$isResourcePackLoaded(AmarongClientUtil.NO_KALEIDOSCOPE_OVERLAY_PACK.toString()));
		AtmosphericSpecialItemRenderHandler.handleShouldRenderTwoHanded(AmarongItems.AMARONG_VERYLONGSWORD, stack -> AmarongConfig.twoHandedVerylongsword());

		//noinspection CodeBlock2Expr
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("amarongconfig").executes(context -> {
				if (!Amarong.shouldDoConfig) {
					context.getSource().sendFeedback(Text.stringifiedTranslatable("commands.amarongconfig.noyacl"));
					return 0;
				}
				if (!Amarong.configLoaded) {
					context.getSource().sendFeedback(Text.stringifiedTranslatable("commands.amarongconfig.fail"));
					return 0;
				}
				MinecraftClient client = context.getSource().getClient();
				Screen configScreen = AmarongConfig.create(null);
				if (configScreen == null) {
					context.getSource().sendFeedback(Text.stringifiedTranslatable("commands.amarongconfig.fail"));
					return 0;
				}
				client.send(() -> client.setScreen(configScreen));
				return 1;
			}));
		});
	}
}