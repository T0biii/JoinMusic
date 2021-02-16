package de.t0biii.joinmusic.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import de.t0biii.joinmusic.velocity.listener.PlayerJoin;
import org.slf4j.Logger;

@Plugin(
        id = "joinmusicvelocity",
        name = "JoinMusic-Velocity",
        version = "1.0-SNAPSHOT",
        description = "Joinmusic Velocity Bridge",
        authors = {"T0biii"}
)
public class JoinMusicVelocity {

    public final ProxyServer server;
    private final Logger logger;

    @Inject
    public JoinMusicVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;



        server.getChannelRegistrar().register();


        logger.info("Hello there! I made my first plugin with Velocity.");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
        server.getEventManager().register(this, new PlayerJoin(this));
    }


}
