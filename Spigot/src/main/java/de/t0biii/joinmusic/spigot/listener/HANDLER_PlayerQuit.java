package de.t0biii.joinmusic.spigot.listener;

import de.t0biii.joinmusic.spigot.domain.Music;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class HANDLER_PlayerQuit implements Listener {

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    final Player player = event.getPlayer();
    Music.stop(player);
  }
}
