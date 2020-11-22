package de.t0biii.joinmusic.bungee.listener;

import de.t0biii.joinmusic.bungee.JoinMusicBungee;
import de.t0biii.joinmusic.bungee.domain.Utils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PlayerJoin implements Listener {

    JoinMusicBungee plugin;
    public PlayerJoin(JoinMusicBungee joinMusicBungee) {
        this.plugin = joinMusicBungee;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {

        ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                Utils.sendCustomData(plugin, event.getPlayer(), "Join", event.getPlayer().getName(), true);
            }
        }, plugin.dealy, TimeUnit.SECONDS);

    }

}
