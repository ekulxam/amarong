package survivalblock.amarong.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class AmarongToolMaterial implements ToolMaterial {

    public static final Identifier BASE_ENTITY_INTERACTION_RANGE_ID = Identifier.ofVanilla("base_entity_interaction_range");
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE_ID = Identifier.ofVanilla("base_block_interaction_range");

    public static final AmarongToolMaterial INSTANCE = new AmarongToolMaterial();

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9.0F;
    }

    @Override
    public float getAttackDamage() {
        return 3.0F;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantability() {
        return 18;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    public static class Configuration extends Item.Settings {

        public Configuration() {
            super();
            this.maxCount();
        }

        @Override
        public Item.Settings maxDamage(int maxDamage) {
            return this;
        }

        @Override
        public Item.Settings maxCount(int maxCount) {
            return super.maxCount(1);
        }

        @SuppressWarnings({"UnusedReturnValue", "unused"})
        public Item.Settings maxCount() {
            return this.maxCount(1);
        }
    }
}
