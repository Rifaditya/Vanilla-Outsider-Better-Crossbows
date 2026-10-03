// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
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
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jspecify.annotations.Nullable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "getChargeDuration", at = @At("RETURN"), cancellable = true)
    private static void bettercrossbows$modifyChargeDuration(ItemStack crossbow, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        int vanillaTicks = cir.getReturnValue();
        int baseTicks = BetterCrossbowsGameRules.getReloadTicks(user.level());
        int quickChargeReduction = 25 - vanillaTicks; // Vanilla base is 25
        cir.setReturnValue(Math.max(1, baseTicks - quickChargeReduction));
    }

    private static float bettercrossbows$getShotMultiplier(Level level, ItemStack weapon) {
        net.minecraft.world.item.component.ChargedProjectiles charged = weapon.get(net.minecraft.core.component.DataComponents.CHARGED_PROJECTILES);
        boolean hasFirework = charged != null && charged.contains(net.minecraft.world.item.Items.FIREWORK_ROCKET);

        float baseMultiplier = hasFirework 
            ? BetterCrossbowsGameRules.getFireworkMultiplier(level)
            : BetterCrossbowsGameRules.getVelocityMultiplier(level);

        int ballisticsLevel = 0;
        ItemEnchantments enchantments = weapon.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                ballisticsLevel = entry.getIntValue();
                break;
            }
        }

        return baseMultiplier * (1.0f + 0.25f * ballisticsLevel);
    }

    @WrapOperation(
        method = "performShooting",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;shootProjectile(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;FZFFZLnet/minecraft/world/entity/LivingEntity;)V")
    )
    private void bettercrossbows$wrapShootProjectile(CrossbowItem instance, Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, ItemStack projectile, float soundPitch, boolean isCreative, float power, float uncertainty, float soundAngle, @Nullable LivingEntity targetOverride, Operation<Void> original) {
        float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
        original.call(instance, level, shooter, hand, weapon, projectile, soundPitch, isCreative, power * multiplier, uncertainty, soundAngle, targetOverride);
    }

    @Inject(method = "performShooting", at = @At("HEAD"))
    private void bettercrossbows$juiceEffect(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float uncertainty, @Nullable LivingEntity targetOverride, CallbackInfo ci) {
        if (!BetterCrossbowsGameRules.isJuiceEnabled(level)) return;

        float multiplier = bettercrossbows$getShotMultiplier(level, weapon);
        float finalPower = velocity * multiplier;
        float powerRatio = finalPower / velocity;

        if (powerRatio > 1.2f) {
            // Dynamic sonic boom crack (lower pitch = heavier sonic boom sound)
            float pitch = Math.max(0.2f, 0.8f - (powerRatio - 1.2f) * 0.15f);
            int cloudCount = (int) (5 * powerRatio);
            double speed = 0.05 * powerRatio;

            net.minecraft.world.entity.player.Player player = shooter instanceof net.minecraft.world.entity.player.Player p ? p : null;
            level.playSound(player, shooter.getX(), shooter.getY(), shooter.getZ(), net.minecraft.sounds.SoundEvents.FIREWORK_ROCKET_BLAST_FAR, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, pitch);

            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                    shooter.getX(), shooter.getEyeY() - 0.15, shooter.getZ(), 
                    cloudCount, 0.1, 0.1, 0.1, speed);

                // Extremely high speed gust trail
                if (powerRatio > 2.0f) {
                    serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.GUST, 
                        shooter.getX(), shooter.getEyeY() - 0.15, shooter.getZ(), 
                        2, 0.15, 0.15, 0.15, 0.0);
                }
            }
        }
    }
}
