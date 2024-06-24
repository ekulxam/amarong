package survivalblock.amarong.common.item;

import gay.lemmaeof.terrifictickets.api.TerrificTicketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.entity.FlyingTicketEntity;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;

import java.util.List;

public class TicketDispenserItem extends Item {
    public TicketDispenserItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (checkForReset(stack) <= 0) {
            return TypedActionResult.fail(stack);
        }
        if (world.isClient()) {
            return TypedActionResult.pass(stack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.success(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks % 3 != 0) {
            return;
        }
        if (world.isClient()) {
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
