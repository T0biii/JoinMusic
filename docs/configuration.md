# Configuration

JoinMusic's configuration is stored in `plugins/JoinMusic/config.yml`. This guide explains all available configuration options.

## Main Configuration

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

## Configuration Options Explained

### Music Settings

| Option | Description | Default | Values |
|--------|-------------|---------|---------|
| `music` | Main music file or folder path | `Song.nbs` | Path to .nbs file |
| `options.music.random` | Enable random music selection | `false` | `true`/`false` |
| `options.music.RandomFoldername` | Folder for random music | `"random"` | Folder name |
| `options.music.Mode` | Playback mode | `"MonoMode"` | `"MonoMode"`/`"StereoMode"` |
| `options.music.10Octave` | Enable 10 octaves | `false` | `true`/`false` |
| `options.music.Volume` | Music volume | `100` | `0-100` |
| `options.music.AllowLooping` | Enable music looping | `false` | `true`/`false` |
| `options.music.OneWorldonly` | Restrict to one world | `false` | `true`/`false` |
| `options.music.Worldname` | Target world name | `"world"` | World name |

### General Settings

| Option | Description | Default | Values |
|--------|-------------|---------|---------|
| `options.update-check` | Check for updates | `true` | `true`/`false` |
| `options.updateinfo` | Show update notifications | `true` | `true`/`false` |
| `options.metrics` | Enable bStats | `true` | `true`/`false` |
| `options.delaySong` | Delay before playing | `2` | Seconds |
| `options.bungeecord` | BungeeCord mode | `false` | `true`/`false` |
| `options.printSongTitel` | Show song title | `true` | `true`/`false` |
| `options.allowDisabling` | Allow player disable | `true` | `true`/`false` |

### Message Settings

All messages support color codes using the `&` symbol:

| Message | Description | Default |
|---------|-------------|---------|
| `messages.prefix` | Command prefix | `&7[&bJoinMusic&7]` |
| `messages.reload` | Reload success | `&3The reload was successful!` |
| `messages.stop` | Stop message | `&3Stopped playing the song!` |
| `messages.no-permission` | Permission denied | `&cYou don't have enough permissions` |
| `messages.disabled` | Music disabled | `&3Disabled playing a song when joining` |
| `messages.enabled` | Music enabled | `&3Enabled playing a song when joining` |
| `messages.playing` | Song playing | `&2Started Playing the Song: &a&l%song%` |

## Color Codes

You can use these color codes in messages:
- `&0` - Black
- `&1` - Dark Blue
- `&2` - Dark Green
- `&3` - Dark Aqua
- `&4` - Dark Red
- `&5` - Dark Purple
- `&6` - Gold
- `&7` - Gray
- `&8` - Dark Gray
- `&9` - Blue
- `&a` - Green
- `&b` - Aqua
- `&c` - Red
- `&d` - Light Purple
- `&e` - Yellow
- `&f` - White

Formatting codes:
- `&l` - Bold
- `&n` - Underline
- `&o` - Italic
- `&k` - Magic
- `&r` - Reset

## Reloading Configuration

To apply configuration changes:
1. Edit the `config.yml` file
2. Use the command `/joinmusic reload` or `/jm reload`
3. The changes will be applied immediately 
