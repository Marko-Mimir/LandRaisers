package me.marko.landraisers.clickables;

import me.marko.landraisers.core.corefunc;
import me.marko.landraisers.core.itemListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class giveItem implements CommandExecutor {
    public itemListener listener;
    public giveItem(itemListener itemListener){listener=itemListener;}
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){commandSender.sendMessage(corefunc.colorize("&4FAIL! Sender is not player :(")); return true;}
        Player p = (Player) commandSender;
        if(args == null){commandSender.sendMessage(corefunc.colorize("&4Error!&c No arguments detected. Usage: /giveItem (ITEM_ID) (ITEM ARGUMENT)"));return true;}
        p.sendMessage(args);
        switch (args.length){
            case 0:
                commandSender.sendMessage(corefunc.colorize("&4Error!&c No arguments detected. Usage: /giveItem (ITEM_ID) (ITEM ARGUMENT)"));
                return true;
            case 1:
                clickable clickable1 = listener.getClickables().get(Integer.parseInt(args[0]));
                p.getInventory().addItem(clickable1.getCheckItem(new ArrayList<>()));
                return true;
            default:
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.addAll(Arrays.asList(args));
                arrayList.remove(0);
                clickable clickable2 = listener.getClickables().get(Integer.parseInt(args[0]));
                p.getInventory().addItem(clickable2.getCheckItem(arrayList));
        }
        return true;
    }
}
