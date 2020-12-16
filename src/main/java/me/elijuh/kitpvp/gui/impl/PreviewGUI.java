package me.elijuh.kitpvp.gui.impl;

import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PreviewGUI extends GUI {
    private static final ItemStack BACK = new ItemBuilder(Material.NETHER_STAR).setName("&6» &eBack &6«").build();
    private final Kit kit;

    public PreviewGUI(Kit kit) {
        super("preview", 6, "&6Preview: " + kit.getColor() + ChatColor.BOLD + ChatUtil.capitalize(kit.getName()) + " &fKit");
        this.kit = kit;
    }

    @Override
    public void setItems(Player p) {
        getInventory().clear();
        int itemIndex = 0;
        for (int i = 0; i < getInventory().getSize(); i++) {
            List<ItemStack> items = kit.getItems();
            if (i % 9 != 8 && i % 9 != 0 && i > 9 && i < 44) {
                if (itemIndex < items.size()) {
                    getInventory().setItem(i, items.get(itemIndex));
                    itemIndex++;
                }
            } else {
                getInventory().setItem(i, GUI.FILLER);
            }
        }
        getInventory().setItem(49, BACK);
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(getInventory().getTitle())) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().equals(BACK)) {
                    e.getView().close();
                }
                e.setCancelled(true);
            }
        }
    }
}
