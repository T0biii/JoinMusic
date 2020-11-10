package de.t0biii.joinmusic.spigot.domain;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.StereoMode;
import org.bukkit.entity.Player;
import com.google.common.io.Files;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.t0biii.joinmusic.spigot.main.Main;

public class Music {

  public static HashMap<UUID, SongPlayer> playingSong = new HashMap<>();

  public static void start(Player player, Main plugin){
	if(plugin.getConfig().getBoolean("options.allowDisabling")) {
		if(plugin.cm.getUserConfigs().getBoolean(player.getUniqueId().toString(),false)) {
			return;
		}
	}
    play(player, plugin);
  }

  public static void stop(Player player) {
    removePlayer(player.getUniqueId());
  }

  public static void stop(UUID uuid) {
    removePlayer(uuid);
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
      File songFile;
      if (plugin.getConfig().getBoolean("options.music.random")) {
        songFile = SelectRandomFileFromFolder(plugin);
      } else {
        songFile = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      }
      Song s = NBSDecoder.parse(songFile);

      final RadioSongPlayer sp = new RadioSongPlayer(s);
      sp.addPlayer(player);
      sp.setPlaying(true);
      if(plugin.getConfig().getBoolean("options.music.10Octave")){
        sp.setEnable10Octave(true);
      }
      String mode = plugin.getConfig().getString("options.music.Mode");
      if(mode.equalsIgnoreCase("MonoMode")){
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
      System.err.println(plugin.cprefix + "No sounds detected");
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
      }
    }
  }

  private static File SelectRandomFileFromFolder(Main plugin) {
    createRandomFileDir(plugin);
    File dir = new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("options.music.RandomFoldername"));
    if (dir.exists()) {
      File[] files = dir.listFiles();
      if (files.length > 0) {
        Random rand = new Random();
        return files[rand.nextInt(files.length)];
      } else {
        return new File(plugin.getDataFolder() + "/" + plugin.getConfig().getString("music"));
      }
    }
    return null;
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
