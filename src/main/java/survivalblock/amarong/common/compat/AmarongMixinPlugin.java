package survivalblock.amarong.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import survivalblock.amarong.common.Amarong;

import java.util.List;
import java.util.Set;

public class AmarongMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("compat")) {
            if (mixinClassName.contains("honque")) {
                return FabricLoader.getInstance().isModLoaded("honque");
            } else if (mixinClassName.contains("config")) {
                return Amarong.resetShouldDoConfig();
            } else if (mixinClassName.contains("twirl")) {
                return FabricLoader.getInstance().isModLoaded("twirl");
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
