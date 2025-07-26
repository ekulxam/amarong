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
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongTags;
import survivalblock.amarong.mixin.staff.ItemUsageContextAccessor;
import survivalblock.amarong.mixin.staff.TypedActionResultAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AmarongStaffItem extends Item {

    public static final Identifier STAFF_ENTITY_REACH_ID = Amarong.id("staff_entity_reach");
    public static final Identifier STAFF_BLOCK_REACH_ID = Amarong.id("staff_block_reach");

    @ApiStatus.Internal
    public static boolean usingStaff = false;

    public AmarongStaffItem(Settings settings) {
        super(settings);
    }

    public ItemStack getStaffStack(ItemStack stack) {
        ItemStack otherStack = stack.atmospheric_api$getOrCreate(AmarongDataComponentTypes.STAFF_STACK, ItemStack.EMPTY);
        if (otherStack == null) {
            otherStack = ItemStack.EMPTY;
            stack.set(AmarongDataComponentTypes.STAFF_STACK, otherStack);
        }
        return otherStack;
    }

    @Override
    public boolean allowContinuingBlockBreaking(PlayerEntity player, ItemStack oldStack, ItemStack newStack) {
        if (oldStack == null || newStack == null || oldStack.isEmpty() || newStack.isEmpty()) {
            return super.allowContinuingBlockBreaking(player, oldStack, newStack);
        }
        ItemStack copyOld = oldStack.copy();
        ItemStack copyNew = newStack.copy();
        copyOld.remove(AmarongDataComponentTypes.STAFF_STACK);
        copyNew.remove(AmarongDataComponentTypes.STAFF_STACK);
        return ItemStack.areEqual(copyOld, copyNew);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return !allowContinuingBlockBreaking(player, oldStack, newStack);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemStack otherStack = this.getStaffStack(entity.getStack());
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
        return staff.getStaffStack(stack).getItem().canMine(state, world, pos, miner);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack staff = context.getStack();
        ItemStack otherStack = this.getStaffStack(staff);
        ((ItemUsageContextAccessor) context).amarong$setStack(otherStack);
        usingStaff = true;
        ActionResult actionResult = otherStack.useOnBlock(context);
        usingStaff = false;
        this.postDoSomething(staff);
        return actionResult;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        return this.getStaffStack(stack).getMiningSpeedMultiplier(state);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack staff = user.getStackInHand(hand);
        ItemStack other = this.getStaffStack(staff);
        usingStaff = true;
        TypedActionResult<ItemStack> typedActionResult = other.use(world, user, hand);
        usingStaff = false;
        this.postDoSomething(staff);
        ItemStack value = typedActionResult.getValue();
        if (!ItemStack.areEqual(other, value)) {
            staff.set(AmarongDataComponentTypes.STAFF_STACK, value);
        }
        ((TypedActionResultAccessor<ItemStack>) typedActionResult).amarong$setValue(staff);
        return typedActionResult;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return this.getStaffStack(stack).isItemBarVisible();
    }

    public int getItemBarStep(ItemStack stack) {
        return this.getStaffStack(stack).getItemBarStep();
    }

    public int getItemBarColor(ItemStack stack) {
        return this.getStaffStack(stack).getItemBarColor();
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
        ItemStack stackInComponents = this.getStaffStack(stack);
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
        return this.getStaffStack(weapon).getItem().getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemStack otherStack = this.postDoSomething(stack);
        usingStaff = true;
        boolean hit = otherStack.getItem().postHit(otherStack, target, attacker);
        usingStaff = false;
        this.postDoSomething(stack);
        return hit;
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemStack otherStack = this.postDoSomething(stack);
        usingStaff = true;
        otherStack.getItem().postDamageEntity(otherStack, target, attacker);
        usingStaff = false;
        this.postDoSomething(stack);
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ItemStack otherStack = this.postDoSomething(stack);
        usingStaff = true;
        boolean mine = otherStack.getItem().postMine(otherStack, world, state, pos, miner);
        usingStaff = false;
        this.postDoSomething(stack);
        return mine;
    }

    public ItemStack postDoSomething(ItemStack stack) {
        ItemStack otherStack = this.getStaffStack(stack).copy();
        stack.set(AmarongDataComponentTypes.STAFF_STACK, otherStack);
        return otherStack;
    }

    public boolean isCorrectForDrops(ItemStack stack, BlockState state) {
        ItemStack otherStack = this.getStaffStack(stack);
        return otherStack.getItem().isCorrectForDrops(otherStack, state);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack otherStack = this.getStaffStack(stack);
        usingStaff = true;
        ActionResult actionResult = otherStack.useOnEntity(user, entity, hand);
        usingStaff = false;
        this.postDoSomething(stack);
        return actionResult;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        usingStaff = true;
        this.getStaffStack(stack).inventoryTick(world, entity, slot, false);
        usingStaff = false;
        this.postDoSomething(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return this.getStaffStack(stack).getUseAction();
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return this.getStaffStack(stack).getMaxUseTime(user);
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        usingStaff = true;
        this.getStaffStack(stack).onStoppedUsing(world, user, remainingUseTicks);
        usingStaff = false;
        this.postDoSomething(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        usingStaff = true;
        stack.set(AmarongDataComponentTypes.STAFF_STACK, this.getStaffStack(stack).finishUsing(world, user));
        usingStaff = false;
        this.postDoSomething(stack);
        return stack;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        ItemStack otherStack = this.postDoSomething(stack);
        usingStaff = true;
        boolean used = otherStack.isUsedOnRelease();
        usingStaff = false;
        this.postDoSomething(stack);
        return used;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        ItemStack otherStack = this.getStaffStack(stack);
        if (otherStack != null && !otherStack.isEmpty()) {
            tooltip.add(Text.translatable("container.shulkerBox.itemCount", otherStack.getName(), otherStack.getCount()));
        }
    }

    public boolean hasGlint(ItemStack stack) {
        return this.getStaffStack(stack).hasGlint();
    }

    @SuppressWarnings("unused")
    public static AttributeModifiersComponent createAttributeModifiers(float reach) {
        return createAttributeModifiers(reach, reach);
    }

    public static AttributeModifiersComponent createAttributeModifiers(float buildReach, float attackReach) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(STAFF_ENTITY_REACH_ID, attackReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, new EntityAttributeModifier(STAFF_BLOCK_REACH_ID, buildReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public static class CombinedComponentMap implements ComponentMap {
        private final ComponentMap original; // staff
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
                //noinspection unchecked
                return (T) builder.build();
            }
            if (isIn(type, AmarongTags.AmarongDataComponentTypeTags.STAFF_PRIORITY)) {
                return other.get(type);
            }
            T t = original.get(type);
            if (t != null) {
                return t;
            }
            if (!isIn(type, AmarongTags.AmarongDataComponentTypeTags.STAFF_VALID)) {
                return null;
            }
            return other.get(type);
        }

        @Override
        public Set<ComponentType<?>> getTypes() {
            Set<ComponentType<?>> set = Sets.union(original.getTypes(), other.getTypes());
            return set.stream().filter(this::contains).collect(Collectors.toSet());
        }

        public static boolean isIn(ComponentType<?> type, TagKey<ComponentType<?>> tag) {
            return Registries.DATA_COMPONENT_TYPE.getEntry(type).isIn(tag);
        }
    }
}
