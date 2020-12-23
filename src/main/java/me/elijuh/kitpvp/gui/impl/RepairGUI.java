package me.elijuh.kitpvp.gui.impl;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemBuilder;
import me.elijuh.kitpvp.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RepairGUI extends GUI {
    private static final Map<String, RepairGUI> instances = new HashMap<>();
    private final ItemStack FILLER = new ItemBuilder(Material.STAINED_GLASS_PANE).setDura(8).setName(" ").build();
    private ItemStack slot;

    public RepairGUI() {
        super("repair", 5, "&4Repair");
    }

    @Override
    public void setItems(Player p) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            getInventory().setItem(i, FILLER);
        }
        getInventory().setItem(13, null);
        getInventory().setItem(31, getRepairItem());
    }

    @Override
    public void open(Player p) {
        RepairGUI instance;
        if (!instances.containsKey(p.getUniqueId().toString())) {
            instance = instances.computeIfAbsent(p.getUniqueId().toString(), uuid -> new RepairGUI());
            instance.setItems(p);
        } else {
            instance = instances.get(p.getUniqueId().toString());
        }
        p.openInventory(instance.getInventory());
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(getInventory().getTitle())) {
            if (e.getCurrentItem() != null) {
                Player p = (Player) e.getWhoClicked();
                if (e.getCurrentItem().isSimilar(FILLER)) {
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getType() == Material.ANVIL) {
                    slot = e.getInventory().getItem(13);
                    if (canRepair(slot)) {
                        double price = getPrice(slot);
                        if (KitPvP.getInstance().getEconomy().has(p, price)) {
                            slot.setDurability((short) 0);
                            KitPvP.getInstance().getEconomy().withdrawPlayer(p, price);
                            p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&aYou have successfuly repaired your item!"));
                            p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&f&l(!) &c&l- $" + price));
                            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 2.0F);
                        } else {
                            p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&cYou can't afford that!"));
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 0.6F);
                        }
                    } else {
                        p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&cYou can't repair that item!"));
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 0.6F);
                    }
                    e.setCancelled(true);
                }
                Bukkit.getScheduler().runTask(KitPvP.getInstance(), () -> {
                    slot = e.getInventory().getItem(13);
                    e.getInventory().setItem(31, getRepairItem());
                });
            }
        }
    }

    public double getPrice(ItemStack item) {
        return MathUtil.roundTo(item.getDurability() * (item.getType().toString().startsWith("IRON_") ? 0.42 : 0.64), 0);
    }

    public ItemStack getRepairItem() {
        return new ItemBuilder(Material.ANVIL)
                .setName(canRepair(slot) ? "&2&lRepair" : "&7&lRepair")
                .addLore(" ")
                .addLore(canRepair(slot) ? "&ePrice: &6$" + getPrice(slot) : "")
                .addLore(canRepair(slot) ? "&a&nClick to repair." : (slot == null ? "&cPlease insert an item!" : "&cThis item cannot be repaired!"))
                .build();
    }

    public boolean canRepair(ItemStack item) {
        if (item == null) {
            return false;
        }
        return !item.getType().isBlock() && item.getType().getMaxDurability() > 0 && item.getDurability() != 0;
    }
}
