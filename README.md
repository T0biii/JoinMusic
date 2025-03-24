# JoinMusic

[![Build Status](https://github.com/T0biii/JoinMusic/workflows/Java%20CI/badge.svg)](https://github.com/T0biii/JoinMusic/actions)
[![Spigot Version](https://img.shields.io/badge/Spigot-1.8--1.21-orange.svg)](https://www.spigotmc.org/resources/joinmusic.83541/)
[![bStats Servers](https://img.shields.io/bstats/servers/6447)](https://bstats.org/plugin/bukkit/JoinMusic/6447)
[![GitHub license](https://img.shields.io/github/license/T0biii/JoinMusic)](https://github.com/T0biii/JoinMusic/blob/master/LICENSE)
[![Discord](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/qKskYDBAMW)
## Beschreibung
JoinMusic ist ein Bukkit/Spigot-Plugin, das Musik abspielt, wenn ein Spieler den Server betritt. Es unterstützt sowohl Standalone-Spigot-Server als auch BungeeCord-Netzwerke.

## Features
- Spielt Musik ab, wenn ein Spieler den Server betritt
- Unterstützt BungeeCord-Netzwerke
- Konfigurierbare Verzögerung vor dem Abspielen der Musik
- Optionales Looping der Musik
- Weltspezifische Musikwiedergabe
- PlaceholderAPI-Unterstützung
- Automatische Update-Benachrichtigungen

## Voraussetzungen
- Bukkit/Spigot 1.8 - 1.21
- [NoteBlockAPI](https://www.spigotmc.org/resources/noteblockapi.19287/) (Version 1.6.1.1 oder neuer)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)

## Installation
1. Lade die neueste Version von JoinMusic herunter
2. Lade NoteBlockAPI herunter und installiere es
3. Platziere beide JAR-Dateien in deinem Plugins-Ordner
4. Starte deinen Server neu
5. Konfiguriere das Plugin nach deinen Wünschen

### BungeeCord-Setup
Wenn du BungeeCord verwendest:
1. Installiere das JoinMusic-Plugin auf allen Spigot-Servern
2. Installiere das JoinMusic-Bungee-Plugin auf deinem BungeeCord-Server
3. Aktiviere die BungeeCord-Option in der Konfiguration aller Spigot-Server

## Konfiguration
Die Hauptkonfigurationsdatei befindet sich unter `plugins/JoinMusic/config.yml`:

```yaml
# Hauptmusikdatei oder Ordner
music: Song.nbs

options:
  # Update-Einstellungen
  update-check: true        # Prüft auf Updates beim Serverstart
  updateinfo: true          # Zeigt Update-Benachrichtigungen für OPs
  
  # Allgemeine Einstellungen
  metrics: true             # Sendet anonyme Nutzungsstatistiken
  delaySong: 2              # Verzögerung in Sekunden vor dem Abspielen
  bungeecord: false         # BungeeCord-Modus aktivieren/deaktivieren
  printSongTitel: true      # Zeigt den Songtitel beim Abspielen an
  allowDisabling: true      # Erlaubt Spielern, die Musik für sich zu deaktivieren
  
  # Musik-Einstellungen
  music:
    random: false           # Zufällige Musikauswahl aktivieren
    RandomFoldername: "random" # Ordner für zufällige Musikauswahl
    Mode: "MonoMode"        # Wiedergabemodus (MonoMode/StereoMode)
    10Octave: false         # 10 Oktaven aktivieren
    Volume: 100             # Lautstärke (0-100)
    AllowLooping: false     # Musik in Schleife abspielen
    OneWorldonly: false     # Musik nur in einer bestimmten Welt abspielen
    Worldname: "world"      # Name der Welt, wenn OneWorldonly aktiviert ist

# Nachrichten-Einstellungen
messages:
  prefix: "&7[&bJoinMusic&7]"
  reload: "&3The reload was successful!"
  stop: "&3Stopped playing the song! &7&oYou can disable playing a song on join with &b&o/jm disable"
  no-permission: "&cYou don't have enough permissions"
  disabled: "&3Disabled playing a song when joining. To enable it again, use &b/jm enable"
  enabled: "&3Enabled playing a song when joining"
  playing: "&2Started Playing the Song: &a&l%song%&2. You can stop it using &a/jm stop"
  help:
    stop: "Stop playing the Song!"
    disableOwn: "Disable playing a song when joining"
    enableOwn: "Enable playing a song when joining"
```

## Befehle
- `/joinmusic` oder `/jm` - Zeigt Hilfe an
- `/joinmusic play [Spieler]` - Spielt die Musik für dich oder einen anderen Spieler ab
- `/joinmusic stop [Spieler]` - Stoppt die Musik für dich oder einen anderen Spieler
- `/joinmusic reload` - Lädt die Konfiguration neu

## Berechtigungen
- `JoinMusic.use` - Erlaubt die Verwendung des /joinmusic-Befehls
- `JoinMusic.play` - Erlaubt die Verwendung von /joinmusic play
- `JoinMusic.stop` - Erlaubt die Verwendung von /joinmusic stop
- `JoinMusic.reload` - Erlaubt die Verwendung von /joinmusic reload
- `JoinMusic.update` - Erhält Update-Benachrichtigungen
- `JoinMusic.play.other` - Erlaubt das Abspielen von Musik für andere Spieler
- `JoinMusic.stop.other` - Erlaubt das Stoppen von Musik für andere Spieler

## PlaceholderAPI
JoinMusic unterstützt die folgenden PlaceholderAPI-Platzhalter:
- `%joinmusic_version%` - Zeigt die aktuelle Version des Plugins an
- `%joinmusic_titel%` oder `%joinmusic_song%` - Gibt den Titel des aktuell spielenden Songs zurück
- `%joinmusic_author%` - Gibt den Autor des aktuell spielenden Songs zurück
- `%joinmusic_originalauthor%` - Gibt den Originalautor des aktuell spielenden Songs zurück
- `%joinmusic_description%` - Gibt die Beschreibung des aktuell spielenden Songs zurück
- `%joinmusic_prefix%` - Gibt den Plugin-Präfix zurück

## Lizenz
Dieses Projekt steht unter der [MIT-Lizenz](LICENSE).

## Links
- [Bukkit](https://dev.bukkit.org/projects/joinmusik)
- [Spigot](https://www.spigotmc.org/resources/joinmusic.83541/)
- [Hanger](https://hangar.papermc.io/T0biii/JoinMusic)
- [Modrinth](https://modrinth.com/plugin/joinmusic)
- [Issue Tracker](https://github.com/T0biii/JoinMusic/issues)
- [bStats](https://bstats.org/plugin/bukkit/JoinMusic/6447)
