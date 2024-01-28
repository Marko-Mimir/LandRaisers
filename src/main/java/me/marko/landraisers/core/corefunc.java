package me.marko.landraisers.core;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public class corefunc {
    static public String colorize(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    static public NamespacedKey key = new NamespacedKey("landraisers", "markowuzhere");
    static public NamespacedKey key2 = new NamespacedKey("landraisers", "markowasalsohere");
}
