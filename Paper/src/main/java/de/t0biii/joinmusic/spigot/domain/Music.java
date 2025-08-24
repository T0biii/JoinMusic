package de.t0biii.joinmusic.spigot.domain;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.StereoMode;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.google.common.io.Files;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.t0biii.joinmusic.spigot.main.Main;

public class Music {

  public static HashMap<UUID, SongPlayer> playingSong = new HashMap<>();

  private static HashMap<String, Song> cachedSongs = new HashMap<>();

  public static void preloadSongs(Main plugin) {
    cachedSongs.clear();

    if (plugin.getConfig().getBoolean("options.music.random")) {
      File dir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername"));
      if (dir.exists()) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
          for (File file : files) {
            Song song = NBSDecoder.parse(file);
            cachedSongs.put(file.getName(), song);
          }
        }
      }
    } else {
        File mainSongFile = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
        Song mainSong = NBSDecoder.parse(mainSongFile);
        cachedSongs.put(mainSongFile.getName(), mainSong);
    }
  }

  private static Set<String> getCachedSongNames() {
    return cachedSongs.keySet();
  }

  public static void start(Player player, Main plugin) {
    if(plugin.getConfig().getBoolean("options.allowDisabling")) {
      if(plugin.cm.getUserConfigs().getBoolean(player.getUniqueId().toString(), false)) {
        return;
      }
    }
    if(plugin.getConfig().getBoolean("options.music.OneWorldonly")) {
      if(!player.getWorld().getName().equalsIgnoreCase(plugin.getConfig().getString("options.music.Worldname"))) {
        return;
      }
    }
    play(player, plugin);
  }

  public static void start(UUID uuid, Main plugin) {
    Player p = Bukkit.getPlayer(uuid);
    start(p,plugin);
  }

  public static void stop(Player player) {
    removePlayer(player.getUniqueId());
  }

  public static void stop(UUID uuid) {
    removePlayer(uuid);
  }

  public static void skip(Player player, Main plugin) {
    if (plugin.getConfig().getBoolean("options.music.AllowLooping") && plugin.getConfig().getBoolean("options.music.random")) {
      stop(player);
      start(player, plugin);
    }
  }

  private static void play(Player player, Main plugin) {
    if (player.hasPermission("JoinMusic.use") || player.isOp()) {
      if (!playingSong.containsKey(player.getUniqueId())) {
        playSong(player, plugin);
      } else {
        final SongPlayer sp = playingSong.get(player.getUniqueId());
        if (!sp.isPlaying()) {
          stop(player);
          start(player, plugin);
        }
      }
    }
  }

  private static void removePlayer(UUID uuid) {

    if (playingSong.containsKey(uuid)) {
      SongPlayer sp = playingSong.get(uuid);
      sp.destroy();
      playingSong.remove(uuid);
    }
  }



  private static void playSong(Player player, Main plugin) {
    try {
      Song s;

      if (plugin.getConfig().getBoolean("options.music.random")) {
        Set<String> keys = getCachedSongNames();
        if (!keys.isEmpty()) {
          String[] songNames = keys.toArray(new String[0]);
          Random rand = new Random();
          String randomSongName = songNames[rand.nextInt(songNames.length)];
          s = cachedSongs.get(randomSongName);
        } else {
          plugin.log.warning(plugin.cprefix + "No song found in Random folder.");
          return;
        }

      } else {
        String mainSongName = new File(plugin.getConfig().getString("music")).getName();
        s = cachedSongs.get(mainSongName);
      }

      final RadioSongPlayer sp = new RadioSongPlayer(s);
      sp.addPlayer(player);
      sp.setPlaying(true);
      int volume = plugin.getConfig().getInt("options.music.Volume");
      if(volume >= 0 && volume <= 100){
        sp.setVolume((byte) volume);
      }else{
        plugin.log.warning(plugin.cprefix + "ERROR: Volume must be between 0-100 (Your Value: " + volume + ")");
      }

      if(plugin.getConfig().getBoolean("options.music.10Octave")){
        sp.setEnable10Octave(true);
      }
      String mode = plugin.getConfig().getString("options.music.Mode");
      if(mode.equalsIgnoreCase("MonoMode")) {
        sp.setChannelMode(new MonoMode());
      }else if(mode.equalsIgnoreCase("MonoStereoMode")){
        sp.setChannelMode(new MonoStereoMode());
      }else if(mode.equalsIgnoreCase("StereoMode")){
        sp.setChannelMode(new StereoMode());
      }else{
        sp.setChannelMode(new MonoMode());
      }
      playingSong.put(player.getUniqueId(), sp);

      String playingMessage = plugin.getConfig().getString("messages.playing");
      playingMessage = replacePlaceholders(player, playingMessage);

      if (!playingMessage.isEmpty() && plugin.getConfig().getBoolean("options.printSongTitel")) {
        player.sendMessage(plugin.prefix + playingMessage.replaceAll("&", "§"));
      }
    } catch (IllegalArgumentException e) {
      plugin.log.warning(plugin.cprefix + "No sounds detected");
    }
  }

  public static void createRandomFileDir(Main plugin) {
    File dir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername"));
    if (!dir.exists()) {
      dir.mkdirs();
      File songFile = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      File copyDir = new File(
          plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername") + "/"
              + plugin.getConfig().getString("music"));
      try {
        Files.copy(songFile, copyDir);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static String replacePlaceholders(Player p, String string){
    SongPlayer sp = Music.playingSong.get(p.getUniqueId());
    string = string.replaceAll("%song%",sp.getSong().getTitle().isEmpty() ? "Untitled" : sp.getSong().getTitle());
    if(Main.placeholderProvided){
      string = PlaceholderAPI.setPlaceholders(p, string);
    }
    return string;
  }

}
