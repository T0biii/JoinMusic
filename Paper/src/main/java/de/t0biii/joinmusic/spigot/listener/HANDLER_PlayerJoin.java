package de.t0biii.joinmusic.spigot.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.t0biii.joinmusic.spigot.domain.Music;
import de.t0biii.joinmusic.spigot.main.Main;

public class HANDLER_PlayerJoin implements Listener {

  private Main plugin;

  public HANDLER_PlayerJoin(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();

    if (!plugin.getConfig().getBoolean("options.bungeecord")) {
      int delay = plugin.getConfig().getInt("options.delaySong");

      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
        @Override
        public void run() {
          Music.start(player, plugin);
        }
      }, 20L * delay);
    }

    if (!this.plugin.UPDATE_AVAILABLE) {
      return;
    }
    if (!(player.isOp() || player.hasPermission("JoinMusic.update"))) {
      return;
    }
    if (!plugin.getConfig().getBoolean("options.updateinfo")) {
      return;
    }

    player.sendMessage(this.plugin.prefix + "§8An update is available: §e§l" + this.plugin.name + "§8, §6§l§8Version §6" + this.plugin.version);
    player.sendMessage(this.plugin.prefix + "§8Download: §7" + this.plugin.link);
  }
}