package me.marko.landraisers.land;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.marko.landraisers.LandRaisers;
import me.marko.landraisers.core.corefunc;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

public class test implements CommandExecutor {

    public class plotDataObject{
        plotDataObject(Player p, int[] chunk){
            owner = p.getDisplayName();
            ownerUID = p.getUniqueId();
            chunkZero = chunk;
        }
        String owner = ""; //Player IGN
        UUID ownerUID; //Player uuid
        int[] chunkZero;

    }
    HashMap<UUID, plotDataObject> saveFile = new HashMap<>();
    int xPlot =-1;
    int yPLot = 1;
    int maxPlot = 1;
    int minPlot = -1;
    String current = "TL";
    LandRaisers parent;

    public test(LandRaisers main){
        parent = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)){commandSender.sendMessage(corefunc.colorize("&4FAIL! Sender is not player :(")); return true;}
        Player p = (Player) commandSender;

        if (args.length != 0) {
            Location loc = p.getLocation();
            int[] chunk = {loc.getChunk().getX(), loc.getChunk().getZ()};
            plotDataObject pdo = saveFile.get(p.getUniqueId());
            if(pdo == null){commandSender.sendMessage(corefunc.colorize("&4FAIL! Player does not have a plot BooWomp")); return true;}
            commandSender.sendMessage((Math.abs(chunk[0]-pdo.chunkZero[0]))+" Distance of X chunk val");
            commandSender.sendMessage((Math.abs(chunk[1]-pdo.chunkZero[1]))+" Distance of Y chunk val");
            return true;
        }
        Clipboard clip;
        File f = new File(parent.getDataFolder().getPath()+"/schematics/flat.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(f);
        try (ClipboardReader reader = format.getReader(Files.newInputStream(f.toPath()))){
            clip = reader.read();
        } catch (IOException e) {
            commandSender.sendMessage(corefunc.colorize("&4Failed! please find staff member."+ e.getMessage()));
            return true;
        }

        int step = 112;
        World w = parent.getServer().getWorld("world");
        Location loc = new Location(w, xPlot*step, 10, yPLot*step);
        //loc.getChunk().getX() loc.getChunk().getZ()

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(w));
        BlockVector3 min = BlockVector3.at(xPlot*step, 10, yPLot*step);
        BlockVector3 max = BlockVector3.at(xPlot*(step+96), 10, yPLot*step+96);
        ProtectedRegion plot = new ProtectedCuboidRegion(p.getUniqueId().toString(), min, max);
        regions.addRegion(plot);
        int[] d = {loc.getChunk().getX(), loc.getChunk().getZ()};
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(w))){
            Operation operation = new ClipboardHolder(clip)
                    .createPaste(editSession)
                    .to(BlockVector3.at(xPlot*step,10,yPLot*step))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            commandSender.sendMessage(corefunc.colorize("&4Failed! Please find staff member. "+e.getMessage()));
            return true;
        }
        stepPlot();
        plotDataObject PDO = new plotDataObject(p, d);
        saveFile.put(p.getUniqueId(), PDO);
        return true;
    }



    private void stepPlot(){
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
                if(yPLot>=maxPlot){
                    current="TL";
                    minPlot -=1;
                    maxPlot +=1;
                    xPlot = minPlot;
                    yPLot = maxPlot;
                }
                break;
            default:
                break;
        }
    }
}