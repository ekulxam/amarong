package survivalblock.amarong.common.compat.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import survivalblock.amarong.client.compat.config.AmarongConfigScreen;

public class AmarongModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return AmarongConfigScreen::new;
    }
}
