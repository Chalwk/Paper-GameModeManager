// Copyright (c) 2026. Jericho Crosby (Chalwk)

package com.chalwk.commands;

import com.chalwk.GameModeManager;
import com.chalwk.config.PluginConfig;
import com.chalwk.util.MessageHelper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameModeCommand implements TabExecutor {
    private final GameModeManager plugin;

    public GameModeCommand(GameModeManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!sender.hasPermission("gmmanage.use")) {
            MessageHelper.sendMessage(sender, plugin.getConfigManager().getConfig().getNoPermissionMsg());
            return true;
        }

        if (args.length == 0) {
            sendStatus(sender);
            return true;
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            if (!sender.hasPermission("gmmanage.reload")) {
                MessageHelper.sendMessage(sender, plugin.getConfigManager().getConfig().getNoPermissionMsg());
                return true;
            }
            plugin.reload();
            MessageHelper.sendMessage(sender, plugin.getConfigManager().getConfig().getReloadedMsg());
        } else {
            sendStatus(sender);
        }
        return true;
    }

    private void sendStatus(CommandSender sender) {
        PluginConfig config = plugin.getConfigManager().getConfig();
        MessageHelper.sendMessage(sender, config.getStatusHeader());

        String auto = config.isAutoSwitchWorlds() ? "enabled" : "disabled";
        MessageHelper.sendMessage(sender, config.getStatusAutoSwitch().replace("{enabled}", auto));

        MessageHelper.sendMessage(sender, config.getStatusWorldMappings());
        for (Map.Entry<String, GameMode> entry : config.getWorldGamemodes().entrySet()) {
            MessageHelper.sendMessage(sender, "  &7" + entry.getKey() + " → &e" + entry.getValue().name());
        }

        MessageHelper.sendMessage(sender, config.getStatusRestrictions());
        MessageHelper.sendMessage(sender, config.getStatusLine()
                .replace("{action}", "block_place")
                .replace("{state}", config.isCreativeBlockPlaceRestricted() ? "&cblocked" : "&aallowed"));
        MessageHelper.sendMessage(sender, config.getStatusLine()
                .replace("{action}", "block_break")
                .replace("{state}", config.isCreativeBlockBreakRestricted() ? "&cblocked" : "&aallowed"));
        MessageHelper.sendMessage(sender, config.getStatusLine()
                .replace("{action}", "item_drop")
                .replace("{state}", config.isCreativeItemDropRestricted() ? "&cblocked" : "&aallowed"));
        MessageHelper.sendMessage(sender, config.getStatusLine()
                .replace("{action}", "item_pickup")
                .replace("{state}", config.isCreativeItemPickupRestricted() ? "&cblocked" : "&aallowed"));

        printMaterialList(sender, "Block restrictions (place & break)", config.getCreativeBlockRestrictions());
        printStringList(sender, "Command blacklist (creative)", config.getCreativeCommandBlacklist());

        MessageHelper.sendMessage(sender, "  &7Falling block drops prevented: &a"
                + config.isPreventFallingBlockDrops());
        MessageHelper.sendMessage(sender, "  &7Automatic messages enabled: &a"
                + config.isMessagesEnabled());
    }

    private void printMaterialList(CommandSender sender, String label, Set<Material> materials) {
        if (materials.isEmpty()) {
            MessageHelper.sendMessage(sender, "  &7" + label + ": &anone");
            return;
        }
        MessageHelper.sendMessage(sender, "  &7" + label + ":");
        StringBuilder sb = new StringBuilder();
        for (Material mat : materials) {
            sb.append(mat.name()).append(", ");
        }
        String list = sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        MessageHelper.sendMessage(sender, "    &c" + list);
    }

    private void printStringList(CommandSender sender, String label, Set<String> values) {
        if (values.isEmpty()) {
            MessageHelper.sendMessage(sender, "  &7" + label + ": &anone");
            return;
        }
        MessageHelper.sendMessage(sender, "  &7" + label + ":");
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value).append(", ");
        }
        String list = sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        MessageHelper.sendMessage(sender, "    &c" + list);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command command,
                                      @NotNull String label,
                                      @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            List<String> options = new ArrayList<>();
            options.add("reload");
            if (sender.hasPermission("gmmanage.reload")) {
                options.add("reload");
            }
            options.add("status");
            for (String opt : options) {
                if (opt.startsWith(partial)) {
                    completions.add(opt);
                }
            }
        }
        return completions;
    }
}