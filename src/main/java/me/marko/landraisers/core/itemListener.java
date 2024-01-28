package me.marko.landraisers.core;
import me.marko.landraisers.clickables.clickable;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class itemListener implements Listener {
    @EventHandler
    public void onChestOpen(InventoryOpenEvent e){
        if(e.getView().getTitle().equals("CLOSE-MARKO-ABCFG")){
            e.setCancelled(true);
        }
    }

    private ArrayList<clickable> clickables;

    public void addClickables(clickable clickable){
        clickables.add(clickable);
    }
    public void removeClickables(clickable clickable){
        clickables.remove(clickable);
    }
    public ArrayList<clickable> getClickables(){
        return clickables;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e){
        if(e.getAction()!= Action.RIGHT_CLICK_BLOCK){return;}
        if(!e.getHand().equals(EquipmentSlot.HAND)){return;}
        if(e.getItem().getType() == Material.AIR){return;}
        for (clickable item : clickables){
            item.check(e.getItem(), e.getPlayer());
        }
    }
}
