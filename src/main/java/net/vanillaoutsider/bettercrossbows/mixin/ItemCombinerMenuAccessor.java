package net.vanillaoutsider.bettercrossbows.mixin;

// Verified against: ItemCombinerMenu.java (Minecraft 26.1.2)

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemCombinerMenu.class)
public interface ItemCombinerMenuAccessor {
    @Accessor("player")
    Player bettercrossbows$getPlayer();
}
