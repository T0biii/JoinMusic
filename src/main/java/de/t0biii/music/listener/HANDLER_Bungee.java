package de.t0biii.music.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.t0biii.music.domain.Music;
import de.t0biii.music.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class HANDLER_Bungee implements PluginMessageListener {

    Main plugin;

    public HANDLER_Bungee(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("t0biii:joinmusic")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("Join")) {
            Music.start(player, plugin);
        }else{
            plugin.log.info(plugin.cprefix + " - Received Message from:" + subchannel);
        }
    }
}
