package me.marko.landraisers.clickables;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public interface clickable {
    public void run(Player p);
    public boolean check(ItemStack clickedItem, Player p);
    public ItemStack getCheckItem(ArrayList<String> args);
}
