package de.t0biii.musik.Listener;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import de.t0biii.musik.JoinMusic;
import de.t0biii.musik.Methods.Updater;
public class PlayerJoin implements Listener{
	
	
	private JoinMusic plugin;
	public PlayerJoin(JoinMusic plugin){ this.plugin = plugin;}
	public ArrayList<String> player = new ArrayList<String>();
	private int time;
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {	
		final Player p = e.getPlayer();
		time = this.plugin.getConfig().getInt("options.limit.time");			
		Song s = NBSDecoder.parse(new File( plugin.getDataFolder()+"/" + plugin.getConfig().getString("musik")));  
		final SongPlayer sp = new RadioSongPlayer(s);
		if(p.hasPermission("JoinMusik.use") || p.isOp() ){				
				
			if(!player.contains(p.getName())){
				player.add(p.getName());
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
				}, 20L*time);
			}
		}
		if(JoinMusic.update){
			if((p.isOp() || p.hasPermission("JoinMusik.update")) && plugin.getConfig().getBoolean("options.updateinfo")){
				if(this.plugin.updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE)
				p.sendMessage(this.plugin.prefix + "An update is available: " + JoinMusic.name + ", a " + JoinMusic.type + " for " + JoinMusic.version);
				p.sendMessage(this.plugin.prefix + "Download: " + this.plugin.link2);
			}
		}
	}
}