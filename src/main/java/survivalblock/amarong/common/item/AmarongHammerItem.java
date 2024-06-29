package survivalblock.amarong.common.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.amarong.common.Amarong;
import survivalblock.amarong.common.compat.AmarongHammerHonqueCompat;
import survivalblock.amarong.common.compat.AmarongHammerTwirlCompat;
import survivalblock.amarong.common.init.AmarongTags;

import java.util.List;

public class AmarongHammerItem extends PickaxeItem {

    public AmarongHammerItem(ToolMaterial material, AmarongToolMaterial.Configuration settings) {
        super(material, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return Items.MACE.postHit(stack, target, attacker);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Items.MACE.postDamageEntity(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.amarong.amarong_hammer.alias").formatted(Formatting.AQUA));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean wasASuccess = false;
        if (Amarong.honque) {
            AmarongHammerHonqueCompat.whack(user);
            wasASuccess = true;
        }
        if (Amarong.twirl) {
            if (AmarongHammerTwirlCompat.hasTwirl(stack)) {
                user.setCurrentHand(hand);
                return TypedActionResult.success(stack);
            }
        }
        if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, AmarongTags.AmarongEnchantmentTags.VAULT_EFFECT)) {
            Vec3d current = user.getVelocity();
            user.setVelocity(current.getX(), Math.max(0, current.getY()) + 2.5, current.getZ());
            if (!world.isClient()) {
                user.getItemCooldownManager().set(this, 200);
            }
            return TypedActionResult.success(stack, world.isClient());
        }
        if (wasASuccess) {
            return TypedActionResult.success(stack, world.isClient());
        }
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (Amarong.twirl && user instanceof ServerPlayerEntity serverPlayer) {
            AmarongHammerTwirlCompat.whack(serverPlayer);
        }
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        return Items.MACE.getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }
}
