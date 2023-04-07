package me.DuckyProgrammer.PlayerGlow;

import me.DuckyProgrammer.PlayerGlow.Commands.Glow;
import me.DuckyProgrammer.PlayerGlow.Commands.GlowColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        int pluginId = 18161; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        getCommand("glow").setExecutor(new Glow());
        getCommand("glowcolor").setExecutor(new GlowColor());
        getCommand("glow").setTabCompleter(new Glow());
        getCommand("glowcolor").setTabCompleter(new GlowColor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
