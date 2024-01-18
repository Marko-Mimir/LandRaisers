package me.marko.landraisers.farming;

import me.marko.landraisers.core.corefunc;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class wheatPlantTest implements Listener {

    @EventHandler
    public void interactDetected(PlayerInteractEvent e){
        Action action = e.getAction();
        if(action != Action.RIGHT_CLICK_BLOCK){return;}
        if(!(e.getHand().equals(EquipmentSlot.HAND))){return;}
        if(e.getItem().getType() != Material.WHEAT_SEEDS){return;}

        Location loc = e.getClickedBlock().getLocation();
        loc.getBlock().setType(Material.FARMLAND);
        Farmland farmDat = (Farmland) Material.FARMLAND.createBlockData();
        farmDat.setMoisture(7);
        loc.getBlock().setBlockData(farmDat);
        loc.add(0,1,0);
        loc.getBlock().setType(Material.WHEAT_SEEDS);
        e.getPlayer().sendMessage(corefunc.colorize("&aSHOULD HAVE WORKED :D.. YIPPIE!!"));
    }
}
