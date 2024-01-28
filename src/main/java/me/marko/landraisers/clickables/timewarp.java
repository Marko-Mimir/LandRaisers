package me.marko.landraisers.clickables;

import me.marko.landraisers.core.corefunc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class timewarp implements clickable{

    public ItemStack checkItem;
    @Override
    public void run(Player p) {
        return;
    }

    @Override
    public boolean check(ItemStack clickedItem, Player p) {
        if(clickedItem.getType() != Material.CLOCK){return false;}
        if(clickedItem.getItemMeta().getPersistentDataContainer().has(corefunc.key)){
            realRun(clickedItem, p);
            return true;
        }
        return false;
    }

    public void realRun(ItemStack clickedItem, Player p){
        ItemMeta itemMeta = clickedItem.getItemMeta();
        if(!(itemMeta.getPersistentDataContainer().has(corefunc.key2))){p.sendMessage(corefunc.colorize("&aFail BOOWOMP"));return;}
        int hr = itemMeta.getPersistentDataContainer().get(corefunc.key2, PersistentDataType.INTEGER);
        p.sendMessage(corefunc.colorize("&aPSST! Pretend this is giving you money!"));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, hr*10, 255, false, false, false));
    }

    public void init(){
        if(checkItem == null){
            checkItem = new ItemStack(Material.CLOCK);
            checkItem.getData();
            ItemMeta itemMeta = checkItem.getItemMeta();
            itemMeta.getPersistentDataContainer().set(corefunc.key, PersistentDataType.BOOLEAN, true);
            checkItem.setItemMeta(itemMeta);
        }
    }

    @Override
    public ItemStack getCheckItem(ArrayList<String> args) {
        if(args == null){
            return null;
        }
        int hr = Integer.parseInt(args.get(0));
        ItemStack returnItem = checkItem;
        ItemMeta returnMeta = returnItem.getItemMeta();
        returnMeta.setDisplayName(corefunc.colorize("&dTimeWarper® "+hr+"hr"));
        returnMeta.setLore(Arrays.asList(corefunc.colorize("&f'10/10 Easiest "+hr+"hr of my life!'"), corefunc.colorize("&o&7Get the money for "+hr+"hr worth of work.")));
        returnMeta.getPersistentDataContainer().set(corefunc.key2, PersistentDataType.INTEGER, hr);
        returnItem.setItemMeta(returnMeta);
        return returnItem;
    }
}
