package de.t0biii.musik.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.t0biii.musik.main.Main;

import java.util.ArrayList;

public class CMD_PlayMusic implements CommandExecutor{

	private Main plugin;
	public CMD_PlayMusic(Main plugin){ this.plugin = plugin;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = ((Player) sender).getPlayer();
            if(args.length == 1) {
            	if(args[0].equalsIgnoreCase("reload")) {
            		try {
            			plugin.reloadConfig();
            			player.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("messages.reload"));
            		} catch (Exception exception) {
            			player.sendMessage(ChatColor.RED + "Reload failed!");
            		}	
            	}
            } else {
            	player.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GREEN + "JoinMusik " + ChatColor.GRAY + "=======");
                player.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "reload  " + ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + "Config Reload!");
				player.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GREEN + "JoinMusik " + ChatColor.GRAY + "=======");            }
		} else
			sender.sendMessage(plugin.prefix + "This command is not for console!");
		return true;
	}
}