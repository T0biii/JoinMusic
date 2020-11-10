package de.t0biii.joinmusic.spigot.domain;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlaceholderCustom extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "JoinMusic";
    }

    @Override
    public @NotNull String getAuthor() {
        return "T0biii";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }


    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        UUID uuid = player.getUniqueId();
        SongPlayer sp;
        if(Music.playingSong.containsKey(uuid)){
           sp = Music.playingSong.get(player.getUniqueId());
        }else{
            return null;
        }

        switch (identifier){
            // %joinmusic_titel%
            // %joinmusic_song%
            case "titel":
            case "song":
                return sp.getSong().getTitle().isEmpty() ? "Untitled" : sp.getSong().getTitle();
            // %joinmusic_author%
            case "author":
                return sp.getSong().getAuthor().isEmpty() ? "Unknown" : sp.getSong().getAuthor();
            // %joinmusic_originalauthor%
            case "originalauthor":
                return sp.getSong().getOriginalAuthor().isEmpty() ? "Unknown" : sp.getSong().getOriginalAuthor();
            // %joinmusic_description%
            case "description":
                return  sp.getSong().getDescription().isEmpty() ? "Unknown" : sp.getSong().getDescription();
        }


        // We return null if an invalid placeholder was provided
        return null;
    }

}
