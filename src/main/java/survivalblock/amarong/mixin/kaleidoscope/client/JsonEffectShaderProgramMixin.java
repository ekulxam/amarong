package survivalblock.amarong.mixin.kaleidoscope.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.amarong.common.Amarong;

@Mixin(JsonEffectShaderProgram.class)
public class JsonEffectShaderProgramMixin {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
    private Identifier onInit(String path, Operation<Identifier> original) {
        return loadFromAmarongNamespace(path, original);
    }

    @WrapOperation(method = "loadEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
    private static Identifier onLoadEffect(String path, Operation<Identifier> original) {
        return loadFromAmarongNamespace(path, original);
    }

    @Unique
    private static Identifier loadFromAmarongNamespace(String path, Operation<Identifier> original) {
        String mod = Amarong.MOD_ID + ":";
        if (path.contains(mod)) {
            String pathOnly = path.replaceFirst(mod, "");
            return Amarong.id(pathOnly);
        }
        return original.call(path);
    }
}
