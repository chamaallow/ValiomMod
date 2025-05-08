package com.valiom.mod;

import com.valiom.ValiomCore;
import com.valiom.mod.chatmod.ChatReportListener;
import com.valiom.mod.commands.CommandManager;
import com.valiom.mod.commands.ResetPlayerCommand;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ValiomMod extends JavaPlugin {

    private static ValiomMod instance;
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().getPlugin("ValiomCore") == null) {
            getLogger().severe("❌ ValiomCore n'est pas chargé, arrêt de ValiomMod...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("✅ Hooké à ValiomCore !");
        setupLuckPerms();
        getLogger().info("✅ ValiomMod actif !");

        new CommandManager(this).registerCommands();

        getServer().getPluginManager().registerEvents(new ChatReportListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("⛔ ValiomMod désactivé !");
    }

    private void setupLuckPerms() {
        try {
            luckPerms = LuckPermsProvider.get();
            getLogger().info("✅ Hooké à LuckPerms !");
        } catch (Exception e) {
            getLogger().severe("❌ LuckPerms non trouvé !");
        }
    }

    public static ValiomMod getInstance() {
        return instance;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
