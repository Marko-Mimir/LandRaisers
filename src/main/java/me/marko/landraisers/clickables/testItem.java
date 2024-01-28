package me.marko.landraisers.clickables;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.marko.landraisers.core.corefunc;

import java.util.ArrayList;

public class testItem implements clickable{
    Material checkItem;
    public testItem(Material mat){
         checkItem = mat;
    }
    @Override
    public void run(Player p) {
        p.sendMessage(corefunc.colorize("&aWORKED!"));
    }

    @Override
    public boolean check(ItemStack clickedItem, Player p){
        if(clickedItem.getType() == checkItem){
            run(p);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCheckItem(ArrayList<String> args) {
        ItemStack returnItem = new ItemStack(checkItem);
        return returnItem;
    }
}
