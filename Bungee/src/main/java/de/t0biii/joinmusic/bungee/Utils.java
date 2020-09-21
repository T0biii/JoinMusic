package de.t0biii.joinmusic.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class Utils {

    public static void sendCustomData(ProxiedPlayer player, String channel , String data1) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        // perform a check to see if globally are no players
        if ( networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( channel );
        out.writeUTF( data1 );
        player.getServer().getInfo().sendData( "t0biii:joinmusic", out.toByteArray() );
    }


}
