package survivalblock.amarong.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongItems;

import static survivalblock.amarong.client.AmarongClientUtil.SUPER_SECRET_SETTING_PROGRAMS;

public class KaleidoscopeShaderTypeRecipe extends SpecialCraftingRecipe {

    public static final RecipeSerializer<KaleidoscopeShaderTypeRecipe> SERIALIZER = new SpecialRecipeSerializer<>(KaleidoscopeShaderTypeRecipe::new);

    public KaleidoscopeShaderTypeRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack stack = getStack(input);
        return stack != null && !stack.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack stack = getStack(input);
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(AmarongItems.KALEIDOSCOPE);
        if (stack.contains(AmarongDataComponentTypes.SHADER_TYPE)) {
            Identifier shaderTypeOfOriginal = stack.get(AmarongDataComponentTypes.SHADER_TYPE);
            if (shaderTypeOfOriginal == null) {
                return result;
            }
            if (SUPER_SECRET_SETTING_PROGRAMS.contains(shaderTypeOfOriginal)) {
                if (shaderTypeOfOriginal.equals(SUPER_SECRET_SETTING_PROGRAMS.getLast())) {
                    return result; // go back to random if reached end
                }
                int index = SUPER_SECRET_SETTING_PROGRAMS.indexOf(shaderTypeOfOriginal) + 1;
                if (index >= SUPER_SECRET_SETTING_PROGRAMS.size()) {
                    return result;
                }
                result.set(AmarongDataComponentTypes.SHADER_TYPE, SUPER_SECRET_SETTING_PROGRAMS.get(index));
            }
        } else if (!SUPER_SECRET_SETTING_PROGRAMS.isEmpty()) {
            result.set(AmarongDataComponentTypes.SHADER_TYPE, SUPER_SECRET_SETTING_PROGRAMS.getFirst());
        }
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 1 && height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private ItemStack getStack(CraftingRecipeInput input) {
        ItemStack stack;
        if (input.getHeight() == 1 && input.getWidth() == 1) {
            for (int i = 0; i < input.getHeight(); i++) {
                for (int j = 0; j < input.getWidth(); j++) {
                    stack = input.getStackInSlot(j, i);
                    if (stack.isOf(AmarongItems.KALEIDOSCOPE)) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, Amarong.id("crafting_special_kaleidoscope_shader_type"), SERIALIZER);
    }
}
