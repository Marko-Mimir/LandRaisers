package me.marko.landraisers.clickables;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface clickable {
    public void run(Player p);
    public void check(ItemStack clickedItem, Player p);
}
