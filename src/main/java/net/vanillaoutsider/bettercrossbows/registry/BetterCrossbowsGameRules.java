package net.vanillaoutsider.bettercrossbows.registry;

// Verified against: GameRules.java (Snapshot 10 / 26.1.2)

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class BetterCrossbowsGameRules {

    public static final GameRuleCategory CATEGORY = DynamicGameRuleManager.registerCategory(
            Identifier.fromNamespaceAndPath("bettercrossbows", "better_crossbows")
    );

    public static GameRule<Integer> CROSSBOW_BALLISTICS_MAX_LEVEL;
    public static GameRule<Integer> CROSSBOW_VELOCITY_MULTIPLIER;
    public static GameRule<Integer> CROSSBOW_RELOAD_TICKS;
    public static GameRule<Boolean> CROSSBOW_ENABLE_JUICE;

    public static void register() {
        CROSSBOW_BALLISTICS_MAX_LEVEL = DynamicGameRuleManager.integerRule("bettercrossbows:crossbow_ballistics_max_level", CATEGORY, 5)
                .name("Crossbow Ballistics Max Level")
                .description("Maximum obtainable level for the Ballistics enchantment. Default: 5")
                .register();

        CROSSBOW_VELOCITY_MULTIPLIER = DynamicGameRuleManager.integerRule("bettercrossbows:crossbow_velocity_multiplier", CATEGORY, 150)
                .name("Crossbow Velocity Multiplier")
                .description("Multiplier applied to the base power of arrows (in percent). Default: 150 (1.5x)")
                .register();

        CROSSBOW_RELOAD_TICKS = DynamicGameRuleManager.integerRule("bettercrossbows:crossbow_reload_ticks", CATEGORY, 25)
                .name("Crossbow Reload Ticks")
                .description("Base duration in ticks to charge a crossbow. Default: 25")
                .register();

        CROSSBOW_ENABLE_JUICE = DynamicGameRuleManager.booleanRule("bettercrossbows:crossbow_enable_juice", CATEGORY, true)
                .name("Crossbow Enable Juice")
                .description("When true, high-velocity shots produce particles and sonic crack sounds. Default: true")
                .register();
    }

    public static float getVelocityMultiplier(Level level) {
        return DynamicGameRuleManager.getInt(level, CROSSBOW_VELOCITY_MULTIPLIER) / 100.0f;
    }

    public static int getBallisticsMaxLevel(Level level) {
        return DynamicGameRuleManager.getInt(level, CROSSBOW_BALLISTICS_MAX_LEVEL);
    }

    public static int getReloadTicks(Level level) {
        return DynamicGameRuleManager.getInt(level, CROSSBOW_RELOAD_TICKS);
    }

    public static boolean isJuiceEnabled(Level level) {
        return DynamicGameRuleManager.getBoolean(level, CROSSBOW_ENABLE_JUICE);
    }
}
