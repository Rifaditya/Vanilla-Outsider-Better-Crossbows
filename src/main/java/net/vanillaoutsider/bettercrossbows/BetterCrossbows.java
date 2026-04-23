package net.vanillaoutsider.bettercrossbows;

import net.fabricmc.api.ModInitializer;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterCrossbows implements ModInitializer {
    public static final String MOD_ID = "bettercrossbows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Vanilla Outsider: Better Crossbows Initializing...");
        BetterCrossbowsGameRules.register();
        BetterCrossbowsEnchantments.register();
    }
}