// Copyright (c) 2026. Jericho Crosby (Chalwk)

package com.chalwk.config;

import com.chalwk.GameModeManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;

public class PluginConfig {
    private final GameModeManager plugin;
    private final Map<String, GameMode> worldGamemodes = new HashMap<>();
    private final Set<Material> creativeBlockRestrictions = new HashSet<>();
    private final Set<String> creativeCommandBlacklist = new HashSet<>();
    private final List<InventoryType> restrictedContainerTypes = new ArrayList<>();
    private boolean autoSwitchWorlds = true;
    private boolean creativeBlockPlaceRestricted = false;
    private boolean creativeBlockBreakRestricted = false;
    private boolean creativeItemDropRestricted = false;
    private boolean creativeItemPickupRestricted = false;
    private boolean preventFallingBlockDrops = false;
    private boolean messagesEnabled = true;
    private String noPermissionMsg = "&cYou don't have permission to use this feature!";
    private String reloadedMsg = "&aConfiguration reloaded!";
    private String statusHeader = "&6GameModeManager Status";
    private String statusAutoSwitch = "&7Auto world-switch: &a{enabled}";
    private String statusWorldMappings = "&7World mappings:";
    private String statusRestrictions = "&7Restrictions (creative):";
    private String statusLine = "  &7{action}: &a{state}";
    private String inventoryLoaded = "&aLoaded {gamemode} inventory for {player}";
    private String gamemodeChanged = "&aGamemode changed to {gamemode} for {player}";
    private String containerAccessDenied = "&cYou cannot open containers in Creative mode!";
    private String blockBreakDenied = "&cYou cannot break that block in Creative mode!";
    private String itemDropDenied = "&cYou cannot drop items in Creative mode!";
    private String itemPickupDenied = "&cYou cannot pick up items in Creative mode!";
    private String commandBlocked = "&cThat command is disabled in Creative mode!";

    public PluginConfig(GameModeManager plugin) {
        this.plugin = plugin;
    }

    public void loadFromConfig(ConfigurationSection config) {
        autoSwitchWorlds = config.getBoolean("auto_switch_worlds", true);
        preventFallingBlockDrops = config.getBoolean("prevent_falling_block_drops", false);

        ConfigurationSection worldSection = config.getConfigurationSection("world_gamemodes");
        if (worldSection != null) {
            worldGamemodes.clear();
            for (String worldName : worldSection.getKeys(false)) {
                String gmStr = worldSection.getString(worldName);
                try {
                    GameMode gm = GameMode.valueOf(gmStr.toUpperCase());
                    worldGamemodes.put(worldName, gm);
                } catch (IllegalArgumentException e) {
                    // ignore invalid gamemode
                }
            }
        }

        ConfigurationSection restrictions = config.getConfigurationSection("restrictions.creative");
        if (restrictions != null) {
            creativeBlockPlaceRestricted = restrictions.getBoolean("block_place", false);
            creativeBlockBreakRestricted = restrictions.getBoolean("block_break", false);
            creativeItemDropRestricted = restrictions.getBoolean("item_drop", false);
            creativeItemPickupRestricted = restrictions.getBoolean("item_pickup", false);

            List<String> restrictedBlockNames = restrictions.getStringList("block_restrictions");
            creativeBlockRestrictions.clear();
            for (String name : restrictedBlockNames) {
                Material mat = Material.getMaterial(name.toUpperCase());
                if (mat != null) {
                    creativeBlockRestrictions.add(mat);
                } else {
                    plugin.getLogger().warning("Unknown material in block_restrictions: " + name);
                }
            }

            List<String> containerTypeNames = restrictions.getStringList("restricted_container_types");
            restrictedContainerTypes.clear();
            for (String name : containerTypeNames) {
                try {
                    InventoryType type = InventoryType.valueOf(name.toUpperCase());
                    restrictedContainerTypes.add(type);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Unknown inventory type in restricted_container_types: " + name);
                }
            }

            List<String> commandNames = restrictions.getStringList("command_blacklist");
            creativeCommandBlacklist.clear();
            for (String name : commandNames) {
                String cmd = name.trim().toLowerCase();
                if (cmd.startsWith("/")) {
                    cmd = cmd.substring(1);
                }
                if (!cmd.isEmpty()) {
                    creativeCommandBlacklist.add(cmd);
                }
            }
        }

        ConfigurationSection messages = config.getConfigurationSection("messages");
        if (messages != null) {
            messagesEnabled = messages.getBoolean("enabled", true);
            noPermissionMsg = messages.getString("no_permission", noPermissionMsg);
            reloadedMsg = messages.getString("reloaded", reloadedMsg);
            statusHeader = messages.getString("status_header", statusHeader);
            statusAutoSwitch = messages.getString("status_auto_switch", statusAutoSwitch);
            statusWorldMappings = messages.getString("status_world_mappings", statusWorldMappings);
            statusRestrictions = messages.getString("status_restrictions", statusRestrictions);
            statusLine = messages.getString("status_line", statusLine);
            inventoryLoaded = messages.getString("inventory_loaded", inventoryLoaded);
            gamemodeChanged = messages.getString("gamemode_changed", gamemodeChanged);
            containerAccessDenied = messages.getString("container_access_denied", containerAccessDenied);
            blockBreakDenied = messages.getString("block_break_denied", blockBreakDenied);
            itemDropDenied = messages.getString("item_drop_denied", itemDropDenied);
            itemPickupDenied = messages.getString("item_pickup_denied", itemPickupDenied);
            commandBlocked = messages.getString("command_blocked", commandBlocked);
        }
    }

    public boolean isAutoSwitchWorlds() {
        return autoSwitchWorlds;
    }

    public Map<String, GameMode> getWorldGamemodes() {
        return worldGamemodes;
    }

    public boolean isCreativeBlockPlaceRestricted() {
        return creativeBlockPlaceRestricted;
    }

    public boolean isCreativeBlockBreakRestricted() {
        return creativeBlockBreakRestricted;
    }

    public boolean isCreativeItemDropRestricted() {
        return creativeItemDropRestricted;
    }

    public boolean isCreativeItemPickupRestricted() {
        return creativeItemPickupRestricted;
    }

    public boolean isPreventFallingBlockDrops() {
        return preventFallingBlockDrops;
    }

    public boolean isMessagesEnabled() {
        return messagesEnabled;
    }

    public Set<Material> getCreativeBlockRestrictions() {
        return creativeBlockRestrictions;
    }

    public Set<String> getCreativeCommandBlacklist() {
        return creativeCommandBlacklist;
    }

    public List<InventoryType> getRestrictedContainerTypes() {
        return restrictedContainerTypes;
    }

    public String getNoPermissionMsg() {
        return noPermissionMsg;
    }

    public String getReloadedMsg() {
        return reloadedMsg;
    }

    public String getStatusHeader() {
        return statusHeader;
    }

    public String getStatusAutoSwitch() {
        return statusAutoSwitch;
    }

    public String getStatusWorldMappings() {
        return statusWorldMappings;
    }

    public String getStatusRestrictions() {
        return statusRestrictions;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public String getInventoryLoaded() {
        return inventoryLoaded;
    }

    public String getGamemodeChanged() {
        return gamemodeChanged;
    }

    public String getContainerAccessDenied() {
        return containerAccessDenied;
    }

    public String getBlockBreakDenied() {
        return blockBreakDenied;
    }

    public String getItemDropDenied() {
        return itemDropDenied;
    }

    public String getItemPickupDenied() {
        return itemPickupDenied;
    }

    public String getCommandBlocked() {
        return commandBlocked;
    }
}
