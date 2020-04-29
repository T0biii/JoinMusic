package de.t0biii.music.domain;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.entity.Player;
import com.google.common.io.Files;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.t0biii.music.main.Main;

public class Music {

  // private static HashMap<String, Long> playing = new HashMap<>();
  private static HashMap<UUID, SongPlayer> playingSong = new HashMap<>();

  public static void play(Player player, Main plugin) {
    if (player.hasPermission("JoinMusic.use") || player.isOp()) {
      if (!playingSong.containsKey(player.getUniqueId())) {
        playSong(player, plugin);
      } else {
        final SongPlayer sp = playingSong.get(player.getUniqueId());
        if (!sp.isPlaying()) {
          removePlayer(player);
          playSong(player, plugin);
        }
      }
    }
  }

  public static void stop(Player player) {
    removePlayer(player);
  }

  private static void removePlayer(Player player) {
    if (playingSong.containsKey(player.getUniqueId())) {
      SongPlayer sp = playingSong.get(player.getUniqueId());
      sp.destroy();
      playingSong.remove(player.getUniqueId());
    }
  }

  private static void playSong(Player player, Main plugin) {
    try {
      File songFile;
      if(plugin.getConfig().getBoolean("options.music.random")) {
        songFile = SelectRandomFileFromFolder(plugin);  
      }else {
        songFile = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      }
      Song s = NBSDecoder.parse(songFile);
      final SongPlayer sp = new RadioSongPlayer(s);
      playingSong.put(player.getUniqueId(), sp);
      sp.addPlayer(player);
      sp.setPlaying(true);
      if (plugin.getConfig().getBoolean("options.printSongTitel")) {
        player.sendMessage(plugin.prefix + "§2Start Playing the Song:§a§l " + sp.getSong().getTitle());
      }
    } catch (IllegalArgumentException e) {
      System.err.println(plugin.cprefix + "No sounds detected");
    }
  }
  
  public static void createRandomFileDir(Main plugin) {
    File dir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername"));
    if(!dir.exists()) {
      dir.mkdirs();
      File songFile =  new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      File copyDir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername") + "/" + plugin.getConfig().getString("music"));
      try {
        Files.copy(songFile, copyDir);
      } catch (IOException e) {
      }
    }
  }
  
  private static File SelectRandomFileFromFolder(Main plugin){
    createRandomFileDir(plugin);
    File dir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername"));
    if(dir.exists()) {
      File[] files = dir.listFiles();
      if(files.length > 0) {
        Random rand = new Random();
        File file = files[rand.nextInt(files.length)];
        return file;
      }else {
        return new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      }
    }
    return null;
  }
}
