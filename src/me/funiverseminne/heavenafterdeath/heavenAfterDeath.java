package me.funiverseminne.heavenafterdeath;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class heavenAfterDeath extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        respawnListener l = new respawnListener(this);
        getServer().getPluginManager().registerEvents(l, this); //Respawn listener register
        getCommand("had").setExecutor(new hadCommand(this)); //Command register
        Bukkit.getLogger().info("Enabled " + this.getName());
        Bukkit.getLogger().info("HeavenAfterDeath Plugin made by FuniverseMinne");
        if(getConfig().getInt("configVersion") != 1){
            Bukkit.getLogger().info("Error: Your config may be out of date, consider regenerating it.");
        }
    }

    @Override
    public void onDisable() {
        reloadConfig();
        saveConfig();
        Bukkit.getLogger().info("HeavenAfterDeath Config Saved.");
        Bukkit.getLogger().info("Disabled " + this.getName());
    }
}
