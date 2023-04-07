package me.DuckyProgrammer.PlayerGlow;

import me.DuckyProgrammer.PlayerGlow.Commands.Glow;
import me.DuckyProgrammer.PlayerGlow.Commands.GlowColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends JavaPlugin implements Listener {
    public static boolean updateAvailable;
    @Override
    public void onEnable() {
        // Plugin startup logic
        int pluginId = 18161; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        updateAvailable = checkUpdate();
        getCommand("glow").setExecutor(new Glow());
        getCommand("glowcolor").setExecutor(new GlowColor());
        getCommand("glow").setTabCompleter(new Glow());
        getCommand("glowcolor").setTabCompleter(new GlowColor());
        if (updateAvailable) {
            getLogger().warning("A new version of SignEditor is available!");
            getLogger().warning("Download it at https://www.spigotmc.org/resources/player-glow.109117/");
            for (Player ops : Bukkit.getOnlinePlayers()) {
                if (ops.isOp()) {
                    ops.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bA new version of Sign Editor is available!"));
                    ops.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bDownload it at &a&nhttps://www.spigotmc.org/resources/player-glow.109117/"));
                }
            }
        }
        getLogger().info("PlayerGlow has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            if (Main.updateAvailable) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bA new version of Sign Editor is available!"));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bDownload it at &a&nhttps://www.spigotmc.org/resources/player-glow.109117/"));
            }
        }
    }

    public boolean checkUpdate() {
        try {
            URL url = new URL("https://api.spiget.org/v2/resources/109117/versions/latest");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONObject obj = (JSONObject) JSONValue.parse(content.toString());
            String version = (String) obj.get("name");
            if (version.equals(getDescription().getVersion())) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
