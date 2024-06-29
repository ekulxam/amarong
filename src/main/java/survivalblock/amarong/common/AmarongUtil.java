package survivalblock.amarong.common;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.LazyIterationConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.block.AmarongCoreBlockEntity;
import survivalblock.amarong.mixin.verylongsword.ServerWorldAccessor;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class AmarongUtil {

    public static boolean increaseThrowablePotionVelocity = false;

    // this should probably be in AmarongClientUtil because colors but whatever, tick is server and client
    private static final Color TRANS_LIGHT_BLUE = new Color(91, 205, 250);
    private static final Color TRANS_PINK = new Color(245, 169, 184);
    public static final Color FULL_WHITE = new Color(255, 255, 255);

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

    public static void grantAdvancment(ServerPlayerEntity serverPlayer, Identifier advancementId) {
        MinecraftServer server = serverPlayer.getServer();
        if (server == null) return;
        ServerAdvancementLoader loader = serverPlayer.getServer().getAdvancementLoader();
        AdvancementEntry advancement = loader.get(advancementId);
        AdvancementProgress advancementProgress = serverPlayer.getAdvancementTracker().getProgress(advancement);
        if (advancementProgress.isDone()) {
            return;
        }
        for (String string : advancementProgress.getUnobtainedCriteria()) {
            serverPlayer.getAdvancementTracker().grantCriterion(advancement, string);
        }
    }

    public static int getLevel(ItemStack stack, TagKey<Enchantment> tag) {
        int level = 0;
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> enchantments = itemEnchantmentsComponent.getEnchantmentEntries();
        RegistryEntry<Enchantment> registryEntry;
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : enchantments) {
            registryEntry = entry.getKey();
            if (registryEntry.isIn(tag)) {
                level += Math.abs(EnchantmentHelper.getLevel(registryEntry, stack));
            }
        }
        return level;
    }

    public static Box getProperlyScaledBox(Entity entity) {
        Box entityBox = entity.getBoundingBox();
        if (entity instanceof LivingEntity living) {
            Vec3d boxCenter = entityBox.getCenter();
            float scale = living.getScale();
            double boxNewHeight = (entityBox.getLengthY() * scale) / 2;
            double boxOldHeight = entityBox.getLengthY() / 2;
            double yDiff;
            if (boxNewHeight > boxOldHeight) {
                yDiff = boxNewHeight - boxOldHeight;
            } else {
                yDiff = boxOldHeight - boxNewHeight;
            }
            entityBox = Box.of(boxCenter, entityBox.getLengthX() * scale, entityBox.getLengthY() * scale, entityBox.getLengthZ() * scale);
            entityBox.expand(0, yDiff, 0);
        }
        return entityBox;
    }

    public static void getEntitiesWithScaling(ServerWorld serverWorld, Predicate<Entity> predicate, List<Entity> entities) {
        AtomicInteger entityCount = new AtomicInteger(0);
        ((ServerWorldAccessor) serverWorld).amarong$invokeGetEntityLookup().forEach(TypeFilter.instanceOf(Entity.class), (entity) -> {
            if (entityCount.get() >= Integer.MAX_VALUE) {
                return LazyIterationConsumer.NextIteration.ABORT;
            }
            if (entity == null || !entity.isAlive()) {
                return LazyIterationConsumer.NextIteration.CONTINUE;
            }
            if (predicate.test(entity)) {
                entityCount.incrementAndGet();
                entities.add(entity);
            }
            return LazyIterationConsumer.NextIteration.CONTINUE;
        });
    }
}
