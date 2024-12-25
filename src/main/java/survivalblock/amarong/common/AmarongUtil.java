package survivalblock.amarong.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import survivalblock.amarong.common.block.AmarongCoreBlockEntity;
import survivalblock.amarong.common.init.AmarongDataComponentTypes;
import survivalblock.amarong.common.init.AmarongTags;

import java.awt.*;

public class AmarongUtil {

    public static boolean increaseThrowablePotionVelocity = false;

    // this should probably be in AmarongClientUtil because colors but whatever, tick is server and client
    private static final Color TRANS_LIGHT_BLUE = new Color(91, 205, 250);
    private static final Color TRANS_PINK = new Color(245, 169, 184);
    public static final Color FULL_WHITE = new Color(255, 255, 255);

    public static int getCoreColor(World world, BlockPos blockPos, long age) {
        int mode;
        if (world.getBlockEntity(blockPos) instanceof AmarongCoreBlockEntity amarongCore) {
            long offset = amarongCore.getOffset();
            if (offset != -1) {
                age -= amarongCore.getOffset();
            }
            mode = amarongCore.getMode();
        } else {
            return FULL_WHITE.getRGB();
        }
        long n = age / 25;
        int o;
        int p;
        int q;
        float r;
        int s;
        int t;
        if (mode == 0) {
            o = 7;
            p = (int) (n % o);
            q = (int) ((n + 1) % o);
            r = ((float) (age % 25) + 1) / 25.0F;
            s = SheepEntity.getRgbColor(AmarongUtil.rainbow(p));
            t = SheepEntity.getRgbColor(AmarongUtil.rainbow(q));
        } else if (mode == 1) {
            // thanks, SheepWoolFeatureRenderer
            o = DyeColor.values().length;
            p = (int) (n % o);
            q = (int) ((n + 1) % o);
            r = ((float) (age % 25) + 1) / 25.0F;
            s = SheepEntity.getRgbColor(DyeColor.byId(p));
            t = SheepEntity.getRgbColor(DyeColor.byId(q));
        } else {
            o = 5;
            p = (int) (n % o);
            q = (int) ((n + 1) % o);
            r = ((float) (age % 25) + 1) / 25.0F;
            s = AmarongUtil.trans(p);
            t = AmarongUtil.trans(q);
        }
        return ColorHelper.Argb.lerp(r, s, t);
    }

    public static DyeColor rainbow(int ordinal) {
        if (ordinal == 0) {
            return DyeColor.RED;
        }
        if (ordinal == 1) {
            return DyeColor.ORANGE;
        }
        if (ordinal == 2) {
            return DyeColor.YELLOW;
        }
        if (ordinal == 3) {
            return DyeColor.LIME;
        }
        if (ordinal == 4) {
            return DyeColor.LIGHT_BLUE;
        }
        if (ordinal == 5) {
            return DyeColor.BLUE;
        }
        if (ordinal == 6) {
            return DyeColor.PURPLE;
        }
        return DyeColor.BLACK;
    }

    public static int trans(int ordinal) {
        if (ordinal < 0 || ordinal > 4) {
            ordinal = 2;
        }
        if (ordinal == 0) {
            return TRANS_LIGHT_BLUE.getRGB();
        }
        if (ordinal == 1) {
            return TRANS_PINK.getRGB();
        }
        if (ordinal == 2) {
            return FULL_WHITE.getRGB();
        }
        if (ordinal == 3) {
            return TRANS_PINK.getRGB();
        }
        return TRANS_LIGHT_BLUE.getRGB();
    }

    public static Box getProperlyScaledBox(Entity entity) {
        return entity.getBoundingBox(); // so apparently mojang did do it correctly
    }

    public static boolean shouldDamageWithAmarong(Entity entity) {
        if (entity instanceof ArmorStandEntity armorStandEntity && armorStandEntity.isMarker()) {
            return false;
        }
        return entity instanceof LivingEntity || entity.getType().isIn(AmarongTags.AmarongEntityTypeTags.AMARONG_HITTABLE);
    }

    public static boolean shouldRetainCharge(ItemStack stack) {
        return stack.getOrDefault(AmarongDataComponentTypes.RETAINS_CHARGE, false);
    }
}