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

    /**
     * Resolves the current Ballistics display cap from the live GameRule,
     * falling back to the default (5) if no world is loaded or not on a client.
     */
    private static int bettercrossbows$getBallisticsCap() {
        int cap = 5; // GameRule default
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                if (mc != null && mc.level != null) {
                    cap = BetterCrossbowsGameRules.getBallisticsMaxLevel(mc.level);
                }
            } catch (Exception ignored) {
                // Graceful fallback
            }
        }
        return cap;
    }

    /**
     * Hooks the PARENT TAB single-book generation.
     * Vanilla shows one book at enchantment.getMaxLevel() — we clamp Ballistics to the GameRule cap.
     */
    @Inject(method = "generateEnchantmentBookTypesOnlyMaxLevel", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$capBallisticsMaxLevelBook(
            CreativeModeTab.Output output,
            HolderLookup<Enchantment> enchantments,
            CreativeModeTab.TabVisibility tabVisibility,
            CallbackInfo ci
    ) {
        final int cap = bettercrossbows$getBallisticsCap();
        enchantments.listElements().map(enchantment -> {
            int level = enchantment.is(BetterCrossbowsEnchantments.BALLISTICS_ID)
                    ? Math.min(enchantment.value().getMaxLevel(), cap)
                    : enchantment.value().getMaxLevel();
            return EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, level));
        }).forEach(stack -> output.accept(stack, tabVisibility));
        ci.cancel();
    }

    /**
     * Hooks the SEARCH TAB all-levels generation.
     * Vanilla generates books from minLevel to maxLevel — we stop at the GameRule cap for Ballistics.
     * Commands (/enchant, /give, etc.) are NOT affected — they bypass the creative tab entirely.
     */
    @Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$capBallisticsAllLevelBooks(
            CreativeModeTab.Output output,
            HolderLookup<Enchantment> enchantments,
            CreativeModeTab.TabVisibility tabVisibility,
            CallbackInfo ci
    ) {
        final int cap = bettercrossbows$getBallisticsCap();
        enchantments.listElements().flatMap(enchantment -> {
            int maxLevel = enchantment.is(BetterCrossbowsEnchantments.BALLISTICS_ID)
                    ? Math.min(enchantment.value().getMaxLevel(), cap)
                    : enchantment.value().getMaxLevel();
            return IntStream.rangeClosed(enchantment.value().getMinLevel(), maxLevel)
                    .mapToObj(level -> EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, level)));
        }).forEach(stack -> output.accept(stack, tabVisibility));
        ci.cancel();
    }
}
