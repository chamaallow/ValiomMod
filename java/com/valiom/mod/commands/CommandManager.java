package com.valiom.mod.commands;

import com.valiom.mod.commands.ValiomRankCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

public class CommandManager {

    private final Plugin plugin;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        register("valiomrank", new ValiomRankCommand());
        register("resetplayer", new ResetPlayerCommand());
    }

    private void register(String name, CommandExecutor executor) {
        PluginCommand command = plugin.getServer().getPluginCommand(name);
        if (command != null) {
            command.setExecutor(executor);
        } else {
            Bukkit.getLogger().warning("❗ La commande /" + name + " n'est pas déclarée dans plugin.yml !");
        }
    }
}
