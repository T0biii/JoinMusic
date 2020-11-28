package de.t0biii.joinmusic.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "joinmusicvelocity",
        name = "JoinMusic-Velocity",
        version = "1.0-SNAPSHOT",
        description = "Joinmusic Velocity Bridge",
        authors = {"T0biii"}
)
public class JoinMusicVelocity {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
