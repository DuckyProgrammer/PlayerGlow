package me.DuckyProgrammer.PlayerGlow.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Glow implements CommandExecutor, TabCompleter {


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
        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "Usage: /glow [player]");
            return false;
        }
        if (args.length == 0) {
            if (player.isGlowing()) {
                player.setGlowing(false);
                player.sendMessage(ChatColor.GREEN + "You are no longer glowing!");
            } else {
                player.setGlowing(true);
                player.sendMessage(ChatColor.GREEN + "You are now glowing!");
            }
            return true;
        }
        if (!player.hasPermission("playerglow.glow.others")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to glow others!");
            return false;
        }
        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        if (target.isGlowing()) {
            target.setGlowing(false);
            player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " is no longer glowing!");
            target.sendMessage(ChatColor.GREEN + "You are no longer glowing!");
        } else {
            target.setGlowing(true);
            player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " is now glowing!");
            target.sendMessage(ChatColor.GREEN + "You are now glowing!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("glow") || command.getName().equalsIgnoreCase("glow")) {
            List<String> completions = new ArrayList<>();
            List<String> commands = new ArrayList<>();
            if (args.length == 1) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    commands.add(online.getName());
                }
                StringUtil.copyPartialMatches(args[0], commands, completions);
                Collections.sort(completions);
                return completions;
            } else {
                commands.add("");
                StringUtil.copyPartialMatches(args[1], commands, completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
