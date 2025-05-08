package com.valiom.mod.commands;

import com.valiom.ValiomCore;
import com.valiom.mod.ValiomMod;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ValiomRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3 || !args[0].equalsIgnoreCase("set")) {
            sender.sendMessage("§cUsage: /valiomrank set <joueur> <grade>");
            return true;
        }

        String playerName = args[1];
        String rankName = args[2];

        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            sender.sendMessage("§cLe joueur §e" + playerName + " §cn'est pas en ligne !");
            return true;
        }

        UUID uuid = target.getUniqueId();

        // Vérifie si le grade existe
        Group group = ValiomMod.getInstance().getLuckPerms().getGroupManager().getGroup(rankName);
        if (group == null) {
            sender.sendMessage("§cLe grade §e" + rankName + " §cn'existe pas !");
            return true;
        }

        // Modifier le joueur via LuckPerms
        ValiomMod.getInstance().getLuckPerms().getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            // Vérifie si le joueur a déjà ce groupe
            boolean hasGroup = user.getNodes().stream()
                    .filter(node -> node instanceof InheritanceNode)
                    .map(node -> (InheritanceNode) node)
                    .anyMatch(node -> node.getGroupName().equalsIgnoreCase(rankName));

            if (hasGroup) {
                sender.sendMessage("§cLe joueur §e" + playerName + " §ca déjà le grade §e" + rankName + "§c !");
                return;
            }

            // Supprimer anciens groupes
            user.data().clear();

            // Ajouter le nouveau groupe
            InheritanceNode node = InheritanceNode.builder(rankName).build();
            user.data().add(node);

            // Sauvegarder les modifications
            ValiomMod.getInstance().getLuckPerms().getUserManager().saveUser(user);

            // ✅ Met à jour le rank dans la BDD + cache mémoire
            ValiomCore.getInstance().getAPI().getProfileManager().updateRank(uuid, rankName);

            // Feedback
            sender.sendMessage("§aLe grade de §e" + playerName + " §aa été changé en §b" + rankName + "§a !");
        });

        return true;
    }
}
