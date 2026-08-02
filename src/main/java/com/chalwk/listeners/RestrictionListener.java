// Copyright (c) 2026. Jericho Crosby (Chalwk)

package com.chalwk.listeners;

import com.chalwk.GameModeManager;
import com.chalwk.config.PluginConfig;
import com.chalwk.util.MessageHelper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Set;

public class RestrictionListener implements Listener {
    private final GameModeManager plugin;

    public RestrictionListener(GameModeManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        if (config.isCreativeBlockPlaceRestricted()) {
            event.setCancelled(true);
            return;
        }

        Material placed = event.getBlock().getType();
        if (config.getCreativeBlockRestrictions().contains(placed)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        if (config.isCreativeBlockBreakRestricted()) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getBlockBreakDenied());
            return;
        }

        Material broken = event.getBlock().getType();
        if (config.getCreativeBlockRestrictions().contains(broken)) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getBlockBreakDenied());
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        if (config.isCreativeItemDropRestricted()) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getItemDropDenied());
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        if (config.isCreativeItemPickupRestricted()) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getItemPickupDenied());
        }
    }

    /**
     * Prevents gravity-affected blocks (anvils, sand, gravel, concrete powder, etc.)
     * from dropping items when they land and break. This is a global, server-wide
     * setting (prevent_falling_block_drops) and is not tied to a specific player's
     * gamemode, since falling blocks aren't reliably attributable to one player.
     */
    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof FallingBlock)) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        if (config.isPreventFallingBlockDrops()) {
            event.setCancelled(true);
        }
    }

    /**
     * Blocks the use of blacklisted commands while a player is in Creative mode.
     * Only the base command/label is checked (e.g. "give" blocks "/give" and
     * "/minecraft:give"), not full argument matching.
     */
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        Set<String> blacklist = config.getCreativeCommandBlacklist();
        if (blacklist.isEmpty()) return;

        String raw = event.getMessage().substring(1); // strip leading '/'
        if (raw.isEmpty()) return;

        String label = raw.split(" ", 2)[0].toLowerCase();
        int colonIdx = label.indexOf(':');
        if (colonIdx != -1) {
            label = label.substring(colonIdx + 1); // strip "namespace:" prefix
        }

        if (blacklist.contains(label)) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getCommandBlocked());
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (player.hasPermission("gmmanage.bypass.restrictions")) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;

        PluginConfig config = plugin.getConfigManager().getConfig();
        InventoryType type = event.getInventory().getType();
        if (config.getRestrictedContainerTypes().contains(type)) {
            event.setCancelled(true);
            MessageHelper.sendGameMessage(plugin, player, config.getContainerAccessDenied());
        }
    }
}