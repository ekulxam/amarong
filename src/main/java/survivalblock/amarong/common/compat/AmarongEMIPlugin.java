package survivalblock.amarong.common.compat;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;
import survivalblock.amarong.mixin.compat.emi.VanillaPluginAccessor;

import java.util.List;

import static survivalblock.amarong.client.AmarongClientUtil.SUPER_SECRET_SETTING_PROGRAMS;

@SuppressWarnings("UnreachableCode")
public class AmarongEMIPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        for (CraftingRecipe recipe : VanillaPluginAccessor.amarong$invokeGetRecipes(registry, RecipeType.CRAFTING)) {
            if (recipe instanceof KaleidoscopeShaderTypeRecipe) {
                addRandomToFirst(registry, recipe);
                addConversions(registry, recipe);
                addLastToRandom(registry, recipe);
            }
        }
    }

    private void addRandomToFirst(EmiRegistry registry, CraftingRecipe recipe) {
        ItemStack kaleidoscopeItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        EmiStack kaleidoscope = EmiStack.of(kaleidoscopeItemstack);
        ItemStack resultItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        resultItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, SUPER_SECRET_SETTING_PROGRAMS.getFirst());
        VanillaPluginAccessor.amarong$invokeAddRecipeSafe(registry, () -> new EmiCraftingRecipe(List.of(kaleidoscope),
                EmiStack.of(resultItemstack),
                VanillaPluginAccessor.synthetic("crafting/kaleidoscope_shader_type", "random"),
                true), recipe);
    }

    private void addConversions(EmiRegistry registry, CraftingRecipe recipe) {
        SUPER_SECRET_SETTING_PROGRAMS.forEach(identifier -> {
            ItemStack kaleidoscopeItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
            kaleidoscopeItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, identifier);
            EmiStack kaleidoscope = EmiStack.of(kaleidoscopeItemstack);
            if (SUPER_SECRET_SETTING_PROGRAMS.getLast().equals(identifier)) {
                return;
            }
            int index = SUPER_SECRET_SETTING_PROGRAMS.indexOf(identifier) + 1;
            ItemStack resultItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
            resultItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, SUPER_SECRET_SETTING_PROGRAMS.get(index));
            VanillaPluginAccessor.amarong$invokeAddRecipeSafe(registry, () -> new EmiCraftingRecipe(List.of(kaleidoscope),
                    EmiStack.of(resultItemstack),
                    VanillaPluginAccessor.synthetic("crafting/kaleidoscope_shader_type", EmiUtil.subId(identifier)),
                    true), recipe);
        });
    }

    private void addLastToRandom(EmiRegistry registry, CraftingRecipe recipe) {
        Identifier id = SUPER_SECRET_SETTING_PROGRAMS.getLast();
        ItemStack kaleidoscopeItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        kaleidoscopeItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, id);
        EmiStack kaleidoscope = EmiStack.of(kaleidoscopeItemstack);
        ItemStack resultItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        VanillaPluginAccessor.amarong$invokeAddRecipeSafe(registry, () -> new EmiCraftingRecipe(List.of(kaleidoscope),
                EmiStack.of(resultItemstack),
                VanillaPluginAccessor.synthetic("crafting/kaleidoscope_shader_type", EmiUtil.subId(id)),
                true), recipe);
    }
}
