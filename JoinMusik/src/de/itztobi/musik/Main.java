package de.itztobi.musik;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.itztobi.musik.Methods.Updater;
import de.itztobi.musik.Methods.Updater.ReleaseType;
import de.itztobi.musik.commands.PlayMusic;
import de.itztobi.musik.Listener.PlayerJoin;;


public class Main extends JavaPlugin{
	
	
	
	public static boolean update = false;
	public static String name = "";
	public static ReleaseType type = null;
	public static Main instance;
	public static String version = "";
	public static String link = "";
	public static String link2 = "http://dev.bukkit.org/bukkit-plugins/joinmusik/";

   public ConfigManager cm = new ConfigManager(this);
   Logger log = Bukkit.getLogger();
   public String cprefix = "[JoinMusik] ";
   public String prefix = "§7[§bJoinMusik§7]§r ";

	@Override
	public void onEnable() {
	   
	    cm.loadConfig();
	    saveConfig();
	    Updater();
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		this.getCommand("JoinMusik").setExecutor(new PlayMusic(this));
		log.info(cprefix+"Enabled");


       if (pm.getPlugin("NoteBlockAPI").isEnabled()) {
            log.info(cprefix +"The plugin NoteBlockAPI was found successfully");  }
       else {
            log.info(cprefix+"The plugin NoteBlockAPI could not be found!"); 
            pm.disablePlugins();
		    log.info(cprefix+"Disabled");
		    
       }
	}
	
    public void Updater()
    {
   	if(this.getConfig().getBoolean("options.update-check"))
   	{
      	  Updater updater = new Updater(this, 83541, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check
      	  update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
      	  name = updater.getLatestName(); // Get the latest name
      	  version = updater.getLatestGameVersion(); // Get the latest game version
      	  type = updater.getLatestType(); // Get the latest file's type
      	  link = updater.getLatestFileLink(); // Get the latest link
      	  if(update)
      	  {
      		  log.info(prefix + "New version available! " + name);
      		  log.info(prefix + "Download at " + link2 +" or go InGame");  	
      	  }
      	}
    }
    
    public void froce()
    {
       	 @SuppressWarnings("unused")
		Updater updater = new Updater(this, 83541, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true); // Go straight to downloading, and announce progress to console.
    }
	
	
	

}
