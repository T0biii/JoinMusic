package de.t0biii.joinmusic.spigot.domain;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.t0biii.joinmusic.spigot.main.Main;

public class ConfigManager {

  private Main plugin;
  
  private FileConfiguration userConfigs = null;

  public ConfigManager(Main plugin) {
    this.plugin = plugin;
  }

  public void loadConfig() {
    this.plugin.getConfig().options().header("Change at your own risk\nOnly the messages with color codes here support color codes. The rest have a default color");
    this.plugin.getConfig().addDefault("music", "Song.nbs");
    this.plugin.getConfig().addDefault("options.music.random", false);
    this.plugin.getConfig().addDefault("options.music.RandomFoldername", "random");
    this.plugin.getConfig().addDefault("options.music.Mode", "MonoMode");
    this.plugin.getConfig().addDefault("options.music.10Octave", false);
    this.plugin.getConfig().addDefault("options.update-check", true);
    this.plugin.getConfig().addDefault("options.updateinfo", true);
    this.plugin.getConfig().addDefault("options.printSongTitel", true);
    this.plugin.getConfig().addDefault("options.delaySong", 2);
    this.plugin.getConfig().addDefault("options.allowDisabling", true);
    this.plugin.getConfig().addDefault("options.metrics", true);
    this.plugin.getConfig().addDefault("options.bungeecord", false);
    this.plugin.getConfig().addDefault("messages.prefix", "&7[&bJoinMusic&7]");
    this.plugin.getConfig().addDefault("messages.reload", "&3The reload was successful!");
    this.plugin.getConfig().addDefault("messages.stop", "&3Stopped playing the song! &7&oYou can disable playing a song on join with &b&o/jm disable");
    this.plugin.getConfig().addDefault("messages.no-permission", "&cYou don't have enough permissions");
    this.plugin.getConfig().addDefault("messages.disabled","&3Disabled playing a song when joining. To enable it again, use &b/jm enable");
    this.plugin.getConfig().addDefault("messages.enabled","&3Enabled playing a song when joining");
    this.plugin.getConfig().addDefault("messages.playing", "&2Started Playing the Song: &a&l%song%&2. You can stop it using &a/jm stop");
    this.plugin.getConfig().addDefault("messages.help.stop", "Stop playing the Song!");
    this.plugin.getConfig().addDefault("messages.help.disableOwn", "Disable playing a song when joining");
    this.plugin.getConfig().addDefault("messages.help.enableOwn", "Enable playing a song when joining");

    this.plugin.getConfig().options().copyDefaults(true);
    this.plugin.prefix = this.plugin.getConfig().getString("messages.prefix").replaceAll("&", "§") + "§r ";
  }

  public void reloadConfigs() {
	  plugin.reloadConfig();
	  loadConfig();
	  plugin.saveConfig();
	  loadUserConfigsIfNeeded();
  }
  
  private void createUserConfigs() {
	  File userConfigsFile = new File(plugin.getDataFolder(), "disabledPlayers.yml");
    if (!userConfigsFile.exists()) {
      userConfigsFile.getParentFile().mkdirs();
      plugin.saveResource("disabledPlayers.yml", false);
    }
	  this.userConfigs = new YamlConfiguration();
      try {
        this.userConfigs.load(userConfigsFile);
    } catch (IOException | InvalidConfigurationException e) {
        e.printStackTrace();
    }
  }
  
  public FileConfiguration getUserConfigs() {
	return this.userConfigs;
  }
  
  public void loadUserConfigsIfNeeded() {
	if (plugin.getConfig().getBoolean("options.allowDisabling")) {
	  createUserConfigs();
	}
  }
  
  public void saveUserConfigs() {
	try {
	  this.userConfigs.save(new File(plugin.getDataFolder(), "disabledPlayers.yml"));
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}
