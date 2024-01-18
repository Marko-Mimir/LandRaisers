package me.marko.landraisers.land;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class test implements CommandExecutor {
    int xPlot =-1;
    int yPLot = 1;
    int maxPlot = 1;
    int minPlot = -1;
    String current = "TL";
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }



    public int[] claimPlot(){
        switch (current){
            case "TL": xPlot+=1;
                if(xPlot>=maxPlot){current="TR";}
                break;
            case "TR":yPLot-=1;
                if(yPLot<=minPlot){current="BR";}
                break;
            case "BR":xPlot-=1;
                if(xPlot<=minPlot){current="BL";}
                break;
            case "BL":yPLot+=1;
                if(yPLot>=maxPlot){current="TL";}
                break;
            default:
                break;
        }
        return(new int[]{xPlot, yPLot});
    }
}