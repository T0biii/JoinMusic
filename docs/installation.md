# Installation Guide

This guide will help you install and set up JoinMusic on your server.

## Prerequisites

Before installing JoinMusic, make sure you have:

- A Bukkit/Spigot server (version 1.8 - 1.21.x)
- [NoteBlockAPI](https://www.spigotmc.org/resources/noteblockapi.19287/) (Version 1.6.1.1 or newer)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)

## Basic Installation

### Step 1: Download Required Files
1. Download the latest version of JoinMusic from:
   - [Spigot](https://www.spigotmc.org/resources/joinmusic.78169/)
   - [Bukkit](https://dev.bukkit.org/projects/joinmusik)
   - [Hangar](https://hangar.papermc.io/T0biii/JoinMusic)
   - [Modrinth](https://modrinth.com/plugin/joinmusic)

2. Download [NoteBlockAPI](https://www.spigotmc.org/resources/noteblockapi.19287/)

### Step 2: Install the Plugins
1. Stop your server
2. Place both JAR files in your server's `plugins` folder:
   - `JoinMusic.jar`
   - `NoteBlockAPI.jar`
3. Start your server

### Step 3: Initial Configuration
1. The plugin will generate its configuration file on first run
2. Place your music files (`.nbs` format) in the `plugins/JoinMusic/music` folder
3. Configure the plugin using the commands or by editing `plugins/JoinMusic/config.yml`

## BungeeCord Setup

If you're using a BungeeCord network, follow these additional steps:

### Step 1: Install on Spigot Servers
1. Install JoinMusic and NoteBlockAPI on all Spigot servers in your network
2. Enable BungeeCord mode in the configuration of each Spigot server

### Step 2: Install on BungeeCord
1. Download the BungeeCord version of JoinMusic
2. Place the BungeeCord plugin JAR in your BungeeCord's `plugins` folder
3. Restart your BungeeCord server

## Verification

To verify your installation:

1. Check the server console for any error messages
2. Use the command `/joinmusic` or `/jm` to see if the plugin responds
3. Join the server to test if music plays automatically

## Troubleshooting

If you encounter issues:

1. Check if NoteBlockAPI is properly installed
2. Verify your music files are in the correct format (`.nbs`)
3. Ensure you have the correct permissions
4. Check the server logs for error messages

## Next Steps

After installation:
- [Configure the plugin](configuration.md)
- [Set up permissions](permissions.md)
- [Learn about available commands](commands.md) 
