package de.t0biii.joinmusic.bungee.domain;


import de.t0biii.joinmusic.bungee.JoinMusicBungee;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private JoinMusicBungee plugin;
    public ConfigManager(JoinMusicBungee plugin){
        this.plugin = plugin;
    }

    private void load(){
        try{
            File file = getFile();
            Configuration config = getConfig(file);
            config.set("metrics", true);
            config.set("delay", 3);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Configuration getConfig() {
        try {
            return getConfig(getFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Configuration getConfig (File file) throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    private File getFile () throws IOException {
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        File file = new File(plugin.getDataFolder().getPath(), "config.yml");
        if(!file.exists()){
            file.createNewFile();
            load();
        }
        return file;
    }


}
