package me.marko.landraisers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.marko.landraisers.core.corefunc;
import me.marko.landraisers.farming.wheatPlantTest;

public final class LandRaisers extends JavaPlugin {

    @Override
    public void onEnable() {
        wheatPlantTest wpt = new wheatPlantTest();
        getDataFolder().mkdir();
        Bukkit.getConsoleSender().sendMessage(corefunc.colorize("&aLandRaisers was created by @_marko. on discord for Generations Online."));
        this.getServer().getPluginManager().registerEvents(wpt, this);
        //work with schematic flat from the server, and paste
        // it in a 4*4 square 32 away from spawn :) so the 1st bottom left chunk would be at 48,0? then (48, 16) (64,0) (64,16)
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(corefunc.colorize("&aLandRaisers has stopped successfully!"));
    }
}
