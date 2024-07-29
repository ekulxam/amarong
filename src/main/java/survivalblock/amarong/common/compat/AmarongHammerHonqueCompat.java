package survivalblock.amarong.common.compat;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import survivalblock.amarong.mixin.compat.honque.TheFunnyAccessor;
import symbolics.division.honque.HonqueTraquer;
import symbolics.division.honque.TheFunny;
import symbolics.division.honque.magic.Honk;

public class AmarongHammerHonqueCompat {

    public static void whack(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }
        ServerWorld serverWorld = serverPlayer.getServerWorld();
        HonqueTraquer.inc(serverWorld);
        ItemStack stack = getFunny(player);
        if (stack == null || stack.isEmpty()) {
            return;
        }
        Item item = stack.getItem();
        if (!(item instanceof TheFunny theFunny)) {
            return;
        }
        final float prob = serverWorld.getRandom().nextFloat();
        final float instantDeathChance = ((TheFunnyAccessor) theFunny).amarong$getInstantDeathChance();
        final Honk whatIDo = theFunny.beep();
        Text text;
        if (prob <= instantDeathChance / 200) {
            whatIDo.trulyUnfortunateCircumstance(serverPlayer, serverPlayer, stack, item);
            text = Text.translatable("message.amarong.compat.honque.trulyUnfortunateCircumstance");
        } else if (prob <= instantDeathChance / 5) {
            whatIDo.veryBadLuck(serverPlayer, serverPlayer, stack, item);
            text = Text.translatable("message.amarong.compat.honque.veryBadLuck");
        } else if (prob <= instantDeathChance) {
            whatIDo.badLuck(serverPlayer, serverPlayer, stack, item);
            text = Text.translatable("message.amarong.compat.honque.badLuck");
        } else {
            whatIDo.honk(serverPlayer, serverPlayer, stack, item);
            text = Text.translatable("message.amarong.compat.honque.honk");
        }
        serverPlayer.sendMessage(text, true);
    }

    @Nullable
    public static ItemStack getFunny(PlayerEntity player) {
        ItemStack stack = getFunny(player, EquipmentSlot.OFFHAND);
        if (stack == null || stack.isEmpty()) {
            stack = getFunny(player, EquipmentSlot.HEAD);
        }
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack;
    }

    @Nullable
    public static ItemStack getFunny(PlayerEntity player, EquipmentSlot equipmentSlot) {
        ItemStack stack = player.getEquippedStack(equipmentSlot);
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        Item item = stack.getItem();
        if (item instanceof TheFunny) {
            return stack;
        }
        return null;
    }
}
