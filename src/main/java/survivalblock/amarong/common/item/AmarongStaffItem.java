package survivalblock.amarong.common.item;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongTags;
import survivalblock.amarong.mixin.staff.ItemUsageContextAccessor;
import survivalblock.atmosphere.atmospheric_api.not_mixin.item.TwoHandedItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static survivalblock.amarong.common.item.AmarongToolMaterial.BASE_BLOCK_INTERACTION_RANGE_ID;
import static survivalblock.amarong.common.item.AmarongToolMaterial.BASE_ENTITY_INTERACTION_RANGE_ID;

public class AmarongStaffItem extends Item {

    public AmarongStaffItem(Settings settings) {
        super(settings);
    }

    public ItemStack getItemStackFromComponents(ItemStack stack) {
        return stack.atmospheric_api$getOrCreate(AmarongDataComponentTypes.STAFF_STACK, ItemStack.EMPTY);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemStack otherStack = this.getItemStackFromComponents(entity.getStack());
        ItemUsage.spawnItemContents(entity, List.of(otherStack.copy()));
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        ItemStack stack = miner.getMainHandStack();
        if (stack == null || stack.isEmpty()) {
            return super.canMine(state, world, pos, miner);
        }
        if (!(stack.getItem() instanceof AmarongStaffItem staff)) {
            return super.canMine(state, world, pos, miner);
        }
        return staff.getItemStackFromComponents(stack).getItem().canMine(state, world, pos, miner);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack otherStack = this.getItemStackFromComponents(context.getStack());
        if (!(otherStack.getItem() instanceof BlockItem)) {
            return ActionResult.PASS;
        }
        ((ItemUsageContextAccessor) context).amarong$setStack(otherStack);
        return otherStack.useOnBlock(context);
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        return this.getItemStackFromComponents(stack).getMiningSpeedMultiplier(state);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        final boolean isClient = world.isClient;
        if (!isClient) {
            user.setCurrentHand(hand);
        }
        return TypedActionResult.success(stack, isClient);
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return this.getItemStackFromComponents(stack).isItemBarVisible();
    }

    public int getItemBarStep(ItemStack stack) {
        return this.getItemStackFromComponents(stack).getItemBarStep();
    }

    public int getItemBarColor(ItemStack stack) {
        return this.getItemStackFromComponents(stack).getItemBarColor();
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return super.onStackClicked(stack, slot, clickType, player);
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!ClickType.RIGHT.equals(clickType)) {
            return false;
        }
        if (stack.getItem() instanceof AmarongStaffItem && otherStack.getItem() instanceof AmarongStaffItem) {
            return false;
        }
        ItemStack stackInComponents = this.getItemStackFromComponents(stack);
        if (ItemStack.areEqual(stackInComponents, otherStack)) {
            return false;
        }
        stack.set(AmarongDataComponentTypes.STAFF_STACK, otherStack.copy());
        cursorStackReference.set(stackInComponents.copy());
        return true;
    }

    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        if (!(damageSource.getSource() instanceof LivingEntity livingEntity)) {
            return super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
        }
        ItemStack weapon = livingEntity.getWeaponStack();
        if (weapon.isEmpty()) {
            return super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
        }
        return this.getItemStackFromComponents(weapon).getItem().getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemStack otherStack = this.postDoSomething(stack);
        return otherStack.getItem().postHit(otherStack, target, attacker);
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemStack otherStack = this.postDoSomething(stack);
        otherStack.getItem().postDamageEntity(otherStack, target, attacker);
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ItemStack otherStack = this.postDoSomething(stack);
        return otherStack.getItem().postMine(otherStack, world, state, pos, miner);
    }

    public ItemStack postDoSomething(ItemStack stack) {
        ItemStack otherStack = this.getItemStackFromComponents(stack).copy();
        stack.set(AmarongDataComponentTypes.STAFF_STACK, otherStack);
        return otherStack;
    }

    public boolean isCorrectForDrops(ItemStack stack, BlockState state) {
        ItemStack otherStack = this.getItemStackFromComponents(stack);
        return otherStack.getItem().isCorrectForDrops(otherStack, state);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return super.useOnEntity(stack, user, entity, hand);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(this.getItemStackFromComponents(stack).getName());
    }

    public boolean hasGlint(ItemStack stack) {
        return this.getItemStackFromComponents(stack).hasGlint();
    }

    @SuppressWarnings("unused")
    public static AttributeModifiersComponent createAttributeModifiers(float reach) {
        return createAttributeModifiers(reach, reach);
    }

    public static AttributeModifiersComponent createAttributeModifiers(float buildReach, float attackReach) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, attackReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, new EntityAttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, buildReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public static class CombinedComponentMap implements ComponentMap {
        private final ComponentMap original;
        private final ComponentMap other;

        public CombinedComponentMap(ComponentMap original, ComponentMap other) {
            this.original = original;
            this.other = other;
        }

        @Nullable
        @Override
        public <T> T get(ComponentType<? extends T> type) {
            if (type.equals(DataComponentTypes.ATTRIBUTE_MODIFIERS)) {
                AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                AttributeModifiersComponent originalAttributes = original.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                AttributeModifiersComponent otherAttributes = other.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                Map<RegistryEntry<EntityAttribute>, AttributeModifierSlot> map = new HashMap<>();
                for (var entry : originalAttributes.modifiers()) {
                    builder.add(entry.attribute(), entry.modifier(), entry.slot());
                    map.put(entry.attribute(), entry.slot());
                }
                for (var entry : otherAttributes.modifiers()) {
                    RegistryEntry<EntityAttribute> attribute = entry.attribute();
                    if (map.containsKey(attribute) && map.get(attribute).equals(entry.slot())) {
                        continue;
                    }
                    builder.add(entry.attribute(), entry.modifier(), entry.slot());
                }
                return (T) builder.build();
            }
            T t = original.get(type);
            if (t != null) {
                return t;
            }
            if (shouldIgnore(type)) {
                return null;
            }
            return other.get(type);
        }

        @Override
        public Set<ComponentType<?>> getTypes() {
            Set<ComponentType<?>> set = Sets.union(original.getTypes(), other.getTypes());;
            return set.stream().filter(this::contains).collect(Collectors.toSet());
        }

        public static boolean shouldIgnore(ComponentType<?> type) {
            return isIn(type, AmarongTags.AmarongDataComponentTypeTags.STAFF_IGNORE);
        }

        public static boolean isIn(ComponentType<?> type, TagKey<ComponentType<?>> tag) {
            return Registries.DATA_COMPONENT_TYPE.getEntry(type).isIn(tag);
        }
    }
}
