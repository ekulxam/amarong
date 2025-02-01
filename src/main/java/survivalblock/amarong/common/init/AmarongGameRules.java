package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class AmarongGameRules {

    public static final GameRules.Key<GameRules.BooleanRule> FLYING_TICKETS_DROP =
            GameRuleRegistry.register("amarong:flyingTicketsDrop", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<DoubleRule> BOOMERANG_DAMAGE =
            GameRuleRegistry.register("amarong:boomerangDamage", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(4.0F, 0.01F, Float.MAX_VALUE));
    public static final GameRules.Key<GameRules.BooleanRule> VERYLONGSWORD_PASSIVE_CHARGE =
            GameRuleRegistry.register("amarong:verylongswordPassiveCharge", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> OBSCURE_SPAWNS_PARTICLES =
            GameRuleRegistry.register("amarong:obscure_spawns_particles", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));

    public static void init() {

    }
}
