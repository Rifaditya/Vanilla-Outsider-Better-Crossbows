package net.vanillaoutsider.bettercrossbows.mixin;

// Verified against: CreativeModeTabs.java (26.1.2)
// Two methods fill enchanted books in the Ingredients creative tab:
//   1. generateEnchantmentBookTypesOnlyMaxLevel → one book at max level (shown in parent tab)
//   2. generateEnchantmentBookTypesAllLevels    → all levels 1–max (shown in search tab)
// Both use enchantment.value().getMaxLevel() directly — no GameRule awareness.
// We cancel + re-implement both, capping Ballistics to the configured GameRule value.

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.api.EnvType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.core.HolderLookup;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.IntStream;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {

    @org.spongepowered.asm.mixin.Shadow
    private static net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters CACHED_PARAMETERS;

    private static int bettercrossbows$lastKnownCap = -2;

    /**
     * Intercepts creative tab rebuild checks. If the GameRule has changed since last check,
     * forces a rebuild by clearing the CACHED_PARAMETERS.
     */
    @Inject(method = "tryRebuildTabContents", at = @At("HEAD"))
    private static void bettercrossbows$forceRebuildOnGameRuleChange(net.minecraft.world.flag.FeatureFlagSet enabledFeatures, boolean hasPermissions, HolderLookup.Provider lookup, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> cir) {
        int currentCap = bettercrossbows$getBallisticsCap();
        if (bettercrossbows$lastKnownCap != -2 && currentCap != bettercrossbows$lastKnownCap) {
            CACHED_PARAMETERS = null;
        }
        bettercrossbows$lastKnownCap = currentCap;
    }


    /**
     * Resolves the current Ballistics display cap from the live GameRule,
     * falling back to the default (5) if no world is loaded or not on a client.
     */
    private static int bettercrossbows$getBallisticsCap() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                if (mc != null && mc.getSingleplayerServer() != null) {
                    return BetterCrossbowsGameRules.getBallisticsMaxLevel(mc.getSingleplayerServer().overworld());
                }
            } catch (Exception ignored) {}
        }
        return -1; // -1 means unknown (multiplayer client), fallback to default data max level
    }

    /**
     * Hooks the PARENT TAB single-book generation.
     */
    @Inject(method = "generateEnchantmentBookTypesOnlyMaxLevel", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$capBallisticsMaxLevelBook(
            CreativeModeTab.Output output,
            HolderLookup<Enchantment> enchantments,
            CreativeModeTab.TabVisibility tabVisibility,
            CallbackInfo ci
    ) {
        final int cap = bettercrossbows$getBallisticsCap();
        enchantments.listElements().forEach(enchantment -> {
            if (enchantment.is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                int level = cap >= 0 ? Math.min(enchantment.value().getMaxLevel(), cap) : enchantment.value().getMaxLevel();
                if (level > 0) {
                    output.accept(EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, level)), tabVisibility);
                }
            } else {
                output.accept(EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, enchantment.value().getMaxLevel())), tabVisibility);
            }
        });
        ci.cancel();
    }

    /**
     * Hooks the SEARCH TAB all-levels generation.
     */
    @Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$capBallisticsAllLevelBooks(
            CreativeModeTab.Output output,
            HolderLookup<Enchantment> enchantments,
            CreativeModeTab.TabVisibility tabVisibility,
            CallbackInfo ci
    ) {
        final int cap = bettercrossbows$getBallisticsCap();
        enchantments.listElements().forEach(enchantment -> {
            int maxLevel = enchantment.is(BetterCrossbowsEnchantments.BALLISTICS_ID)
                    ? (cap >= 0 ? Math.min(enchantment.value().getMaxLevel(), cap) : enchantment.value().getMaxLevel())
                    : enchantment.value().getMaxLevel();
            
            int minLevel = enchantment.value().getMinLevel();
            for (int i = minLevel; i <= maxLevel; i++) {
                if (i > 0) {
                    output.accept(EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, i)), tabVisibility);
                }
            }
        });
        ci.cancel();
    }
}
