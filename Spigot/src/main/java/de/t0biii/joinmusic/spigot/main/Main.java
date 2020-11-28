package de.t0biii.joinmusic.spigot.main;

import java.util.logging.Logger;

import de.t0biii.joinmusic.spigot.commands.CMD_PlayMusic;
import de.t0biii.joinmusic.spigot.domain.*;
import de.t0biii.joinmusic.spigot.listener.HANDLER_Bungee;
import de.t0biii.joinmusic.spigot.listener.HANDLER_PlayerJoin;
import de.t0biii.joinmusic.spigot.listener.HANDLER_PlayerQuit;
import de.t0biii.joinmusic.spigot.listener.HANDLER_SongEndEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

  public boolean update = false;
  public static boolean placeholderProvided = false;
  public String noteblockAPIVersion = "";
  public String placeholderAPIVersion = "";
  public String name = "";
  public Updater.ReleaseType type = null;
  public Main instance;
  public String version = "";
  public String link = "";
  public String link2 = "http://dev.bukkit.org/bukkit-plugins/joinmusic/";
  private int uid = 83541;
  public Updater updater;

  public ConfigManager cm = new ConfigManager(this);
  public Logger log = Bukkit.getLogger();
  PluginManager pm = Bukkit.getPluginManager();
  public String cprefix = "[JoinMusic] ";
  public String prefix = "§7[§bJoinMusic§7]§r ";

  @Override
  public void onEnable() {
    if (pm.isPluginEnabled("NoteBlockAPI")) {
      noteblockAPIVersion = pm.getPlugin("NoteBlockAPI").getDescription().getVersion();
      log.info(cprefix + "Successfully hooked into: NoteBlockAPI");
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
    pm.registerEvents(new HANDLER_SongEndEvent(), this);
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
      // Start Updater but just do a version check
      updater = new Updater(this, uid, Updater.UpdateType.NO_DOWNLOAD);
      // Determine if there is an update ready for us
      update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
      name = updater.getLatestName(); // Get the latest name
      version = updater.getLatestGameVersion(); // Get the latest game version
      type = updater.getLatestType(); // Get the latest file's type
      link = updater.getLatestFileLink(); // Get the latest link
      if (update) {
        log.info(cprefix + "New version available! " + name);
        log.info(cprefix + "Download at: " + link2);
      }
    }
  }
  
}
