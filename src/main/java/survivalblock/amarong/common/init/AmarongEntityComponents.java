package survivalblock.amarong.common.init;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.component.KaleidoscopeShaderComponent;
import survivalblock.amarong.common.component.VerylongswordComponent;

public class AmarongEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<KaleidoscopeShaderComponent> KALEIDOSCOPE_SHADER = ComponentRegistry.getOrCreate(Amarong.id("kaleidoscope_shader"), KaleidoscopeShaderComponent.class);
    public static final ComponentKey<VerylongswordComponent> VERYLONGSWORD_COMPONENT = ComponentRegistry.getOrCreate(Amarong.id("verylongsword_component"), VerylongswordComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(KALEIDOSCOPE_SHADER, KaleidoscopeShaderComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(VERYLONGSWORD_COMPONENT, VerylongswordComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
