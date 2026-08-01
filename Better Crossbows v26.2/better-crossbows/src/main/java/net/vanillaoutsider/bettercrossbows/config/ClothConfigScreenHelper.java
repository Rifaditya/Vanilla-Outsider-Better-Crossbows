package net.vanillaoutsider.bettercrossbows.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenHelper {
    
    public static ConfigScreenFactory<?> createFactory() {
        return ClothConfigScreenHelper::createScreen;
    }

    public static Screen createScreen(Screen parent) {
        BetterCrossbowsConfig config = BetterCrossbowsConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("config.bettercrossbows.title"));

        builder.setSavingRunnable(BetterCrossbowsConfig::save);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.bettercrossbows.category.general"));

        // Mandatory baseline GameRules warning description at the top of the screen
        general.addEntry(entryBuilder.startTextDescription(Component.translatable("config.bettercrossbows.warning")).build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.bettercrossbows.crossbowBallisticsMaxLevel"), config.crossbowBallisticsMaxLevel)
            .setDefaultValue(5)
            .setTooltip(Component.translatable("config.bettercrossbows.crossbowBallisticsMaxLevel.description"))
            .setSaveConsumer(val -> config.crossbowBallisticsMaxLevel = val)
            .setMin(0).setMax(255)
            .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.bettercrossbows.crossbowVelocityMultiplier"), config.crossbowVelocityMultiplier)
            .setDefaultValue(150)
            .setTooltip(Component.translatable("config.bettercrossbows.crossbowVelocityMultiplier.description"))
            .setSaveConsumer(val -> config.crossbowVelocityMultiplier = val)
            .setMin(0).setMax(1000)
            .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.bettercrossbows.crossbowFireworkMultiplier"), config.crossbowFireworkMultiplier)
            .setDefaultValue(100)
            .setTooltip(Component.translatable("config.bettercrossbows.crossbowFireworkMultiplier.description"))
            .setSaveConsumer(val -> config.crossbowFireworkMultiplier = val)
            .setMin(0).setMax(1000)
            .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.bettercrossbows.crossbowReloadTicks"), config.crossbowReloadTicks)
            .setDefaultValue(25)
            .setTooltip(Component.translatable("config.bettercrossbows.crossbowReloadTicks.description"))
            .setSaveConsumer(val -> config.crossbowReloadTicks = val)
            .setMin(1).setMax(1200)
            .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.bettercrossbows.crossbowEnableJuice"), config.crossbowEnableJuice)
            .setDefaultValue(true)
            .setTooltip(Component.translatable("config.bettercrossbows.crossbowEnableJuice.description"))
            .setSaveConsumer(val -> config.crossbowEnableJuice = val)
            .build());

        return builder.build();
    }
}
