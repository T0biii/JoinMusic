package de.t0biii.joinmusic.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static void sendCustomData(JoinMusicBungee plugin, ProxiedPlayer player, String channel , String data1, Boolean firstrun) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        // perform a check to see if globally are no players
        if ( networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( channel );
        out.writeUTF( data1 );
        try{
            player.getServer().getInfo().sendData( "t0biii:joinmusic", out.toByteArray() );
        }catch (NullPointerException e){
            if(firstrun){
                ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                        sendCustomData(plugin, player, channel, data1, false);
                    }
                }, 10, TimeUnit.SECONDS);
            }else{
                plugin.getLogger().info("Error sending Music to Player:" + player);
            }
        }
    }
}
