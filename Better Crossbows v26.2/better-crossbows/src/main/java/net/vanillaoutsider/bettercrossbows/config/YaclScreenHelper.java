// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.bettercrossbows.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.dasik.social.api.config.DasikSupportHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        BetterCrossbowsConfig config = BetterCrossbowsConfig.get();

        var generalGroup = OptionGroup.createBuilder()
            .name(Component.translatable("config.bettercrossbows.category.general"));

        Option<?> supportButton = (Option<?>) DasikSupportHelper.createYaclButton();
        if (supportButton != null) {
            generalGroup.option(supportButton);
        }

        generalGroup
            // Ballistics Max Level
            .option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.bettercrossbows.crossbowBallisticsMaxLevel"))
                .description(OptionDescription.of(Component.translatable("config.bettercrossbows.crossbowBallisticsMaxLevel.description")))
                .binding(
                    5,
                    () -> config.crossbowBallisticsMaxLevel,
                    val -> config.crossbowBallisticsMaxLevel = val
                )
                .customController(opt -> new IntegerSliderController(opt, 0, 255, 1))
                .build())

            // Velocity Multiplier
            .option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.bettercrossbows.crossbowVelocityMultiplier"))
                .description(OptionDescription.of(Component.translatable("config.bettercrossbows.crossbowVelocityMultiplier.description")))
                .binding(
                    150,
                    () -> config.crossbowVelocityMultiplier,
                    val -> config.crossbowVelocityMultiplier = val
                )
                .customController(opt -> new IntegerSliderController(opt, 0, 1000, 5))
                .build())

            // Firework Multiplier
            .option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.bettercrossbows.crossbowFireworkMultiplier"))
                .description(OptionDescription.of(Component.translatable("config.bettercrossbows.crossbowFireworkMultiplier.description")))
                .binding(
                    100,
                    () -> config.crossbowFireworkMultiplier,
                    val -> config.crossbowFireworkMultiplier = val
                )
                .customController(opt -> new IntegerSliderController(opt, 0, 1000, 5))
                .build())

            // Reload Ticks
            .option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.bettercrossbows.crossbowReloadTicks"))
                .description(OptionDescription.of(Component.translatable("config.bettercrossbows.crossbowReloadTicks.description")))
                .binding(
                    25,
                    () -> config.crossbowReloadTicks,
                    val -> config.crossbowReloadTicks = val
                )
                .customController(opt -> new IntegerSliderController(opt, 1, 1200, 1))
                .build())

            // Enable Juice
            .option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.bettercrossbows.crossbowEnableJuice"))
                .description(OptionDescription.of(Component.translatable("config.bettercrossbows.crossbowEnableJuice.description")))
                .binding(
                    true,
                    () -> config.crossbowEnableJuice,
                    val -> config.crossbowEnableJuice = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.bettercrossbows.title"))
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.bettercrossbows.category.general"))
                .group(generalGroup.build())
                .build())
            .save(BetterCrossbowsConfig::save)
            .build()
            .generateScreen(parent);
    }
}
