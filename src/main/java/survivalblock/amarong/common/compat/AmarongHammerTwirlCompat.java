package survivalblock.amarong.common.compat;

import folk.sisby.twirl.Twirl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import survivalblock.amarong.common.AmarongUtil;
import survivalblock.amarong.common.init.AmarongTags;

import java.util.ArrayList;
import java.util.List;

public class AmarongHammerTwirlCompat {

    public static boolean hasTwirl(ItemStack stack) {
        return stack.atmospheric_api$getAbsoluteLevel(Twirl.TWIRLING) > 0;
    }

    public static void whack(PlayerEntity player) {
        World world = player.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }
        if (!player.isUsingItem()) {
            return;
        }
        ItemStack stack = player.getActiveItem();
        if (stack == null || stack.isEmpty()) {
            return;
        }
        if (!stack.isIn(AmarongTags.AmarongItemTags.TWIRL_DAMAGE)) {
            return;
        }
        int level = stack.atmospheric_api$getAbsoluteLevel(Twirl.TWIRLING);
        if (level <= 0) {
            return;
        }
        List<Entity> entities = new ArrayList<>();
        Box box = player.getBoundingBox().expand(2);
        serverWorld.atmospheric_api$getAndAddEntitiesToCollection(entity -> box.intersects(entity.getBoundingBox()), entities);
        //noinspection RedundantCollectionOperation
        if (entities.contains(player)) {
            entities.remove(player);
        }
        DamageSource source = serverWorld.getDamageSources().playerAttack(player);
        entities.forEach(entity -> {
            if (AmarongUtil.shouldDamageWithAmarong(entity)) {
                entity.damage(source, (entity instanceof PlayerEntity ? 0.5f : 0.25f) * (float) level);
            }
        });
    }
}
