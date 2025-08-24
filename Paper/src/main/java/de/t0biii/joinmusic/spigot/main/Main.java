package de.t0biii.joinmusic.spigot.main;

import java.util.logging.Logger;

import de.t0biii.joinmusic.spigot.commands.CMD_PlayMusic;
import de.t0biii.joinmusic.spigot.domain.*;
import de.t0biii.joinmusic.spigot.listener.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

  public boolean UPDATE_AVAILABLE = false;
  public static boolean placeholderProvided = false;
  public String noteblockAPIVersion = "";
  public String placeholderAPIVersion = "";
  final private String author = "T0biii";
  final public String name = "JoinMusic";
  public String version = "";
  public String link = "";

  public ConfigManager cm = new ConfigManager(this);
  public Logger log = Bukkit.getLogger();
  PluginManager pm = Bukkit.getPluginManager();
  public String cprefix = "[JoinMusic] ";
  public String prefix;

  @Override
  public void onEnable() {
    if (pm.isPluginEnabled("NoteBlockAPI")) {
      noteblockAPIVersion = pm.getPlugin("NoteBlockAPI").getDescription().getVersion();
      if(noteblockAPIVersion.equalsIgnoreCase("1.6.1-SNAPSHOT")){
        log.warning(cprefix + "This Plugin dont work with NoteblockAPI Version 1.6.1");
        log.warning(cprefix + "Use Version 1.6.1.1 or newer");
        pm.disablePlugin(this);
        return;
      }
    } else {
      log.info(cprefix + "The plugin NoteBlockAPI could not be found!");
      pm.disablePlugin(this);
      log.info(cprefix + "Disabled!");
      return;
    }


    if (pm.isPluginEnabled("PlaceholderAPI")) {
      new PlaceholderCustom().register();
      placeholderProvided = true;
      placeholderAPIVersion = pm.getPlugin("PlaceholderAPI").getDescription().getVersion();
      log.info(cprefix + "Successfully hooked into: PlaceholderAPI");
    }

    cm.loadConfig();
    saveConfig();
    cm.loadUserConfigsIfNeeded();

    Updater();
    Music.createRandomFileDir(this);
    Music.preloadSongs(this);

    if (this.getConfig().getBoolean("options.metrics")) {
      Metrics metrics = new Metrics(this, 6447);
      new bStatsCustom(this).customCharts(metrics);
    }
    if(this.getConfig().getBoolean("options.bungeecord")){
     // this.getServer().getMessenger().registerOutgoingPluginChannel(this, "t0biii:joinmusic");
      this.getServer().getMessenger().registerIncomingPluginChannel(this, "t0biii:joinmusic", new HANDLER_Bungee(this));
    }

    pm.registerEvents(new HANDLER_PlayerJoin(this), this);
    pm.registerEvents(new HANDLER_PlayerQuit(), this);
    pm.registerEvents(new HANDLER_PlayerChangeWorld(this), this);
    pm.registerEvents(new HANDLER_SongEndEvent(this), this);
    getCommand("JoinMusic").setExecutor(new CMD_PlayMusic(this));
    getCommand("JoinMusic").setTabCompleter(CMD_PlayMusic.tabCompleter);

    if(this.getConfig().getBoolean("options.bungeecord")){
      log.info(cprefix + "Enabled in BungeeMode!");
    }else{
      log.info(cprefix + "Enabled!");
    }
  }

  public void Updater() {
    if (this.getConfig().getBoolean("options.update-check")) {
      version = this.getDescription().getVersion();
      UpdateChecker updater = new UpdateChecker(author, name, version);
      updater.checkAsync();
      UPDATE_AVAILABLE = updater.isUpdateAvailable();

      if (UPDATE_AVAILABLE) {
        version = updater.getLatestVersion();
        link = updater.getUpdateUrl();
        log.info(cprefix + "New version available for JoinMusic! " + version);
        log.info(cprefix + "Download at: " + link);
      }
    }
  }
  
}
