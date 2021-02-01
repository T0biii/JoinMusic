package de.t0biii.joinmusic.bungee;

import de.t0biii.joinmusic.bungee.domain.ConfigManager;
import de.t0biii.joinmusic.bungee.domain.bStatsCustom;
import de.t0biii.joinmusic.bungee.listener.PlayerJoin;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public final class JoinMusicBungee extends Plugin  {

    public ConfigManager cm = new ConfigManager(this);
    public Integer dealy = 3;

    @Override
    public void onEnable() {
        getProxy().registerChannel( "JoinMusic" );
        getProxy().getPluginManager().registerListener(this, new PlayerJoin(this));
        dealy = cm.getConfig().getInt("delay");

        if(cm.getConfig().getBoolean("metrics")){
            Metrics metrics = new Metrics(this, 8760);
            new bStatsCustom(this).customCharts(metrics);
        }

        getLogger().info( "Plugin enabled!" );
    }

    @Override
    public void onDisable() {
        getLogger().info( "Plugin disabled!" );
    }

}
