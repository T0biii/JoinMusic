package de.t0biii.music.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import de.t0biii.music.domain.Music;

public class HANDLER_PlayerQuit implements Listener {
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    final Player player = event.getPlayer();
    Music.stop(player);
  }
}
