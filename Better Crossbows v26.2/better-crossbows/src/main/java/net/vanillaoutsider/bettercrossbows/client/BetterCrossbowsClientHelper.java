// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.bettercrossbows.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;

@Environment(EnvType.CLIENT)
public class BetterCrossbowsClientHelper {

    public static int getBallisticsCapFromSingleplayer() {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc != null) {
                MinecraftServer server = mc.getSingleplayerServer();
                if (server != null && server.overworld() != null) {
                    return BetterCrossbowsGameRules.getBallisticsMaxLevel(server.overworld());
                }
            }
        } catch (Exception ignored) {}
        return -1;
    }
}
