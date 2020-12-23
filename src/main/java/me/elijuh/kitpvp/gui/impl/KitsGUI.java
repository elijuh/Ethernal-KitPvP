package me.elijuh.kitpvp.gui.impl;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.Userdata;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitsGUI extends GUI {
    private static final Map<String, KitsGUI> instances = new HashMap<>();

    public KitsGUI() {
        super("kits", 6, "&4Kits");
    }

    @Override
    public void setItems(Player p) {
        getInventory().clear();
        for (int i = 0; i < getInventory().getSize(); i++) {
            getInventory().setItem(i, GUI.FILLER);
        }
        getInventory().setItem(13, getKitItem(KitPvP.getInstance().getKitManager().getKit("default"), p));
        getInventory().setItem(29, getKitItem(KitPvP.getInstance().getKitManager().getKit("knight"), p));
        getInventory().setItem(30, getKitItem(KitPvP.getInstance().getKitManager().getKit("master"), p));
        getInventory().setItem(31, getKitItem(KitPvP.getInstance().getKitManager().getKit("king"), p));
        getInventory().setItem(32, getKitItem(KitPvP.getInstance().getKitManager().getKit("legend"), p));
        getInventory().setItem(33, getKitItem(KitPvP.getInstance().getKitManager().getKit("spartan"), p));
    }

    @Override
    public void open(Player p) {
        KitsGUI instance = instances.computeIfAbsent(p.getUniqueId().toString(), uuid -> new KitsGUI());
        instance.setItems(p);
        p.openInventory(instance.getInventory());
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(getInventory().getTitle())) {
            if (e.getCurrentItem() != null) {
                ItemStack item = e.getCurrentItem();
                if (!item.equals(GUI.FILLER)) {
                    if (!item.hasItemMeta()) return;
                    if (!item.getItemMeta().hasDisplayName()) return;

                    Player p = (Player) e.getWhoClicked();
                    Kit kit = KitPvP.getInstance().getKitManager().getKit(ChatColor.stripColor(item.getItemMeta().getDisplayName()
                            .substring(0, item.getItemMeta().getDisplayName().length() - 6)));
                    if (kit != null) {
                        if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                            kit.apply(KitPvP.getInstance().getUserManager().getUser(p));
                            e.getView().close();
                        } else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
                            kit.preview(KitPvP.getInstance().getUserManager().getUser(p));
                        }
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    private ItemStack getKitItem(Kit kit, Player p) {
        Userdata data = KitPvP.getInstance().getUserManager().getUser(p).getUserdata();
        return new ItemBuilder(kit.getMenuItem())
                .setName(kit.getColor() + String.valueOf(ChatColor.BOLD) + ChatUtil.capitalize(kit.getName()) + " &rKit")
                .addLore(" ")
                .addLore("&f&l(!) &eCooldown: &f" + ChatUtil.formatSeconds(kit.getCooldown()))
                .addLore(data.canUse(kit) ? "&f&l(!) &eAvailable: &f" + (data.getCooldown(kit) > 0 ? "&cIn " + ChatUtil.formatSeconds(data.getCooldown(kit)) : "&aNow") : "&cYou don't own this kit.")
                .addLore(" ")
                .addLore("&7(&6Left-Click&7) to use this kit.")
                .addLore("&7(&6Right-Click&7) to preview this kit.")
                .build();
    }
}
