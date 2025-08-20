package de.t0biii.joinmusic.spigot.domain;

import java.util.concurrent.Callable;

import de.t0biii.joinmusic.spigot.main.Main;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public class bStatsCustom {
    private Main plugin;

    public bStatsCustom(Main plugin) {
        this.plugin = plugin;
    }

    public void customCharts(Metrics bstats) {
        String random = plugin.getConfig().getString("options.music.random");
        String updateCheck = plugin.getConfig().getString("options.update-check");
        String updateinfo = plugin.getConfig().getString("options.updateinfo");
        String printSongTitel = plugin.getConfig().getString("options.printSongTitel");
        String allowDisabling = plugin.getConfig().getString("options.allowDisabling");
        String delaySong = plugin.getConfig().getString("options.delaySong");
        String octave10  = plugin.getConfig().getString("options.music.10Octave");
        String mode = plugin.getConfig().getString("options.music.Mode");
        String bungeemode = plugin.getConfig().getString("options.bungeecord");
        String noteblockAPIVersion = plugin.noteblockAPIVersion;
        String placeholderAPIVersion = plugin.placeholderAPIVersion;

        bstats.addCustomChart(new SimplePie("options_music_random", new Callable<String>() {
            @Override
            public String call()  {
                if (random.equalsIgnoreCase("true") || random.equalsIgnoreCase("false")) {
                    return random;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_update-check", new Callable<String>() {
            @Override
            public String call()  {
                if (updateCheck.equalsIgnoreCase("true") || updateCheck.equalsIgnoreCase("false")) {
                    return updateCheck;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_updateinfo", new Callable<String>() {
            @Override
            public String call()  {
                if (updateinfo.equalsIgnoreCase("true") || updateinfo.equalsIgnoreCase("false")) {
                    return updateCheck;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_printSongTitel", new Callable<String>() {
            @Override
            public String call()  {
                if (printSongTitel.equalsIgnoreCase("true") || printSongTitel.equalsIgnoreCase("false")) {
                    return printSongTitel;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_delaySong", new Callable<String>() {
            @Override
            public String call()  {
                if (delaySong != null) {
                    return delaySong;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_allowDisabling", new Callable<String>() {
            @Override
            public String call()  {
                if (allowDisabling.equalsIgnoreCase("true") || allowDisabling.equalsIgnoreCase("false")) {
                    return allowDisabling;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_music_10Octave", new Callable<String>() {
            @Override
            public String call()  {
                if (octave10.equalsIgnoreCase("true") || octave10.equalsIgnoreCase("false")) {
                    return octave10;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_music_Mode", new Callable<String>() {
            @Override
            public String call()  {
                if (mode.equalsIgnoreCase("MonoMode") || mode.equalsIgnoreCase("MonoStereoMode") || mode.equalsIgnoreCase("StereoMode")) {
                    return mode;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("options_bungeecord", new Callable<String>() {
            @Override
            public String call()  {
                if (bungeemode.equalsIgnoreCase("true") || bungeemode.equalsIgnoreCase("false")) {
                    return bungeemode;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("version_noteblockapi", new Callable<String>() {
            @Override
            public String call()  {
                if (!noteblockAPIVersion.isEmpty()) {
                    return noteblockAPIVersion;
                }
                return "unknow";
            }
        }));

        bstats.addCustomChart(new SimplePie("version_placeholderAPI", new Callable<String>() {
            @Override
            public String call()  {
                if (!placeholderAPIVersion.isEmpty()) {
                    return placeholderAPIVersion;
                }
                return "unknow";
            }
        }));

    }
}
