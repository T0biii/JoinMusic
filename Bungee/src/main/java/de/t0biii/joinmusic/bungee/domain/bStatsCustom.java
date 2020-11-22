package de.t0biii.joinmusic.bungee.domain;


import de.t0biii.joinmusic.bungee.JoinMusicBungee;
import org.bstats.bungeecord.Metrics;

import java.util.concurrent.Callable;

public class bStatsCustom {
    private JoinMusicBungee plugin;

    public bStatsCustom(JoinMusicBungee plugin) {
        this.plugin = plugin;
    }

    public void customCharts(Metrics bstats) {
        String delay = plugin.cm.getConfig().getString("delay");

        bstats.addCustomChart(new Metrics.SimplePie("options_delay", new Callable<String>() {
            @Override
            public String call()  {
                return delay;
            }
        }));

    }
}
