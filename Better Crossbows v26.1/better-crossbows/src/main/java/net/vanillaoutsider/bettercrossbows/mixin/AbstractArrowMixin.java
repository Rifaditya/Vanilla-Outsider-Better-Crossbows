// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.bettercrossbows.mixin;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Inject(method = "getDefaultGravity", at = @At("RETURN"), cancellable = true)
    private void bettercrossbows$adjustCrossbowArrowGravity(CallbackInfoReturnable<Double> cir) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        ItemStack weapon = arrow.getWeaponItem();
        if (weapon != null && weapon.is(net.minecraft.world.item.Items.CROSSBOW)) {
            // Retrieve ballistics level from weapon components
            int ballisticsLevel = 0;
            net.minecraft.world.item.enchantment.ItemEnchantments enchantments = weapon.getOrDefault(
                net.minecraft.core.component.DataComponents.ENCHANTMENTS, 
                net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY
            );
            for (var entry : enchantments.entrySet()) {
                if (entry.getKey().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                    ballisticsLevel = entry.getIntValue();
                    break;
                }
            }

            // Scale gravity down based on velocity boost
            double baseMultiplier = BetterCrossbowsGameRules.getVelocityMultiplier(arrow.level());
            double speedMultiplier = baseMultiplier * (1.0f + 0.25f * ballisticsLevel);

            if (speedMultiplier > 0.0) {
                double vanillaGravity = cir.getReturnValue();
                cir.setReturnValue(vanillaGravity / speedMultiplier);
            }
        }
    }
}
