# Permissions

JoinMusic uses a comprehensive permission system to control access to commands and features. This page lists all available permissions and their purposes.

## Permission Overview

| Permission | Description | Default |
|------------|-------------|---------|
| `JoinMusic.use` | Allows using the /joinmusic command | All players |
| `JoinMusic.play` | Allows using /joinmusic play | All players |
| `JoinMusic.command.stop` | Allows using /joinmusic stop | All players |
| `JoinMusic.command.reload` | Allows using /joinmusic reload | Operators |
| `JoinMusic.command.disableOwn` | Allows using /joinmusic disable and /jm enable | All players |
| `JoinMusic.update` | Receives update notifications | Operators |

## Permission Details

### Basic Permissions

#### `JoinMusic.use`
- **Description:** Allows using the main /joinmusic command
- **Default:** All players
- **Usage:** This permission is required to use any JoinMusic command
- **Example:** Without this permission, players cannot see the help menu

#### `JoinMusic.play`
- **Description:** Allows using the /joinmusic play command
- **Default:** All players
- **Usage:** This permission is required to manually play music
- **Example:** Players with this permission can trigger music playback

### Command Permissions

#### `JoinMusic.command.stop`
- **Description:** Allows using the /joinmusic stop command
- **Default:** All players
- **Usage:** This permission is required to stop currently playing music
- **Example:** Players with this permission can stop music that is currently playing

#### `JoinMusic.command.reload`
- **Description:** Allows using the /joinmusic reload command
- **Default:** Operators
- **Usage:** This permission is required to reload the plugin configuration
- **Example:** Server administrators use this to apply configuration changes without restarting

#### `JoinMusic.command.disableOwn`
- **Description:** Allows using /joinmusic disable and /jm enable commands
- **Default:** All players
- **Usage:** This permission is required to toggle music playback for yourself
- **Example:** Players with this permission can disable music when they join

### Notification Permissions

#### `JoinMusic.update`
- **Description:** Receives update notifications
- **Default:** Operators
- **Usage:** This permission is required to see update notifications
- **Example:** Server administrators use this to be informed about new versions

## Permission Configuration

JoinMusic integrates with popular permission plugins such as:

- LuckPerms
- PermissionsEx
- GroupManager
- EssentialsX

To set up permissions, you can use your preferred permission plugin's commands or configuration files.

### Example with LuckPerms

```
/lp user <player> permission set JoinMusic.command.reload
/lp group admin permission set JoinMusic.command.reload
```

### Example with PermissionsEx

```
/pex user <player> add JoinMusic.command.reload
/pex group admin add JoinMusic.command.reload
```

## Default Permission Assignments

By default, JoinMusic assigns permissions as follows:

- All players can use basic commands (`JoinMusic.use`, `JoinMusic.play`, `JoinMusic.command.stop`, `JoinMusic.command.disableOwn`)
- Only operators can use administrative commands (`JoinMusic.command.reload`, `JoinMusic.update`)

## Permission Inheritance

If you're using a permission plugin that supports inheritance, you can set up permission groups to inherit permissions from other groups. For example:

```
admin:
  permissions:
    - JoinMusic.*
moderator:
  permissions:
    - JoinMusic.use
    - JoinMusic.play
    - JoinMusic.command.stop
player:
  permissions:
    - JoinMusic.use
    - JoinMusic.command.disableOwn
```

This way, administrators would have all permissions, moderators would have most permissions except reload, and regular players would only have basic permissions. 
