package de.t0biii.joinmusic.spigot.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import de.t0biii.joinmusic.spigot.domain.Music;
import de.t0biii.joinmusic.spigot.main.Main;
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
        String noperm = plugin.getConfig().getString("messages.no-permission").replaceAll("&", "§");
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            if (sender.hasPermission("JoinMusic.command.reload")) {
              try {
                plugin.cm.reloadConfigs();
                player
                    .sendMessage(plugin.prefix + plugin.getConfig().getString("messages.reload").replaceAll("&", "§"));
              } catch (Exception exception) {
                player.sendMessage(plugin.prefix + ChatColor.RED + "Reload failed!");
              }
            } else {
              sender.sendMessage(plugin.prefix + noperm);
            }
          } else if (args[0].equalsIgnoreCase("stop")) {
            if (sender.hasPermission("JoinMusic.command.stop")) {
              Music.stop(player);
              player.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.stop").replaceAll("&", "§"));
            } else {
              sender.sendMessage(plugin.prefix + noperm);
            }
          } else if (args[0].equalsIgnoreCase("disable") && plugin.getConfig().getBoolean("options.allowDisabling")) {
        	  if(sender.hasPermission("JoinMusic.command.disableOwn")) {
        		plugin.cm.getUserConfigs().set(player.getUniqueId().toString(),true);
       		    plugin.cm.saveUserConfigs();
                sender.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.disabled").replaceAll("&", "§"));
        	  }else {
        		sender.sendMessage(plugin.prefix + noperm);
        	  }
          }else if (args[0].equalsIgnoreCase("enable") && plugin.getConfig().getBoolean("options.allowDisabling")) {
        	  if(sender.hasPermission("JoinMusic.command.disableOwn")) {
        		  plugin.cm.getUserConfigs().set(player.getUniqueId().toString(),null);
        		  plugin.cm.saveUserConfigs();
        		  sender.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.enabled").replaceAll("&", "§"));
        	  } else {
        		sender.sendMessage(plugin.prefix + noperm);
        	  }
          } else{
            sendInstructions(player);
          }
        } else {
          sendInstructions(player);
        }
      } else {
    	  if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
              try {
                plugin.cm.reloadConfigs();
                sender
                  .sendMessage(plugin.cprefix + plugin.getConfig().getString("messages.reload"));
                } catch (Exception exception) {
                  sender.sendMessage(plugin.cprefix + ChatColor.RED + "Reload failed!");
                }
    	  }else {
    		  sender.sendMessage(plugin.cprefix + "Only reload is for console!");
    	  }
      }
    }
    return true;
  }

  private void sendInstructions(CommandSender sender) {
    sender.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GREEN + plugin.prefix + ChatColor.GRAY + "=======");
    if(sender.hasPermission("JoinMusic.command.reload")) {
      sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "reload  " + ChatColor.DARK_GRAY + "| "
          + ChatColor.GREEN + "Config Reload!");
    }
    sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "stop  " + ChatColor.DARK_GRAY + "| "
        + ChatColor.GREEN + plugin.getConfig().getString("messages.help.stop"));
    if(sender.hasPermission("JoinMusic.command.disableOwn")) {
      sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "disable  " + ChatColor.DARK_GRAY + "| "
          + ChatColor.GREEN + plugin.getConfig().getString("messages.help.disableOwn"));
      sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "enable   " + ChatColor.DARK_GRAY + "| "
          + ChatColor.GREEN + plugin.getConfig().getString("messages.help.enableOwn"));
      sender.sendMessage(ChatColor.GRAY + "======= " + ChatColor.GREEN + plugin.prefix + ChatColor.GRAY + "=======");
    }
  }

  public static TabCompleter tabCompleter = new TabCompleter() {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
      if (strings.length == 1) {
        ArrayList<String> list = new ArrayList<>();
        if (commandSender.hasPermission("JoinMusic.command.reload")) {
          list.add("reload");
        }
        if (commandSender.hasPermission("JoinMusic.command.stop")) {
          list.add("stop");
        }
        if (commandSender.hasPermission("JoinMusic.command.disableOwn")) {
          list.add("enable");
          list.add("disable");
        }
        if (!commandSender.hasPermission("JoinMusic.command.stop")
            && !commandSender.hasPermission("JoinMusic.command.reload")
            && !commandSender.hasPermission("JoinMusic.command.disableOwn")) {
          list.add(" ");
        }
        return list;
      }
      return Collections.singletonList(" ");
    }
  };
}
