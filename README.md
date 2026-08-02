# GameModeManager

Store and restore player states per gamemode.  
Separate inventories, health, hunger, experience, and potion effects for **Creative** and **Survival** modes.

[![MIT License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Latest Release](https://img.shields.io/github/v/release/Chalwk/Paper-GameModeManager?sort=semver)](https://github.com/Chalwk/Paper-GameModeManager/releases/latest)

## Features

- **Per‑Gamemode Player States**  
  Saves inventory, health, food, saturation, experience (total, level, progress) and active potion effects separately
  for **Creative** and **Survival** modes.

- **Automatic State Switching**  
  When a player changes gamemode, the current state is saved and the state of the new gamemode is applied.

- **World‑Change Awareness**  
  If a player teleports or portals to another world, the plugin remembers the gamemode they had before the world change
  and restores it after the world switch completes.

- **Persistent Storage**  
  All player data is saved to disk (`playerdata/<uuid>.yml`) and loaded when the player joins. Data is automatically
  saved on quit and during server shutdown.

- **Reload Support**  
  Configuration can be reloaded in‑game without restarting the server.

## Commands

| Command               | Description                    |
|-----------------------|--------------------------------|
| `/gmmanage` or `/gmm` | Shows the help message.        |
| `/gmmanage reload`    | Reloads the `config.yml` file. |
| `/gmmanage help`      | Displays the help message.     |

## Permissions

| Permission        | Description                           | Default |
|-------------------|---------------------------------------|---------|
| `gmmanage.use`    | Allows using the `/gmmanage` command. | `op`    |
| `gmmanage.reload` | Allows reloading the configuration.   | `op`    |
| `gmmanage.*`      | Grants all `gmmanage` permissions.    | `op`    |