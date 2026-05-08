package net.vanillaoutsider.bettercrossbows.mixin;

// Verified against: CrossbowItem.java (26.1.2 Release)

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void bettercrossbows$modifyChargeDuration(ItemStack crossbow, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        int ticks = BetterCrossbowsGameRules.getReloadTicks(user.level());
        cir.setReturnValue(ticks);
    }

    @ModifyVariable(method = "performShooting", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float bettercrossbows$modifyPower(float velocity, Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, java.util.List<ItemStack> projectiles, float uncertainty, boolean isCrit, LivingEntity targetOverride) {
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
    private void bettercrossbows$juiceEffect(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, java.util.List<ItemStack> projectiles, float velocity, float uncertainty, boolean isCrit, LivingEntity targetOverride, CallbackInfo ci) {
        if (!BetterCrossbowsGameRules.isJuiceEnabled(level)) return;

        // Recalculate to see if we breach the threshold (since power here is pre-modification)
        // Wait, @ModifyVariable runs before @Inject if we don't specify carefully, but we can just do the check by calculating again, or modifying power inline.
        // Actually, let's just do it in a unified @Inject instead of @ModifyVariable? No, we can't modify method args with @Inject.
        // But @ModifyVariable changes the value for subsequent @Injects! So `power` here might already be modified.
        // Let's assume it's modified if we put the ModifyVariable above. Wait, order of Mixin execution can be tricky.
        // Let's just calculate it again for safety.
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
        
        if (finalPower > velocity * 1.2f) {
            // Sonic Crack (pitch 0.5f) - Play on both sides for zero latency
            net.minecraft.world.entity.player.Player player = shooter instanceof net.minecraft.world.entity.player.Player p ? p : null;
            level.playSound(player, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.FIREWORK_ROCKET_BLAST_FAR, SoundSource.PLAYERS, 1.0f, 0.5f);
            
            if (level instanceof ServerLevel serverLevel) {
                // Cloud particles - Sent by server to tracking clients
                serverLevel.sendParticles(ParticleTypes.CLOUD, 
                    shooter.getX(), shooter.getEyeY() - 0.15, shooter.getZ(), 
                    5, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }
}
