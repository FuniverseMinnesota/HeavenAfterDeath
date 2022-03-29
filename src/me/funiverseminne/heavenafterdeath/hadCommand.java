package me.funiverseminne.heavenafterdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class hadCommand implements CommandExecutor {

    private heavenAfterDeath plugin; //plugin becomes reference to heavenAfterDeath class

    public hadCommand(heavenAfterDeath instanceOfMainClass) { //Constructor
        this.plugin = instanceOfMainClass;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("Error: This command must be ran by a player.");
            return true;
        }
        Player p = (Player) sender;
        if(args.length < 1){
            p.sendMessage(ChatColor.RED + "Missing Arguments, Valid Arguments: " + ChatColor.WHITE + "set, reload, debugconfig");
            return true;
        }
        if (args[0].equalsIgnoreCase("set")){
            plugin.reloadConfig(); //Reloads config for any external changes
            p.sendMessage(ChatColor.GREEN + "Saving Current Configuration Settings...");
            plugin.saveConfig(); //Saves any changes before writing to config
            p.sendMessage(ChatColor.GREEN + "Updating Configuration File...");
            plugin.getConfig().set("world", p.getWorld().getName()); //Applies world to Config
            plugin.getConfig().set("x", p.getLocation().getX()); //Applies sender X coordinate to Config
            plugin.getConfig().set("y", p.getLocation().getY()); //Applies sender Y coordinate to Config
            plugin.getConfig().set("z", p.getLocation().getZ()); //Applies sender Z coordinate to Config
            plugin.getConfig().set("rotation", p.getLocation().getYaw()); //Applies Yaw Rotation to Config
            plugin.getConfig().set("locationEnabled", true); //Sets locationEnabled to true in the Config
            if (plugin.getConfig().getBoolean("sendToChat", true)) { //Sends update info to chat if "sendToChat" is set true
                p.sendMessage(ChatColor.WHITE + "World: " + p.getWorld());
                p.sendMessage(ChatColor.WHITE + "X: " + p.getLocation().getX());
                p.sendMessage(ChatColor.WHITE + "Y: " + p.getLocation().getY());
                p.sendMessage(ChatColor.WHITE + "Z: " + p.getLocation().getZ());
                p.sendMessage(ChatColor.WHITE + "Rotation: " + p.getLocation().getYaw());
                p.sendMessage(ChatColor.WHITE + "locationsEnabled: true");
            }
            plugin.saveConfig();
            p.sendMessage(ChatColor.GREEN + "HeavenAfterDeath Config Updates Saved.");
            plugin.reloadConfig(); //Reloads Config
            p.sendMessage(ChatColor.GREEN + "HeavenAfterDeath Config Reloaded.");
        }
        else if(args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            p.sendMessage(ChatColor.GREEN + "HeavenAfterDeath Config Reloaded.");
        }
        else if(args[0].equalsIgnoreCase("debugconfig")){
            float yaw = (float) plugin.getConfig().getDouble("rotation");
            Location heaven = new Location(Bukkit.getWorld(plugin.getConfig().getString("world")), plugin.getConfig().getDouble("x"), plugin.getConfig().getDouble("y"), plugin.getConfig().getDouble("z"), yaw, 0);
            p.sendMessage("Main World: " + plugin.getConfig().getString("mainWorld"));
            p.sendMessage("Main World Spawnpoint: " + Bukkit.getWorld(plugin.getConfig().getString("mainWorld")).getSpawnLocation());
            p.sendMessage("Heaven Location: " + heaven);
            p.sendMessage("Message Enabled: " + plugin.getConfig().getBoolean("sendMessage"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message")));
            p.sendMessage("Return Delay: " + plugin.getConfig().getInt("returnDelay" + " Seconds"));
            p.sendMessage("Return Location: " + plugin.getConfig().getString("returnLocation") + " spawn");
        }
        return true;
    }
}
