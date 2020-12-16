package me.elijuh.kitpvp.gui.impl;

import com.google.common.collect.Lists;
import me.elijuh.kitpvp.data.shop.ShopItem;
import me.elijuh.kitpvp.data.shop.ShopType;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopGUI extends GUI {
    private static final Map<String, ShopGUI> instances = new HashMap<>();
    private static final ItemStack EXIT = new ItemBuilder(Material.NETHER_STAR).setName("&6» &eExit &6«").build();
    private static final List<ShopItem> shopItems = Lists.newArrayList(
                new ShopItem(new ItemBuilder(Material.DIAMOND_SWORD).build(), 26.92, ShopType.ITEM)
            );

    public ShopGUI() {
        super("shop", 6, "&6Shop");
    }

    @Override
    public void setItems(Player p) {
        getInventory().clear();
        int itemIndex = 0;
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (i % 9 != 8 && i % 9 != 0 && i > 9 && i < 44) {
                if (itemIndex < shopItems.size()) {
                    getInventory().setItem(i, getDisplay(shopItems.get(itemIndex)));
                    itemIndex++;
                }
            } else {
                getInventory().setItem(i, GUI.FILLER);
            }
        }
        getInventory().setItem(49, EXIT);
    }

    @Override
    public void open(Player p) {
        ShopGUI instance = instances.computeIfAbsent(p.getUniqueId().toString(), uuid -> new ShopGUI());
        instance.setItems(p);
        p.openInventory(instance.getInventory());
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(getInventory().getTitle())) {
            if (e.getCurrentItem() != null) {
                ItemStack item = e.getCurrentItem().clone();
                if (item.getItemMeta().hasLore()) {
                    trimLore(item);
                    ShopItem shopItem = getShopItem(item);
                    if (shopItem != null && e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                        shopItem.sell((Player)e.getWhoClicked());
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    private void trimLore(ItemStack item) {
        if (item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore().size() >= 3) {
                List<String> lore = item.getItemMeta().getLore();
                lore.subList(lore.size() - 3, lore.size()).clear();
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    private ItemStack getDisplay(ShopItem shopItem) {
        return new ItemBuilder(shopItem.getItem())
                .addLore(" ")
                .addLore("&ePrice: &6$" + shopItem.getPrice())
                .addLore("&a&nClick to purchase this item.")
                .build();
    }

    private ShopItem getShopItem(ItemStack item) {
        for (ShopItem shopItem : shopItems) {
            if (shopItem.getItem().equals(item)) {
                return shopItem;
            }
        }
        return null;
    }
}
