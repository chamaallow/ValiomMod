package com.valiom.mod.chatmod.gui;

import com.valiom.mod.chatmod.Report;
import com.valiom.mod.chatmod.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReportActionGUI {

    public static void open(Player player, Report report) {
        Inventory gui = Bukkit.createInventory(null, 9, "Actions pour: " + report.getReportedName());

        ItemStack sanction = new ItemStack(Material.ANVIL);
        ItemMeta sanctionMeta = sanction.getItemMeta();
        sanctionMeta.setDisplayName("§cSanctionner");
        sanction.setItemMeta(sanctionMeta);
        gui.setItem(3, sanction);

        ItemStack ignore = new ItemStack(Material.BARRIER);
        ItemMeta ignoreMeta = ignore.getItemMeta();
        ignoreMeta.setDisplayName("§aIgnorer");
        ignore.setItemMeta(ignoreMeta);
        gui.setItem(5, ignore);

        player.openInventory(gui);
    }

    public static void handleClick(Player player, InventoryClickEvent event, Report report) {
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;

        switch (event.getCurrentItem().getType()) {
            case ANVIL:
                player.performCommand("mute " + report.getReportedName() + " 10m Chat inapproprié");
                ReportManager.deleteReport(report);
                player.closeInventory();
                break;
            case BARRIER:
                ReportManager.deleteReport(report);
                player.closeInventory();
                break;
        }
    }
}
