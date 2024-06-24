package survivalblock.amarong.common;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import survivalblock.amarong.common.block.AmarongCoreBlockEntity;

public class AmarongUtil {

    public static int getCoreColor(World world, BlockPos blockPos) {
        long age = world.getTime();
        int mode = 0;
        if (world.getBlockEntity(blockPos) instanceof AmarongCoreBlockEntity amarongCore) {
            long offset = amarongCore.getOffset();
            if (offset != -1) age -= amarongCore.getOffset();
            mode = amarongCore.getMode();
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
            r = ((float)(age % 25) + 1) / 25.0F;
            s = SheepEntity.getRgbColor(AmarongUtil.rainbow(p));
            t = SheepEntity.getRgbColor(AmarongUtil.rainbow(q));
        } else if (mode == 1) {
            // thanks, SheepWoolFeatureRenderer
            o = DyeColor.values().length;
            p = (int) (n % o);
            q = (int) ((n + 1) % o);
            r = ((float)(age % 25) + 1) / 25.0F;
            s = SheepEntity.getRgbColor(DyeColor.byId(p));
            t = SheepEntity.getRgbColor(DyeColor.byId(q));
        } else {
            o = 5;
            p = (int) (n % o);
            q = (int) ((n + 1) % o);
            r = ((float)(age % 25) + 1) / 25.0F;
            s = SheepEntity.getRgbColor(AmarongUtil.trans(p));
            t = SheepEntity.getRgbColor(AmarongUtil.trans(q));
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
            return DyeColor.GREEN;
        }
        if (ordinal == 4) {
            return DyeColor.BLUE;
        }
        if (ordinal == 5) {
            return DyeColor.PURPLE;
        }
        if (ordinal == 6) {
            return DyeColor.MAGENTA;
        }
        return DyeColor.BLACK;
    }

    public static DyeColor trans(int ordinal) {
        if (ordinal == 0) {
            return DyeColor.LIGHT_BLUE;
        }
        if (ordinal == 1) {
            return DyeColor.PINK;
        }
        if (ordinal == 2) {
            return DyeColor.WHITE;
        }
        if (ordinal == 3) {
            return DyeColor.PINK;
        }
        if (ordinal == 4) {
            return DyeColor.LIGHT_BLUE;
        }
        return DyeColor.BLACK;
    }
}
