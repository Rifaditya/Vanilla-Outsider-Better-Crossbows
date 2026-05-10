package net.vanillaoutsider.bettercrossbows.mixin;

// Verified against: CrossbowItem.java (26.1.2)
// performShooting signature: (Level, LivingEntity, InteractionHand, ItemStack, float power, float uncertainty, LivingEntity targetOverride)
// NOTE: List<ItemStack> projectiles parameter was REMOVED in 26.1.2. Do not use it.

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jspecify.annotations.Nullable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$modifyChargeDuration(ItemStack crossbow, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        int ticks = BetterCrossbowsGameRules.getReloadTicks(user.level());
        cir.setReturnValue(ticks);
    }

    // @ModifyVariable rule: first param = captured var, remaining = ALL method params as context.
    // performShooting has (Level, LivingEntity, InteractionHand, ItemStack, float power, float uncertainty, LivingEntity)
    // Handler must list: float captured, Level, LivingEntity, InteractionHand, ItemStack, float power, float uncertainty, LivingEntity
    @ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float bettercrossbows$modifyPower(float velocity, Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float power, float uncertainty, @Nullable LivingEntity targetOverride) {
        float baseMultiplier = BetterCrossbowsGameRules.getVelocityMultiplier(level);

        int ballisticsLevel = 0;
        ItemEnchantments enchantments = weapon.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                ballisticsLevel = entry.getIntValue();
                break;
            }
        }

        return velocity * baseMultiplier * (1.0f + 0.25f * ballisticsLevel);
    }

    @Inject(method = "performShooting", at = @At("HEAD"))
    private void bettercrossbows$juiceEffect(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float uncertainty, @Nullable LivingEntity targetOverride, CallbackInfo ci) {
        if (!BetterCrossbowsGameRules.isJuiceEnabled(level)) return;

        float baseMultiplier = BetterCrossbowsGameRules.getVelocityMultiplier(level);
        int ballisticsLevel = 0;
        ItemEnchantments enchantments = weapon.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                ballisticsLevel = entry.getIntValue();
                break;
            }
        }

        float finalPower = velocity * baseMultiplier * (1.0f + 0.25f * ballisticsLevel);

        net.dasik.social.api.projectile.ProjectileEffectHelper.playSonicJuice(level, shooter, finalPower, velocity, 1.2f);
    }
}
