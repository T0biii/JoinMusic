package de.t0biii.joinmusic.spigot.listener;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import de.t0biii.joinmusic.spigot.domain.Music;
import de.t0biii.joinmusic.spigot.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.UUID;

public class HANDLER_SongEndEvent implements Listener {

    private final Main plugin;

    public HANDLER_SongEndEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSongEnded(SongEndEvent event) {
        if (!plugin.getConfig().getBoolean("options.music.AllowLooping")) {
            return;
        }

        Set<UUID> uuids = event.getSongPlayer().getPlayerUUIDs();
        
        for (UUID uuid : uuids) {
            Music.stop(uuid);
            Music.start(uuid, plugin);
        }
    }
}
