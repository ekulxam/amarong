package survivalblock.amarong.common.compat;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.recipe.KaleidoscopeShaderTypeRecipe;

import java.util.List;

import static survivalblock.amarong.common.item.KaleidoscopeItem.SUPER_SECRET_SETTING_PROGRAMS;

@SuppressWarnings("UnreachableCode")
public class AmarongEMIPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();
        for (RecipeEntry<CraftingRecipe> entry : manager.listAllOfType(RecipeType.CRAFTING)) {
            CraftingRecipe recipe = entry.value();
            if (recipe instanceof KaleidoscopeShaderTypeRecipe) {
                addRandomToFirst(registry);
                addConversions(registry);
                addLastToRandom(registry);
            }
        }
    }

    private void addRandomToFirst(EmiRegistry registry) {
        ItemStack kaleidoscopeItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        EmiStack kaleidoscope = EmiStack.of(kaleidoscopeItemstack);
        ItemStack resultItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        resultItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, SUPER_SECRET_SETTING_PROGRAMS.getFirst());
        registry.addRecipe(new EmiCraftingRecipe(List.of(kaleidoscope),
                EmiStack.of(resultItemstack),
                Amarong.id("/crafting/kaleidoscope_shader_type/first"),
                true));
    }

    private void addConversions(EmiRegistry registry) {
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
            registry.addRecipe(new EmiCraftingRecipe(List.of(kaleidoscope),
                    EmiStack.of(resultItemstack),
                    Amarong.id("crafting/kaleidoscope_shader_type/" + EmiUtil.subId(identifier)),
                    true));
        });
    }

    private void addLastToRandom(EmiRegistry registry) {
        Identifier id = SUPER_SECRET_SETTING_PROGRAMS.getLast();
        ItemStack kaleidoscopeItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        kaleidoscopeItemstack.set(AmarongDataComponentTypes.SHADER_TYPE, id);
        EmiStack kaleidoscope = EmiStack.of(kaleidoscopeItemstack);
        ItemStack resultItemstack = new ItemStack(AmarongItems.KALEIDOSCOPE);
        registry.addRecipe(new EmiCraftingRecipe(List.of(kaleidoscope),
                EmiStack.of(resultItemstack),
                Amarong.id("crafting/kaleidoscope_shader_type/" + EmiUtil.subId(id)),
                true));
    }

}
