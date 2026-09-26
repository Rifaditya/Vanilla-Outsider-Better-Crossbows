package net.vanillaoutsider.bettercrossbows.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {

    @Shadow @Final protected ContainerLevelAccess access;

    @Inject(method = "getEnchantmentList", at = @At("RETURN"), cancellable = true)
    private void bettercrossbows$capEnchantmentList(RegistryAccess registryAccess, ItemStack itemStack, int slot, int enchantmentCost, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        List<EnchantmentInstance> list = cir.getReturnValue();
        if (list == null || list.isEmpty()) return;

        this.access.evaluate((level, pos) -> {
            int maxLevel = BetterCrossbowsGameRules.getBallisticsMaxLevel(level);

            boolean changed = false;
            List<EnchantmentInstance> newList = new ArrayList<>(list.size());

            for (EnchantmentInstance instance : list) {
                if (instance.enchantment().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                    if (instance.level() > maxLevel) {
                        changed = true;
                        if (maxLevel > 0) {
                            newList.add(new EnchantmentInstance(instance.enchantment(), maxLevel));
                        }
                    } else {
                        newList.add(instance);
                    }
                } else {
                    newList.add(instance);
                }
            }

            if (changed) {
                cir.setReturnValue(newList);
            }
            return null;
        });
    }
}
