package de.t0biii.musik.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.t0biii.musik.JoinMusic;

public class PlayMusic implements CommandExecutor{

	private JoinMusic plugin;
	public PlayMusic(JoinMusic plugin){ this.plugin = plugin;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
            if(args.length == 1){
            	if(args[0].equalsIgnoreCase("reload")){
            		try{
            			this.plugin.reloadConfig();
            			p.sendMessage("§3"+this.plugin.getConfig().getString("messages.reload"));
            		}catch(Exception e){
            			p.sendMessage("§cReload failed!");
            		}	
            	}
            }else{
            	p.sendMessage("§7======= §aJoinMusik §7=======");
                p.sendMessage("§7/jm §areload  §7| §aConfig Reload!");
                p.sendMessage("§7======= §aJoinMusik §7=======");
            }
		}else{ sender.sendMessage(this.plugin.cprefix+"use your PC!"); }
		return false;
	}
}