package me.DuckyProgrammer.PlayerGlow.Commands;

import me.DuckyProgrammer.PlayerGlow.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlowColor implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("playerglow.glow")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return false;
        }
        if (args.length < 1 || args.length >= 3) {
            player.sendMessage(ChatColor.RED + "Usage: /glowcolor <color> [player]");
            return false;
        }
        if (args.length == 1) {
            try {
                ChatColor.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid color!");
                return false;
            }
            Util.addPlayerToGlowingTeam(ChatColor.valueOf(args[0].toUpperCase()), player);
            player.sendMessage(ChatColor.GREEN + "You are now glowing " + ChatColor.valueOf(args[0].toUpperCase()) + args[0].toUpperCase() + ChatColor.GREEN + "!");
            return true;
        }
        if (!player.hasPermission("playerglow.glow.others")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to glow others!");
            return false;
        }
        Player target = player.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        try {
            ChatColor.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid color!");
            return false;
        }
        Util.addPlayerToGlowingTeam(ChatColor.valueOf(args[0].toUpperCase()), target);
        player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " is now glowing " + ChatColor.valueOf(args[0].toUpperCase()) + args[0].toUpperCase() + ChatColor.GREEN + "!");
        target.sendMessage(ChatColor.GREEN + "You are now glowing " + ChatColor.valueOf(args[0].toUpperCase()) + args[0].toUpperCase() + ChatColor.GREEN + "!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("glowcolor") || command.getName().equalsIgnoreCase("glowcolour")) {
            List<String> completions = new ArrayList<>();
            List<String> commands = new ArrayList<>();
            if (args.length == 1) {
                for (ChatColor color : ChatColor.values()) {
                    commands.add(color.name().toLowerCase());
                }
                StringUtil.copyPartialMatches(args[0], commands, completions);
                Collections.sort(completions);
                return completions;
            }
            if (args.length == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    commands.add(player.getName());
                }
                StringUtil.copyPartialMatches(args[1], commands, completions);
                Collections.sort(completions);
                return completions;
            }
            if (args.length > 2) {
                commands.add("");
                StringUtil.copyPartialMatches(args[2], commands, completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
