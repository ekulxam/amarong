package survivalblock.amarong.client.compat.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.Amarong;

@Environment(EnvType.CLIENT)
public interface AmarongConfigScreenCreator {

    AmarongConfigScreenCreator INSTANCE = getInstance();

    @Nullable
    default Screen create(Screen parent) {
        return null;
    }

    static AmarongConfigScreenCreator getInstance() {
        if (Amarong.YACL) {
            return new AmarongYACLConfigScreenCreator();
        }
        return new AmarongConfigScreenCreator() {};
    }

    static void init() {
        // NO-OP
    }
}
