package de.t0biii.joinmusic.spigot.listener;

import de.t0biii.joinmusic.spigot.domain.Music;
import de.t0biii.joinmusic.spigot.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;


public class HANDLER_PlayerChangeWorld implements Listener {

    Main plugin;

    public HANDLER_PlayerChangeWorld(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();

        if(plugin.getConfig().getBoolean("options.music.OneWorldonly")){
            if(!player.getWorld().getName().equalsIgnoreCase(plugin.getConfig().getString("options.music.Worldname"))) {
                Music.stop(player);
            }
        }
    }
}
