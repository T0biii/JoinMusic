package de.t0biii.music.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.t0biii.music.domain.Music;
import de.t0biii.music.domain.Updater;
import de.t0biii.music.main.Main;

public class HANDLER_PlayerJoin implements Listener {

  private Main plugin;

  public HANDLER_PlayerJoin(Main plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();

    if(!plugin.getConfig().getBoolean("options.bungeecord")){
      int delay = plugin.getConfig().getInt("options.delaySong");

      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
        @Override
        public void run() {
          Music.start(player, plugin);
        }
      }, 20L * delay);
    }

    if (this.plugin.update) {
      if ((player.isOp() || player.hasPermission("JoinMusic.update"))
          && plugin.getConfig().getBoolean("options.updateinfo")) {
        if (this.plugin.updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE)
          // &8An update is avaliable: &e&lJoinMusic v1.7&8, a &6&l RELEASE &8for &61.8
          player.sendMessage(this.plugin.prefix + "§8An update is available: §e§l" + this.plugin.name + "§8, a §6§l"
              + this.plugin.type + " §8for §6" + this.plugin.version);
        player.sendMessage(this.plugin.prefix + "§8Download: §7" + this.plugin.link2);
      }
    }
  }
}
