package de.t0biii.musik;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.t0biii.musik.Listener.PlayerJoin;
import de.t0biii.musik.Methods.Updater;
import de.t0biii.musik.Methods.Updater.ReleaseType;
import de.t0biii.musik.commands.PlayMusic;;


public class JoinMusic extends JavaPlugin{
	
	public static boolean update = false;
	public static String name = "";
	public static ReleaseType type = null;
	public static JoinMusic instance;
	public static String version = "";
	public static String link = "";
	public String link2 = "http://dev.bukkit.org/bukkit-plugins/joinmusik/";
	private int uid = 83541;
	public Updater updater;
	
	
    public ConfigManager cm = new ConfigManager(this);
    Logger log = Bukkit.getLogger();
    PluginManager pm = Bukkit.getPluginManager();
    public String cprefix = "[JoinMusik] ";
    public String prefix = "§7[§bJoinMusik§7]§r ";

	@Override
	public void onEnable() {
	   
	    cm.loadConfig();
	    saveConfig();
//	    Updater();
		pm.registerEvents(new PlayerJoin(this), this);
		this.getCommand("JoinMusik").setExecutor(new PlayMusic(this));
		

       if (pm.getPlugin("NoteBlockAPI").isEnabled()){
            log.info(cprefix +"Successfully hooked into: NoteBlockAPI");  
       }else{
            log.info(cprefix+"The plugin NoteBlockAPI could not be found!"); 
            pm.disablePlugin(this);
		    log.info(cprefix+"Disabled");  
       }
       log.info(cprefix+"Enabled");  
	}
	
    public void Updater(){
    	
    	updater = new Updater(this, uid, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
    	
//   	if(this.getConfig().getBoolean("options.update-check")){
//      	  Updater updater = new Updater(this, 83541, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check
//      	  update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
//      	  name = updater.getLatestName(); // Get the latest name
//      	  version = updater.getLatestGameVersion(); // Get the latest game version
//      	  type = updater.getLatestType(); // Get the latest file's type
//      	  link = updater.getLatestFileLink(); // Get the latest link
//      	  if(update){
//      		  log.info(prefix + "New version available! " + name);
//      		  log.info(prefix + "Download at " + link2);  	
//      	  }
//      	}
    }
//    public void froce()
//    {
//       	 @SuppressWarnings("unused")
//		Updater updater = new Updater(this, 83541, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true); // Go straight to downloading, and announce progress to console.
//    }
}