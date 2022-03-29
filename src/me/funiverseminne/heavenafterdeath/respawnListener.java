package me.funiverseminne.heavenafterdeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class respawnListener implements Listener {

    private heavenAfterDeath plugin; //plugin becomes reference to heaveAfterDeath class

    public respawnListener(heavenAfterDeath instanceOfMainClass) { //Constructor
        this.plugin = instanceOfMainClass;
    }

    //Stuff happens here when the player respawns
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        float yaw = (float) plugin.getConfig().getDouble("rotation");
        Location heaven = new Location(Bukkit.getWorld(plugin.getConfig().getString("world")), plugin.getConfig().getDouble("x"), plugin.getConfig().getDouble("y"), plugin.getConfig().getDouble("z"), yaw, 0);
        int delay = plugin.getConfig().getInt("returnDelay");
        if (plugin.getConfig().getBoolean("locationEnabled", true) && !p.hasPermission("heavenafterdeath.bypass")){
            new BukkitRunnable(){
                public void run(){
                    p.teleport(heaven); //teleport player 1 tick after respawning
                }
            }.runTaskLater(plugin, 1);
            if (plugin.getConfig().getBoolean("sendMessage", true)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message"))); //send a message to the player that they died
            }
            if (delay > 0) {
                new BukkitRunnable(){
                    public void run() {
                        String rl = plugin.getConfig().getString("returnLocation");
                        Location respawnpoint = p.getBedSpawnLocation();
                        Location worldspawn = Bukkit.getWorld(plugin.getConfig().getString("mainWorld")).getSpawnLocation();
                        if(respawnpoint != null) {
                            if (rl.equalsIgnoreCase("player")) {
                                p.teleport(respawnpoint);
                            }
                            else p.teleport(worldspawn);
                        }
                        if (rl.equalsIgnoreCase("world")) {
                            p.teleport(worldspawn);
                        }
                    }
                }.runTaskLater(plugin, 20 * delay);
            }
        }
    }
}
