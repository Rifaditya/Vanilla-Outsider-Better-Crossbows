// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.bettercrossbows;

import net.vanillaoutsider.bettercrossbows.config.BetterCrossbowsConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BetterCrossbowsConfigTest {

    @Test
    public void testFullIntegerRangePreservedInValidate() {
        BetterCrossbowsConfig config = new BetterCrossbowsConfig();

        // Negative values
        config.crossbowBallisticsMaxLevel = -500;
        config.crossbowVelocityMultiplier = -500;
        config.crossbowFireworkMultiplier = -500;
        config.crossbowReloadTicks = -500;

        config.validate();

        assertEquals(-500, config.crossbowBallisticsMaxLevel, "Negative ballistics level should be preserved");
        assertEquals(-500, config.crossbowVelocityMultiplier, "Negative velocity multiplier should be preserved");
        assertEquals(-500, config.crossbowFireworkMultiplier, "Negative firework multiplier should be preserved");
        assertEquals(-500, config.crossbowReloadTicks, "Negative reload ticks should be preserved");

        // Extreme values
        config.crossbowBallisticsMaxLevel = 50000;
        config.crossbowVelocityMultiplier = 50000;
        config.crossbowFireworkMultiplier = 50000;
        config.crossbowReloadTicks = 50000;

        config.validate();

        assertEquals(50000, config.crossbowBallisticsMaxLevel, "Extreme ballistics level should be preserved");
        assertEquals(50000, config.crossbowVelocityMultiplier, "Extreme velocity multiplier should be preserved");
        assertEquals(50000, config.crossbowFireworkMultiplier, "Extreme firework multiplier should be preserved");
        assertEquals(50000, config.crossbowReloadTicks, "Extreme reload ticks should be preserved");
    }
}
