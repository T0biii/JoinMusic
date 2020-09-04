package de.t0biii.music.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class HANDLER_Bungee implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("t0biii:joinmusic")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("Join")) {
            System.out.println("[JoinMusic] - Received Join Message");
        }else if (subchannel.equals("Disconnect")){
            System.out.println("[JoinMusic] - Received Disconnect Message");
        }else{
            System.out.println("[JoinMusic] - Received Message from:" + subchannel);
        }
    }
}
