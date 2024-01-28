package me.marko.landraisers.crate;

import me.marko.landraisers.LandRaisers;
import me.marko.landraisers.core.corefunc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Random;

public class openCrate implements CommandExecutor  {

    private LandRaisers parent;
    static public Material[] materials = {Material.CYAN_CONCRETE, Material.RED_CONCRETE, Material.GRAY_CONCRETE};
    public openCrate(LandRaisers main){
        parent = main;
    }

    static public int iterator;
    static public Item itemEntity;
    static public BukkitTask task = null;
    static public Player currentPlayer = null;


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){commandSender.sendMessage(corefunc.colorize("&4FAIL! Sender is not player :(")); return true;}
        Player p = (Player) commandSender;
        BlockFace dir = p.getFacing();
        if(dir == BlockFace.DOWN || dir == BlockFace.UP){commandSender.sendMessage(corefunc.colorize("&4Sorry! Failed to place chest! (Try not looking up or down as the chest places in the direction you look)"));return true;}

        Vector vector = dir.getDirection();
        Location loc = p.getLocation();
        loc.add(vector);
        Block block = loc.getBlock();

        int i = 0;
        while (!block.isEmpty()){
            loc.add(vector);
            block = loc.getBlock();
            i++;
            if(i>=5){
                loc.add(0,1,0);
                block = loc.getBlock();
                if(!block.isEmpty()){
                    commandSender.sendMessage(corefunc.colorize("&4Sorry! Failed to place Chest! (There were blocks in the way, try moving around a little bit!)"));
                    return true;
                }
            }
        }
        block.setType(Material.CHEST);
        org.bukkit.block.data.type.Chest chestDat = (org.bukkit.block.data.type.Chest) block.getBlockData();
        chestDat.setFacing(dir.getOppositeFace());
        block.setBlockData(chestDat);
        Chest chest = (Chest) block.getState();
        chest.open();
        chest.setCustomName("CLOSE-MARKO-ABCFG");
        chest.update();
        ItemStack item = new ItemStack(Material.RED_CONCRETE);
        loc.add(0,1,0);
        World w = parent.getServer().getWorld("world");
        Item itemEnt = w.dropItem(loc, item);
        itemEntity = itemEnt;
        itemEnt.setVelocity(new Vector(0,0,0));
        itemEnt.setCustomNameVisible(true);
        itemEnt.setCustomName(corefunc.colorize("&b&lTESTING YEAH!"));
        itemEnt.setPickupDelay(30000);
        Random rand = new Random();
        iterator = rand.nextInt(12);
        currentPlayer = p;

        task = Bukkit.getScheduler().runTaskTimer(parent, new Runnable() {
            @Override
            public void run() {
                if(iterator != 0){
                    itemEntity.setItemStack(new ItemStack(materials[new Random().nextInt(materials.length)]));
                    itemEntity.setCustomName(itemEntity.getCustomName()+iterator);
                    iterator--;
                }else {
                    currentPlayer.getInventory().addItem(itemEntity.getItemStack());
                    itemEntity.getLocation().getBlock().setType(Material.AIR);
                    itemEntity.remove();
                    task.cancel();
                }

            }
        }, 10, 10);



        return true;
    }
}
