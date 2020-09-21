package de.t0biii.joinmusic.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public final class JoinMusicBungee extends Plugin  {

    ConfigManager cm = new ConfigManager(this);

    @Override
    public void onEnable() {
        getProxy().registerChannel( "JoinMusic" );
        getProxy().getPluginManager().registerListener(this, new PlayerJoin(this));

        if(cm.getConfig().getBoolean("metrics")){
            int pluginId = 8760;
            Metrics metrics = new Metrics(this, pluginId);
        }

        getLogger().info( "Plugin enabled!" );

    }

    @Override
    public void onDisable() {
        getLogger().info( "Plugin disabled!" );
    }

}
