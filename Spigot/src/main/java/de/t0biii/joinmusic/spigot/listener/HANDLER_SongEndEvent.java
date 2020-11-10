package de.t0biii.joinmusic.spigot.listener;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.event.SongStoppedEvent;
import de.t0biii.joinmusic.spigot.domain.Music;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.UUID;

public class HANDLER_SongEndEvent implements Listener {

    @EventHandler
    public void onSongEnded(SongEndEvent event){
        Set<UUID> uuids = event.getSongPlayer().getPlayerUUIDs();
        for(UUID uuid : uuids){
            Music.stop(uuid);
        }
    }

    @EventHandler
    public void onSongStopped(SongStoppedEvent event){
        Set<UUID> uuids = event.getSongPlayer().getPlayerUUIDs();
        for(UUID uuid : uuids){
            Music.stop(uuid);
        }
    }
}
