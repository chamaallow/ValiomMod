package com.valiom.mod.commands;

import com.valiom.ValiomCore;
import com.valiom.api.managers.ProfileManager;
import com.valiom.core.managers.ProfileManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.UUID;

public class ResetPlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("valiommod.command.resetplayer")) {
            sender.sendMessage("§cTu n’as pas la permission d’exécuter cette commande.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUtilisation: /resetplayer <pseudo>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID uuid = target.getUniqueId();

        // 🔁 Supprimer le profil du cache + base
        ProfileManager profileManager = ValiomCore.getAPI().getProfileManager();
        profileManager.deleteProfile(uuid);
        if (profileManager instanceof ProfileManagerImpl) {
            ((ProfileManagerImpl) profileManager).deleteProfileInDatabase(uuid);
            ProfileManagerImpl.clearLuckPermsUser(uuid);
        }

        // 🧹 Supprimer les fichiers joueur dans le monde
        String[] folders = {"playerdata", "stats", "advancements"};
        for (String folder : folders) {
            File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), folder + "/" + uuid + (folder.equals("playerdata") ? ".dat" : ".json"));
            if (file.exists()) {
                file.delete();
            }
        }

        sender.sendMessage("§a✅ Le joueur §e" + args[0] + " §aa été totalement réinitialisé.");
        return true;
    }
}
