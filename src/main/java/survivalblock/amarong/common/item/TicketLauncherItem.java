package survivalblock.amarong.common.item;

import gay.lemmaeof.terrifictickets.TerrificTickets;
import gay.lemmaeof.terrifictickets.api.TerrificTicketsApi;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
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

    public static final Identifier TICKET_LAUNCHER_ACHIEVEMENT = Amarong.id("when_tickets_fly");

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
        return EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.PARTICLE_ACCELERATOR_EFFECT) && (offHandStack.getItem() instanceof ThrowablePotionItem || offHandStack.getItem() instanceof ExperienceBottleItem) && user instanceof PlayerEntity;
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
        if (!AmarongUtil.shouldRetainCharge(stack)) {
            tickets--;
            stack.set(AmarongDataComponentTypes.TICKETS, tickets);
        }
        FlyingTicketEntity flyingTicket = new FlyingTicketEntity(world, user, stack);
        flyingTicket.setVelocity(Vec3d.fromPolar(user.getPitch(), user.getYaw()).multiply(1.2));
        world.spawnEntity(flyingTicket);
        if (user instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.atmospheric_api$grantAdvancement(TICKET_LAUNCHER_ACHIEVEMENT);
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!clickType.equals(ClickType.RIGHT)) {
            return false;
        }
        checkForReset(stack);
        int tickets = TerrificTicketsApi.getTickets(otherStack); // track the other stack's tickets
        if (tickets <= 0) {
            if (!otherStack.isEmpty()) {
                return false;
            }
            tickets = TerrificTicketsApi.getTickets(stack); // tickets now track this stack's tickets
            if (tickets <= 0) {
                return false;
            }
            tickets = Math.min(tickets, TerrificTickets.TICKET.getMaxCount());
            TerrificTicketsApi.removeTickets(stack, tickets);
            ItemStack aStackOfTickets = TerrificTickets.TICKET.getDefaultStack();
            aStackOfTickets.setCount(tickets);
            cursorStackReference.set(aStackOfTickets);
            return true;
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

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxCount() == 1;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }
}
