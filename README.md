# JoinMusic

[![Build Status](https://github.com/T0biii/JoinMusic/workflows/Java%20CI/badge.svg)](https://github.com/T0biii/JoinMusic/actions)
[![Spigot Version](https://img.shields.io/badge/Spigot-1.8--1.21-orange.svg)](https://www.spigotmc.org/resources/joinmusic.78169/)
[![bStats Servers](https://img.shields.io/bstats/servers/6447)](https://bstats.org/plugin/bukkit/JoinMusic/6447)
[![bStats Players](https://img.shields.io/bstats/players/6447)](https://bstats.org/plugin/bukkit/JoinMusik)
[![GitHub license](https://img.shields.io/github/license/T0biii/JoinMusic)](https://github.com/T0biii/JoinMusic/blob/master/LICENSE)
[![Discord](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/qKskYDBAMW)
## Description
JoinMusic is a Bukkit/Spigot plugin that plays music when a player joins the server. It supports both standalone Spigot servers and BungeeCord networks.

## Features
- Plays music when a player joins the server
- Supports BungeeCord networks
- Configurable delay before playing music
- Optional music looping
- World-specific music playback
- PlaceholderAPI support
- Automatic update notifications

## Requirements
- Bukkit/Spigot 1.8 - 1.21
- [NoteBlockAPI](https://www.spigotmc.org/resources/noteblockapi.19287/) (Version 1.6.1.1 or newer)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)

## Installation
1. Download the latest version of JoinMusic
2. Download and install NoteBlockAPI
3. Place both JAR files in your plugins folder
4. Restart your server
5. Configure the plugin to your liking

### BungeeCord Setup
If you're using BungeeCord:
1. Install the JoinMusic plugin on all Spigot servers
2. Install the JoinMusic Bungee plugin on your BungeeCord server
3. Enable the BungeeCord option in the configuration of all Spigot servers

## Configuration
The main configuration file is located at `plugins/JoinMusic/config.yml`:

```yaml
# Main music file or folder
music: Song.nbs

options:
  # Update settings
  update-check: true        # Checks for updates on server start
  updateinfo: true          # Shows update notifications for OPs
  
  # General settings
  metrics: true             # Sends anonymous usage statistics
  delaySong: 2              # Delay in seconds before playing
  bungeecord: false         # Enable/disable BungeeCord mode
  printSongTitel: true      # Shows the song title when playing
  allowDisabling: true      # Allows players to disable music for themselves
  
  # Music settings
  music:
    random: false           # Enable random music selection
    RandomFoldername: "random" # Folder for random music selection
    Mode: "MonoMode"        # Playback mode (MonoMode/StereoMode)
    10Octave: false         # Enable 10 octaves
    Volume: 100             # Volume (0-100)
    AllowLooping: false     # Play music in a loop
    OneWorldonly: false     # Play music only in a specific world
    Worldname: "world"      # Name of the world if OneWorldonly is enabled

# Message settings
messages:
  prefix: '&7[&bJoinMusic&7]'
  reload: '&3The reload was successful!'
  stop: '&3Stopped playing the song! &7&oYou can disable playing a song on join with
    &b&o/jm disable'
  no-permission: '&cYou don''t have enough permissions'
  disabled: '&3Disabled playing a song when joining. To enable it again, use &b/jm
    enable'
  enabled: '&3Enabled playing a song when joining'
  playing: '&2Started Playing the Song: &a&l%song%&2. You can stop it using &a/jm
    stop'
  help:
    stop: Stop playing the Song!
    disableOwn: Disable playing a song when joining
    enableOwn: Enable playing a song when joining
  skip: '&3Skipped to next random song'
```

## Commands
- `/joinmusic` or `/jm` - Shows help with available commands
- `/joinmusic reload` - Reloads the configuration
- `/joinmusic stop` - Stops the currently playing music
- `/joinmusic disable` - Disables automatic music playback when joining the server
- `/joinmusic enable` - Enables automatic music playback when joining the server
- `/joinmusic toggle` - Toggles (enables or disables) automatic music playback when joining the server
- `/joinmusic skip` - Skips the current song if looping is activ

## Permissions
- `JoinMusic.use` - Allows using the /joinmusic command
- `JoinMusic.play` - Allows using /joinmusic play
- `JoinMusic.command.stop` - Allows using /joinmusic stop
- `JoinMusic.command.reload` - Allows using /joinmusic reload
- `JoinMusic.command.disableOwn` - Allows using /joinmusic disable/enable/toggle
- `JoinMusic.update` - Receives update notifications

## PlaceholderAPI
JoinMusic supports the following PlaceholderAPI placeholders:
- `%joinmusic_version%` - Shows the current version of the plugin
- `%joinmusic_titel%` or `%joinmusic_song%` - Returns the title of the currently playing song
- `%joinmusic_author%` - Returns the author of the currently playing song
- `%joinmusic_originalauthor%` - Returns the original author of the currently playing song
- `%joinmusic_description%` - Returns the description of the currently playing song
- `%joinmusic_prefix%` - Returns the plugin prefix

## License
This project is licensed under the [MIT License](LICENSE).

## Links
- [Bukkit](https://dev.bukkit.org/projects/joinmusik)
- [Spigot](https://www.spigotmc.org/resources/joinmusic.78169//)
- [Hanger](https://hangar.papermc.io/T0biii/JoinMusic)
- [Modrinth](https://modrinth.com/plugin/joinmusic)
- [Issue Tracker](https://github.com/T0biii/JoinMusic/issues)
- [bStats](https://bstats.org/plugin/bukkit/JoinMusic/6447)
