package survivalblock.amarong.common.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import survivalblock.amarong.common.component.VerylongswordComponent;
import survivalblock.amarong.common.entity.RailgunEntity;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongEntityComponents;
import survivalblock.amarong.common.init.AmarongTags;

import java.awt.*;

import static survivalblock.amarong.common.item.AmarongToolMaterial.BASE_BLOCK_INTERACTION_RANGE_ID;
import static survivalblock.amarong.common.item.AmarongToolMaterial.BASE_ENTITY_INTERACTION_RANGE_ID;

public class AmarongVerylongswordItem extends SwordItem {

    public static final Color RAILGUN_COLOR = new Color(224, 122, 83);
    public static final Color OBSCURE_COLOR = new Color(176, 174, 241);

    public AmarongVerylongswordItem(ToolMaterial toolMaterial, AmarongToolMaterial.Configuration configuration) {
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean railgun = EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT);
        boolean obscure = EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT);
        if (railgun || obscure) {
            if (checkForReset(stack) >= getMaxCharge(stack)) {
                if (!world.isClient()) {
                    VerylongswordComponent verylongswordComponent = AmarongEntityComponents.VERYLONGSWORD_COMPONENT.get(user);
                    if (railgun) {
                        verylongswordComponent.deathStar();
                        user.getItemCooldownManager().set(this, RailgunEntity.WARNING_TICKS);
                    } else {
                        verylongswordComponent.enterObscure();
                        user.getItemCooldownManager().set(this, VerylongswordComponent.OBSCURE);
                    }
                    stack.set(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE, 0);
                }
                return TypedActionResult.success(stack, world.isClient());
            }
        }
        if (!world.isClient()) {
            user.getItemCooldownManager().set(this, 10);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxCount() == 1;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT) || EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT)) {
            return RAILGUN_COLOR.getRGB();
        } else if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT)) {
            return OBSCURE_COLOR.getRGB();
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((checkForReset(stack) * 13.0f) / getMaxCharge(stack));
    }

    public static int getMaxCharge(ItemStack stack) {
        if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.RAILGUN_EFFECT)) {
            return 20;
        } else if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.OBSCURE_EFFECT)) {
            return 8;
        }
        return 0;
    }

    public static int checkForReset(ItemStack stack) {
        if (!stack.contains(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE)) {
            stack.set(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE, 0);
        } else {
            int max = getMaxCharge(stack);
            if (stack.get(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE) > max) {
                stack.set(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE, max);
            }
        }
        return stack.get(AmarongDataComponentTypes.VERYLONGSWORD_CHARGE);
    }


}
