package com.valiom.mod.chatmod;

import com.valiom.utils.LuckPermUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatReportListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();

        // ⚙️ Nettoyage du préfix LuckPerms (si vide, fallback)
        String rawPrefix = LuckPermUtils.getPrefix(player);
        String cleanPrefix = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', rawPrefix));
        ChatColor prefixColor = ChatColor.RED;

        // 📎 Icône de report
        TextComponent reportIcon = new TextComponent("⚠ ");
        reportIcon.setColor(ChatColor.RED);
        reportIcon.setBold(true);
        reportIcon.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/reportmsg " + UUID.randomUUID()
        ));

        // 🏷️ Préfix du joueur
        TextComponent prefixComponent = new TextComponent(cleanPrefix);
        prefixComponent.setColor(prefixColor);
        prefixComponent.setBold(true);

        // 🧍 Nom du joueur
        TextComponent nameComponent = new TextComponent(player.getName());
        nameComponent.setColor(ChatColor.WHITE);

        // 💬 Message
        TextComponent msgComponent = new TextComponent(" : " + message);
        msgComponent.setColor(ChatColor.GRAY);

        // 📦 Assemblage
        TextComponent fullMessage = new TextComponent();
        fullMessage.addExtra(reportIcon);
        if (!cleanPrefix.isEmpty()) {
            fullMessage.addExtra(prefixComponent);
            fullMessage.addExtra(new TextComponent(" "));
        }
        fullMessage.addExtra(nameComponent);
        fullMessage.addExtra(msgComponent);

        // 📤 Envoi à tous les joueurs
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(fullMessage);
        }
    }
}
