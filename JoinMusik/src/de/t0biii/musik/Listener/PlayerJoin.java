package de.t0biii.musik.Listener;

import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.t0biii.musik.JoinMusic;
import de.t0biii.musik.Methods.Updater;

public class PlayerJoin implements Listener {


  private JoinMusic plugin;

  public PlayerJoin(JoinMusic plugin) {
    this.plugin = plugin;
  }

  private HashMap<String, Long> playing = new HashMap<>();

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    final Player p = e.getPlayer();

    Song s = NBSDecoder
        .parse(new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("musik")));
    final SongPlayer sp = new RadioSongPlayer(s);

    int songLengthInSec = getTimeSeconds(sp.getSong().getLength(), sp.getSong().getSpeed());
    int songLengthInMillisec = songLengthInSec * 1000;

    if (p.hasPermission("JoinMusik.use") || p.isOp()) {
      if (!playing.containsKey(p.getUniqueId().toString())) {
        playSong(sp, p, songLengthInMillisec);
      } else {
        if (playing.get(p.getUniqueId().toString()) <= System.currentTimeMillis()) {
          playing.remove(p.getUniqueId().toString());
          playSong(sp, p, songLengthInMillisec);
        }
      }
    }
    if (this.plugin.update) {
      if ((p.isOp() || p.hasPermission("JoinMusik.update"))
          && plugin.getConfig().getBoolean("options.updateinfo")) {
        if (this.plugin.updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE)
          //&8An update is avaliable: &e&lJoinMusik v1.7&8, a &6&l RELEASE &8for &61.8
          p.sendMessage(this.plugin.prefix + "§8An update is available: §e§l" + this.plugin.name + "§8, a §6§l"
              + this.plugin.type + " §8for §6" + this.plugin.version);
        p.sendMessage(this.plugin.prefix + "§8Download: §7" + this.plugin.link2);
      }
    }
  }

  private void playSong(SongPlayer sp, Player p, long songlengthmilli) {
    playing.put(p.getUniqueId().toString(), (System.currentTimeMillis() + songlengthmilli));
    sp.addPlayer(p);
    sp.setPlaying(true);
    p.sendMessage(this.plugin.prefix + "§2Start Playing the Song:§a§l " + sp.getSong().getTitle());
  }

  private static int getTimeSeconds(short ticks, float speed) {
    long milisTotal = (long) ((ticks / speed) * 1000);
    int seconds = 0;
    seconds = (int) Math.floor(milisTotal / 1000);
    milisTotal -= seconds * 1000;
    return seconds;
  }
}