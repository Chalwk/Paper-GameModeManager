// Copyright (c) 2026. Jericho Crosby (Chalwk)

package com.chalwk.util;

import com.chalwk.GameModeManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class MessageHelper {

    public static void sendMessage(CommandSender sender, String message) {
        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        sender.sendMessage(component);
    }

    /**
     * Sends an automatic, plugin-triggered message (gamemode changes, inventory
     * loaded/saved, restriction "denied" notices, etc.) only if the
     * messages.enabled toggle in config.yml is set to true. Direct command
     * responses (e.g. /gmmanage status, reload) should keep using
     * {@link #sendMessage(CommandSender, String)} instead so they always show.
     */
    public static void sendGameMessage(GameModeManager plugin, CommandSender sender, String message) {
        if (!plugin.getConfigManager().getConfig().isMessagesEnabled()) {
            return;
        }
        sendMessage(sender, message);
    }
}
