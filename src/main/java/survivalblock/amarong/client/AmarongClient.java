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
import survivalblock.amarong.client.render.RailgunEntityRenderer;
import survivalblock.amarong.client.render.WaterStreamEntityRenderer;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.config.AmarongConfig;
import survivalblock.amarong.common.init.AmarongEntityTypes;
import survivalblock.amarong.common.init.AmarongParticleTypes;

public class AmarongClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(AmarongEntityTypes.WATER_STREAM, WaterStreamEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.FLYING_TICKET, FlyingTicketEntityRenderer::new);
		EntityRendererRegistry.register(AmarongEntityTypes.RAILGUN, RailgunEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(AmarongParticleTypes.RAILGUN_PARTICLE, SonicBoomParticle.Factory::new);

		FabricLoader.getInstance().getModContainer(Amarong.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(Amarong.id(AmarongClientUtil.KALEIDOSCOPE_OVERLAY_PACK_NAME), modContainer, Text.translatable("resourcePack.amarong.nokaleidoscopeoverlay.name"), ResourcePackActivationType.NORMAL);
			ResourceManagerHelper.registerBuiltinResourcePack(Amarong.id(AmarongClientUtil.SMOL_VERYLONGSWORD_PACK_NAME), modContainer, Text.translatable("resourcePack.amarong.smolverylongsword.name"), ResourcePackActivationType.NORMAL);
		});
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