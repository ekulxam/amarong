package survivalblock.amarong.common.item;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.ExtendableItemSettings;

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

    public static class Configuration extends ExtendableItemSettings<Configuration> {

        public Configuration() {
            super();
            this.maxCount();
        }

        @Override
        public Configuration maxDamage(int maxDamage) {
            return this;
        }

        @Override
        public Configuration maxCount(int maxCount) {
            return apply(super::maxCount, 1);
        }

        @SuppressWarnings("UnusedReturnValue")
        public Configuration maxCount() {
            return this.maxCount(1);
        }

        @Override
        public Configuration attributeModifiers(AttributeModifiersComponent attributeModifiersComponent) {
            return apply(super::attributeModifiers, attributeModifiersComponent);
        }

        @Override
        public Configuration rarity(Rarity rarity) {
            return apply(super::rarity, rarity);
        }

        @Override
        public <T> Configuration component(ComponentType<T> type, T value) {
            return apply(super::component, type, value);
        }

        @Override
        public Configuration recipeRemainder(Item recipeRemainder) {
            return apply(super::recipeRemainder, recipeRemainder);
        }

        @Override
        public Configuration fireproof() {
            return apply(super::fireproof);
        }

        @Override
        public Configuration equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
            return apply(super::equipmentSlot, equipmentSlotProvider);
        }
    }
}
