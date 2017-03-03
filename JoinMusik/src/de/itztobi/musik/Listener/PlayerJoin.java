package de.itztobi.musik.Listener;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import de.itztobi.musik.Main;
public class PlayerJoin implements Listener{
	
	  private Main plugin;
	  public PlayerJoin(Main plugin){
		  this.plugin = plugin;
	  }
	  
	 public ArrayList<String> player = new ArrayList<String>();
	 private int a;
	   
	  
	  
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	final Player p = e.getPlayer();
	
	
	a = this.plugin.getConfig().getInt("options.limit.time");	
	Song s = NBSDecoder.parse(new File( plugin.getDataFolder()+"/" + plugin.getConfig().getString("musik")));		

			
			
		if(p.hasPermission("JoinMusik.use") || p.isOp() ){			
		
				
				
			final SongPlayer sp = new RadioSongPlayer(s);
			
			if(player.contains(p.getName().toLowerCase())){
			player.remove(p.getName().toLowerCase());
			sp.removePlayer(p);	
			sp.setPlaying(false);
			}else if(!player.contains(p.getName().toLowerCase())){
				player.add(p.getName().toLowerCase());
				//sp.setAutoDestroy(true);
				sp.addPlayer(p);
				sp.setPlaying(true);
				p.sendMessage(this.plugin.prefix+ "§2Start Playing the Song:§1 "+ sp.getSong().getTitle());
			   }	
				
			
			if(this.plugin.getConfig().getBoolean("options.limit.on")){
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
				@Override
				public void run() {
					if(plugin.getConfig().getBoolean("options.limit.on")){
						sp.setPlaying(false);
						sp.removePlayer(p);
					   // sp.destroy();
					}	
				}
			}, 20L*a);
		         	}
	
	

			
			
		}
	      }
	   @EventHandler
	   public void onJoinn(PlayerJoinEvent e){
		   Player p = e.getPlayer();
			if(Main.update){
			if(p.isOp() || p.hasPermission("JoinMusik.update"))
			{
				if(plugin.getConfig().getBoolean("options.updateinfo"))
				{
				p.sendMessage(this.plugin.prefix + "An update is available: " + Main.name + ", a " + Main.type + " for " + Main.version + " do /jm update");
				
				}
			}
		}
	   }
	  }
	   
	   
	   


