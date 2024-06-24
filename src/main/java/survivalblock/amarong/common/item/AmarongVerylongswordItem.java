package survivalblock.amarong.common.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;

public class AmarongVerylongswordItem extends SwordItem {

    public static final Identifier BASE_ENTITY_INTERACTION_RANGE_ID = Identifier.ofVanilla("base_entity_interaction_range");
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE_ID = Identifier.ofVanilla("base_block_interaction_range");

    public AmarongVerylongswordItem(ToolMaterial toolMaterial, Configuration configuration) {
        super(toolMaterial, configuration);
    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackDamage, float attackSpeed, float buildReach, float attackReach) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage - 1, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed - 4, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, attackReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, new EntityAttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, buildReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxCount() == 1;
    }

    public static class Configuration extends Item.Settings {

        public Configuration() {
            super();
        }

        @Override
        public Item.Settings maxDamage(int maxDamage) {
            return this;
        }

        @Override
        public Item.Settings maxCount(int maxCount) {
            return super.maxCount(1);
        }

        @SuppressWarnings("unused")
        public Item.Settings maxCount() {
            return this.maxCount(1);
        }
    }
}
