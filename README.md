# GameModeManager

Manage separate inventories per gamemode with world-based auto-switching and creative-mode restrictions.

[![MIT License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Latest Release](https://img.shields.io/github/v/release/Chalwk/Paper-GameModeManager?sort=semver)](https://github.com/Chalwk/Paper-GameModeManager/releases/latest)

## Features

- **Per-Gamemode Inventories**: Separate inventories, health, hunger, experience, and potion effects for each gamemode (Survival, Creative, Adventure, Spectator).
- **World-Based Auto-Switch**: Automatically change a player's gamemode when they enter a world, based on configurable mappings.
- **Creative Mode Restrictions**: Restrict block placing/breaking, item dropping/pickup, container access, and command usage while in Creative.
- **Falling Block Drop Prevention**: Optionally prevent gravity-affected blocks (sand, gravel, anvils, etc.) from dropping items when they break.
- **Customizable Messages**: All feedback messages are configurable and can be toggled on/off.
- **Status Command**: View current settings and restrictions in-game.
- **Permission-Based Bypass**: Allows specific players to bypass all Creative restrictions.
- **Persistent Data**: Player states are saved to disk and restored on join or gamemode switch.

## Commands

- `/gmmanage` or `/gmm` - Show current status of the plugin configuration.
- `/gmmanage reload` - Reload the configuration file.
- `/gmmanage status` - Display all current settings (same as no subcommand).

## Permissions

- `gmmanage.use` - Allows using the `/gmmanage` command. (Default: op)
- `gmmanage.reload` - Allows reloading the configuration. (Default: op)
- `gmmanage.bypass.restrictions` - Bypass all Creative mode restrictions (block place/break, item drop/pickup, container
  access, command blacklist). (Default: op)
- `gmmanage.*` - Grants all above permissions.

## License

GameModeManager is licensed under the [MIT License](LICENSE).