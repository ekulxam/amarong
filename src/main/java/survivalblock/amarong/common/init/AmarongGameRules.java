package survivalblock.amarong.common.init;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class AmarongGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> FLYING_TICKETS_DROP =
            GameRuleRegistry.register("amarong:flyingTicketsDrop", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    public static void init() {

    }
}
