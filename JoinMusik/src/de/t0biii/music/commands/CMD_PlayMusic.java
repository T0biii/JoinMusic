package de.t0biii.music.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import de.t0biii.music.main.Main;
import java.util.Collections;
import java.util.List;

public class CMD_PlayMusic implements CommandExecutor {

  private Main plugin;

  public CMD_PlayMusic(Main plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (sender.hasPermission("JoinMusic.command.reload")) {
      if (sender instanceof Player) {
        Player player = ((Player) sender).getPlayer();
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("reload")) {
            try {
              plugin.reloadConfig();
              player.sendMessage(
                  ChatColor.DARK_AQUA + plugin.getConfig().getString("messages.reload"));
            } catch (Exception exception) {
              player.sendMessage(ChatColor.RED + "Reload failed!");
            }
          } else
            sendInstructions(player);
        } else
          sendInstructions(player);
      } else
        sender.sendMessage(plugin.prefix + "This command is not for console!");
    } else {
      sender.sendMessage(cmd.getPermissionMessage());
    }
    return true;
  }

  private void sendInstructions(CommandSender sender) {
    sender.sendMessage(
        ChatColor.GRAY + "======= " + ChatColor.GREEN + "JoinMusic " + ChatColor.GRAY + "=======");
    sender.sendMessage(ChatColor.GRAY + "/jm " + ChatColor.GREEN + "reload  " + ChatColor.DARK_GRAY
        + "| " + ChatColor.GREEN + "Config Reload!");
    sender.sendMessage(
        ChatColor.GRAY + "======= " + ChatColor.GREEN + "JoinMusic " + ChatColor.GRAY + "=======");
  }

  public static TabCompleter tabCompleter = new TabCompleter() {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s,
        String[] strings) {
      if (strings.length == 1) {
        return Collections.singletonList("reload");
      }
      return Collections.singletonList(" ");
    }
  };
}
