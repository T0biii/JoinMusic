package de.t0biii.music.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import de.t0biii.music.domain.Music;
import de.t0biii.music.main.Main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMD_PlayMusic implements CommandExecutor {

  private Main plugin;

  public CMD_PlayMusic(Main plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (sender.hasPermission("JoinMusic.use")) {
      if (sender instanceof Player) {
        Player player = ((Player) sender).getPlayer();
        String noperm = plugin.getConfig().getString("messages.no-permission");
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("JoinMusic.command.reload")) {
              try {
                plugin.reloadConfig();
                player.sendMessage(plugin.prefix + ChatColor.DARK_AQUA
                    + plugin.getConfig().getString("messages.reload"));
              } catch (Exception exception) {
                player.sendMessage(plugin.prefix + ChatColor.RED + "Reload failed!");
              }
            } else {
              sender.sendMessage(plugin.prefix + ChatColor.RED + noperm);
            }
          } else if (args[0].equalsIgnoreCase("stop")) {
            if (sender.hasPermission("JoinMusic.command.stop")) {
              Music.stop(player);
              player.sendMessage(plugin.prefix + ChatColor.DARK_AQUA
                  + plugin.getConfig().getString("messages.stop"));
            } else {
              sender.sendMessage(plugin.prefix + ChatColor.RED + noperm);
            }
          } else {
            sendInstructions(player);
          }
        } else {
          sendInstructions(player);
        }
      } else {
        sender.sendMessage(plugin.prefix + "This command is not for console!");
      }
    }
    return true;
  }

  private void sendInstructions(CommandSender sender) {
    sender.sendMessage(
        ChatColor.GRAY + "======= " + ChatColor.GREEN + plugin.prefix + ChatColor.GRAY + "=======");
    sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "reload  " + ChatColor.DARK_GRAY
        + "| " + ChatColor.GREEN + "Config Reload!");
    sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "stop  " + ChatColor.DARK_GRAY
        + "| " + ChatColor.GREEN + "Stop Song Playing!");
    sender.sendMessage(
        ChatColor.GRAY + "======= " + ChatColor.GREEN + plugin.prefix + ChatColor.GRAY + "=======");
  }

  public static TabCompleter tabCompleter = new TabCompleter() {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s,
        String[] strings) {
      if (strings.length == 1) {
        ArrayList<String> list = new ArrayList<String>();
        if(commandSender.hasPermission("JoinMusic.command.reload")) {
          list.add("reload");
        }
        if(commandSender.hasPermission("JoinMusic.command.stop")) {
          list.add("stop");
        }else {
          list.add(" ");
        }
        return list;
      }
      return Collections.singletonList(" ");
    }
  };
}
