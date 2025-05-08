package com.valiom.mod.chatmod.gui;

import com.valiom.mod.chatmod.Report;
import com.valiom.mod.chatmod.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ReportListGUI {

    public static void open(Player player) {
        List<Report> reports = ReportManager.getAllReports();
        Inventory gui = Bukkit.createInventory(null, 54, "📄 Reports messages");

        for (int i = 0; i < reports.size() && i < 54; i++) {
            Report report = reports.get(i);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName("§e" + report.getReportedName());
            meta.setLore(Arrays.asList(
                    "§7Message: §f" + report.getMessage(),
                    "§7Par: §a" + report.getReporterName(),
                    "§7Date: §b" + report.getDate().toString()
            ));

            item.setItemMeta(meta);
            gui.setItem(i, item);
        }

        player.openInventory(gui);
    }
}
