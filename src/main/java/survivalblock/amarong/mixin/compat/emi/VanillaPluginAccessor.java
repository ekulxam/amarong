package survivalblock.amarong.mixin.compat.emi;

import dev.emi.emi.VanillaPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(VanillaPlugin.class)
public interface VanillaPluginAccessor {

    @Invoker("getRecipes")
    static <C extends RecipeInput, T extends Recipe<C>> Iterable<T> amarong$invokeGetRecipes(EmiRegistry registry, RecipeType<T> type) {
        throw new UnsupportedOperationException();
    }

    @Invoker("addRecipeSafe")
    static void amarong$invokeAddRecipeSafe(EmiRegistry registry, Supplier<EmiRecipe> supplier, Recipe<?> recipe) {
        throw new UnsupportedOperationException();
    }

    @Invoker("synthetic")
    static Identifier synthetic(String type, String name) {
        throw new UnsupportedOperationException();
    }
}
