package survivalblock.amarong.common.item;

import gay.lemmaeof.terrifictickets.api.TerrificTicketsApi;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.entity.FlyingTicketEntity;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongTags;

import java.util.List;

public class TicketLauncherItem extends Item {
    public TicketLauncherItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack offHandStack = user.getStackInHand(Hand.OFF_HAND);
        boolean pnuematic = isPnuematic(user, stack, offHandStack);
        boolean particleAccelerator = isParticleAccelerator(user, stack, offHandStack);
        if (checkForReset(stack) <= 0 && !pnuematic && !particleAccelerator) {
            if (!world.isClient()) {
                user.getItemCooldownManager().set(stack.getItem(), 10);
            }
            return TypedActionResult.fail(stack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    private static boolean isPnuematic(LivingEntity user, ItemStack stack, ItemStack offHandStack) {
        return EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.PNUEMATIC_EFFECT) && offHandStack.isOf(Items.WIND_CHARGE) && user instanceof PlayerEntity;
    }

    private static boolean isParticleAccelerator(LivingEntity user, ItemStack stack, ItemStack offHandStack) {
        return EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT) && offHandStack.getItem() instanceof ThrowablePotionItem && user instanceof PlayerEntity;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks % 3 != 0) {
            return;
        }
        if (world.isClient()) {
            return;
        }
        ItemStack offHandStack = user.getOffHandStack();
        boolean pnuematic = isPnuematic(user, stack, offHandStack);
        boolean particleAccelerator = isParticleAccelerator(user, stack, offHandStack);
        if (pnuematic || particleAccelerator) {
            if (particleAccelerator) {
                AmarongUtil.increaseThrowablePotionVelocity = true;
            }
            offHandStack.use(world, (PlayerEntity) user, Hand.OFF_HAND);
            AmarongUtil.increaseThrowablePotionVelocity = false;
            user.setCurrentHand(Hand.MAIN_HAND);
            return;
        }
        int tickets = checkForReset(stack);
        if (tickets <= 0) {
            user.stopUsingItem();
            stack.set(AmarongDataComponentTypes.TICKETS, 0);
            return;
        }
        tickets--;
        stack.set(AmarongDataComponentTypes.TICKETS, tickets);
        FlyingTicketEntity flyingTicket = new FlyingTicketEntity(world, user, stack);
        flyingTicket.setVelocity(Vec3d.fromPolar(user.getPitch(), user.getYaw()).multiply(1.2));
        world.spawnEntity(flyingTicket);
        if (user instanceof ServerPlayerEntity serverPlayer) {
            AmarongUtil.grantAdvancment(serverPlayer, Amarong.id("when_tickets_fly"));
        }
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        // return this.exchangeTickets(stack, slot.getStack(), clickType);
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return this.exchangeTickets(stack, otherStack, clickType);
    }

    private boolean exchangeTickets(ItemStack stack, ItemStack otherStack, ClickType clickType) {
        if (!clickType.equals(ClickType.RIGHT)) {
            return false;
        }
        checkForReset(stack);
        int tickets = TerrificTicketsApi.getTickets(otherStack);
        if (tickets <= 0) {
            return false;
        }
        tickets += stack.get(AmarongDataComponentTypes.TICKETS); // should be not null because I did a check earlier
        stack.set(AmarongDataComponentTypes.TICKETS, tickets);
        TerrificTicketsApi.removeTickets(otherStack, tickets);
        return true;
    }

    public static int checkForReset(ItemStack stack) {
        if (!stack.contains(AmarongDataComponentTypes.TICKETS)) {
            stack.set(AmarongDataComponentTypes.TICKETS, 0);
        }
        return stack.get(AmarongDataComponentTypes.TICKETS);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        int tickets = checkForReset(stack);
        tooltip.add(Text.translatable("tooltip.terrifictickets.ticket_display", tickets).formatted(Formatting.GRAY));
    }
}
