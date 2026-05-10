package net.vanillaoutsider.bettercrossbows.mixin;

// Verified against: CreativeModeTabs.java (26.1.2)
// generateEnchantmentBookTypesAllLevels: uses enchantment.value().getMaxLevel() — no GameRule awareness.
// We cancel and re-implement it, capping Ballistics to the configured GameRule value.

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
     * Intercepts the all-levels enchanted book generation to cap Ballistics at the GameRule value.
     * Commands (/enchant, /give, etc.) are NOT affected — they bypass the creative tab entirely.
     * The GameRule default (5) is used as fallback when no world is loaded (e.g., main menu).
     */
    @Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$capBallisticsInCreativeTab(
            CreativeModeTab.Output output,
            HolderLookup<Enchantment> enchantments,
            CreativeModeTab.TabVisibility tabVisibility,
            CallbackInfo ci
    ) {
        // Resolve the Ballistics cap from the live GameRule if we're on a client with a loaded world.
        // On a dedicated server (no Minecraft client class), fall back to the GameRule default.
        int ballisticsCap = 5; // GameRule default (BetterCrossbowsGameRules.CROSSBOW_BALLISTICS_MAX_LEVEL)
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                if (mc != null && mc.level != null) {
                    ballisticsCap = BetterCrossbowsGameRules.getBallisticsMaxLevel(mc.level);
                }
            } catch (Exception ignored) {
                // Graceful fallback — creative tab still shows 1–5 if anything goes wrong
            }
        }

        final int finalBallisticsCap = ballisticsCap;

        // Rebuild the book list exactly as vanilla does, but clamp Ballistics to our cap.
        enchantments.listElements().flatMap(enchantment -> {
            int maxLevel = enchantment.is(BetterCrossbowsEnchantments.BALLISTICS_ID)
                    ? Math.min(enchantment.value().getMaxLevel(), finalBallisticsCap)
                    : enchantment.value().getMaxLevel();
            return IntStream.rangeClosed(enchantment.value().getMinLevel(), maxLevel)
                    .mapToObj(level -> EnchantmentHelper.createBook(new EnchantmentInstance(enchantment, level)));
        }).forEach(stack -> output.accept(stack, tabVisibility));

        ci.cancel();
    }
}
