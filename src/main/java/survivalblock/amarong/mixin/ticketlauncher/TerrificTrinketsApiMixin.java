package survivalblock.amarong.mixin.ticketlauncher;

import gay.lemmaeof.terrifictickets.api.TerrificTicketsApi;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongItems;
import survivalblock.amarong.common.item.TicketLauncherItem;

@Mixin(TerrificTicketsApi.class)
public class TerrificTrinketsApiMixin {

	@Inject(method = "getTickets", at = @At("HEAD"), cancellable = true)
	private static void getDispenserTickets(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (stack.isOf(AmarongItems.TICKET_LAUNCHER)) {
			cir.setReturnValue(TicketLauncherItem.checkForReset(stack));
		}
	}

	@Inject(method = "removeTickets", at = @At("HEAD"), cancellable = true)
	private static void removeDispenserTicket(ItemStack stack, int amount, CallbackInfoReturnable<Integer> cir) {
		if (stack.isOf(AmarongItems.TICKET_LAUNCHER)) {
			int tickets = TicketLauncherItem.checkForReset(stack);
			int toRemove = Math.min(tickets, amount);
			stack.set(AmarongDataComponentTypes.TICKETS, tickets - toRemove);
			cir.setReturnValue(tickets - toRemove);
		}
	}
}