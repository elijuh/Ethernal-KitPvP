package me.elijuh.kitpvp.gui;

import lombok.Getter;
import me.elijuh.kitpvp.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@Getter
public abstract class GUI {
    private final String id;
    private final Inventory inventory;

    public GUI(String id, int size, String title) {
        this.id = id;
        inventory = Bukkit.createInventory(null, size * 9, ChatUtil.color(title));
    }

    public void open(Player p) {
        setItems(p);
        p.openInventory(inventory);
    }

    public abstract void setItems(Player p);

    public abstract void handle(InventoryClickEvent e);
}
