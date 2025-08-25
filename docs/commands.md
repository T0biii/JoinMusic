# Commands

JoinMusic provides several commands to control music playback and plugin settings. All commands can be used with either `/joinmusic` or the shorter alias `/jm`.

## Command Overview

| Command | Description | Permission |
|---------|-------------|------------|
| `/joinmusic` or `/jm` | Shows help with available commands | `JoinMusic.use` |
| `/joinmusic reload` | Reloads the configuration | `JoinMusic.command.reload` |
| `/joinmusic stop` | Stops the currently playing music | `JoinMusic.command.stop` |
| `/joinmusic disable` | Disables automatic music playback | `JoinMusic.command.disableOwn` |
| `/joinmusic enable` | Enables automatic music playback | `JoinMusic.command.disableOwn` |
| `/joinmusic toogle` | Toggles (enables or disables) automatic music playback when joining the server. | `JoinMusic.command.disableOwn` |
| `/joinmusic skip` | Skips the current song if looping is activ | `JoinMusic.command.skip` |

## Command Details

### Main Command
```
/joinmusic
/jm
```
Shows the help menu with all available commands and their descriptions.

**Permission:** `JoinMusic.use`

### Reload Command
```
/joinmusic reload
/jm reload
```
Reloads the plugin configuration from the config.yml file. This is useful after making changes to the configuration without restarting the server.

**Permission:** `JoinMusic.command.reload`

**Example:**
```
> /jm reload
[JoinMusic] The reload was successful!
```

### Stop Command
```
/joinmusic stop
/jm stop
```
Stops the currently playing music. This is useful if you want to stop the music before it finishes playing.

**Permission:** `JoinMusic.command.stop`

**Example:**
```
> /jm stop
[JoinMusic] Stopped playing the song! You can disable playing a song on join with /jm disable
```

### Disable Command
```
/joinmusic disable
/jm disable
```
Disables automatic music playback when joining the server. This setting is saved per player.

**Permission:** `JoinMusic.command.disableOwn`

**Example:**
```
> /jm disable
[JoinMusic] Disabled playing a song when joining. To enable it again, use /jm enable
```

### Enable Command
```
/joinmusic enable
/jm enable
```
Enables automatic music playback when joining the server. This setting is saved per player.

**Permission:** `JoinMusic.command.disableOwn`

**Example:**
```
> /jm enable
[JoinMusic] Enabled playing a song when joining
```
### Toggle Command
```
/joinmusic toggle
/jm toggle
```
Toggles (enables or disables) automatic music playback when joining the server. This setting is saved per player.

**Permission:** `JoinMusic.command.disableOwn`

**Example:**
```
> /jm toggle
[JoinMusic] Disabled playing a song when joining. To enable it again, use /jm enable
```

```
> /jm toggle
[JoinMusic] Enabled playing a song when joining
```


### Skip Command
```
/joinmusic skip
/jm skip
```
Enables automatic music playback when joining the server. This setting is saved per player.

**Permission:** `JoinMusic.command.skip`

**Example:**
```
> /jm skip
[JoinMusic] Skipped to next random song
```
## Command Messages

All command messages can be customized in the `config.yml` file under the `messages` section:

```yaml
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

## Tab Completion

JoinMusic supports tab completion for all commands, making it easier to use the commands without remembering the exact syntax.

## Command Aliases

For convenience, all commands can be used with either the full command name `/joinmusic` or the shorter alias `/jm`. 
