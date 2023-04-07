package me.DuckyProgrammer.PlayerGlow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Util {

    public static void addPlayerToGlowingTeam(ChatColor color, Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        Scoreboard scoreboard = scoreboardManager.getMainScoreboard();
        Team team = scoreboard.getTeam(color.name());
        if (team == null) {
            team = scoreboard.registerNewTeam(color.name());
            team.setColor(color);
        }
        team.setColor(color);
        team.addEntry(player.getName());
    }
}