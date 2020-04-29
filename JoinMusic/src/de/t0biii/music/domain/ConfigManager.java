package de.t0biii.music.domain;

import de.t0biii.music.main.Main;

public class ConfigManager {

  private Main plugin;

  public ConfigManager(Main plugin) {
    this.plugin = plugin;
  }

  public void loadConfig() {
    this.plugin.getConfig().options().header("Plugin by itzTobi_!\nChange at your own risk");
    this.plugin.getConfig().addDefault("music", "Song.nbs");
    this.plugin.getConfig().addDefault("options.update-check", true);
    this.plugin.getConfig().addDefault("options.updateinfo", true);
    this.plugin.getConfig().addDefault("options.printSongTitel", true);
    this.plugin.getConfig().addDefault("options.delaySong", 2);
    this.plugin.getConfig().addDefault("messages.reload", "The reload was successful!");
    this.plugin.getConfig().addDefault("messages.stop", "The song was stopped!");
    this.plugin.getConfig().addDefault("messages.no-permission",
        "You don't have enough permissions");
    this.plugin.getConfig().addDefault("options.metrics", true);

    this.plugin.getConfig().options().copyDefaults(true);
  }
}
