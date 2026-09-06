// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.bettercrossbows.config;

import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterCrossbowsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("BetterCrossbows Config");
    private static BetterCrossbowsConfig INSTANCE = new BetterCrossbowsConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public int crossbowBallisticsMaxLevel = 5;
    public int crossbowVelocityMultiplier = 150;
    public int crossbowFireworkMultiplier = 100;
    public int crossbowReloadTicks = 25;
    public boolean crossbowEnableJuice = true;

    public void validate() {
        // Full integer space unlocked per Player Agency & Anti-Nanny Invariant
    }

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("bettercrossbows.json");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.load(
            CONFIG_PATH, INSTANCE, BetterCrossbowsConfig.class, VERSION,
            config -> config.configVersion, (config, ver) -> config.configVersion = ver,
            "/bettercrossbows.json", LOGGER
        );
        INSTANCE.validate();
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        net.dasik.social.api.config.ConfigHelper.save(CONFIG_PATH, INSTANCE, LOGGER);
    }

    public static BetterCrossbowsConfig get() {
        return INSTANCE;
    }
}
