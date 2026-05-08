package net.vanillaoutsider.bettercrossbows.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsEnchantments;
import net.vanillaoutsider.bettercrossbows.registry.BetterCrossbowsGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @org.spongepowered.asm.mixin.Shadow
    @org.spongepowered.asm.mixin.Final
    protected net.minecraft.world.entity.player.Player player;

    @Inject(method = "createResult", at = @At("RETURN"))
    private void bettercrossbows$capBallisticsLevel(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;
        
        ItemStack result = menu.getSlot(2).getItem();
        if (result.isEmpty()) return;

        ItemEnchantments enchantments = result.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);

        int maxLevel = BetterCrossbowsGameRules.getBallisticsMaxLevel(this.player.level());

        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(BetterCrossbowsEnchantments.BALLISTICS_ID)) {
                if (entry.getIntValue() > maxLevel) {
                    mutable.set(entry.getKey(), maxLevel);
                }
            }
        }

        // Apply changes if we mutated it? Wait, how do we know we changed it?
        // Let's just compare the new enchantments to old.
        ItemEnchantments newEnchantments = mutable.toImmutable();
        if (!newEnchantments.equals(enchantments)) {
            result.set(DataComponents.ENCHANTMENTS, newEnchantments);
        }
    }
}
