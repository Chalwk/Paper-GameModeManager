// Copyright (c) 2026. Jericho Crosby (Chalwk)

package com.chalwk.listeners;

import com.chalwk.GameModeManager;
import com.chalwk.config.PluginConfig;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldSwitchListener implements Listener {
    private final GameModeManager plugin;

    public WorldSwitchListener(GameModeManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        PluginConfig config = plugin.getConfigManager().getConfig();

        if (!config.isAutoSwitchWorlds()) {
            return;
        }

        String worldName = player.getWorld().getName();
        GameMode targetGm = config.getWorldGamemodes().get(worldName);
        if (targetGm != null) {
            if (player.getGameMode() != targetGm) {
                player.setGameMode(targetGm);
            }
        }
    }
}