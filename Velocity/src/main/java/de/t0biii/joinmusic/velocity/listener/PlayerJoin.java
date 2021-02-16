package de.t0biii.joinmusic.velocity.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.t0biii.joinmusic.velocity.JoinMusicVelocity;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class PlayerJoin  {

    JoinMusicVelocity plugin;
    public PlayerJoin(JoinMusicVelocity joinMusicVelocity) {
        this.plugin = joinMusicVelocity;
    }

    @Subscribe(order = PostOrder.LAST)
    public void onPlayerChat(ServerPostConnectEvent event) {

    }




    public static void sendCustomData(JoinMusicVelocity plugin, Player player, String channel , String data1, Boolean firstrun) {
        Collection<Player> networkPlayers = plugin.server.getAllPlayers();
        // perform a check to see if globally are no players
        if ( networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( channel );
        out.writeUTF( data1 );
        try{
            plugin.server.getChannelRegistrar().register();
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
