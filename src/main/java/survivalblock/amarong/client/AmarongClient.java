package survivalblock.amarong.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.text.Text;
import survivalblock.amarong.client.render.FlyingTicketEntityRenderer;
import survivalblock.amarong.client.render.RailgunEntityRenderer;
import survivalblock.amarong.client.render.WaterStreamEntityRenderer;
import survivalblock.amarong.common.Amarong;
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
	}
}