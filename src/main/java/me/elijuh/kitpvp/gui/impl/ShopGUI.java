package me.elijuh.kitpvp.gui.impl;

import com.google.common.collect.Lists;
import me.elijuh.kitpvp.data.shop.ShopItem;
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
                new ShopItem(new ItemBuilder(Material.GOLDEN_APPLE).setAmount(16).build(), 6.99),
                new ShopItem(new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).build(), 749.99),
                new ShopItem(new ItemBuilder(Material.FISHING_ROD).build(), 59.99),
                new ShopItem(new ItemBuilder(Material.POTION).setDura(8194).build(), 14.99, "&fSpeed I Potion"),
                new ShopItem(new ItemBuilder(Material.POTION).setDura(8226).build(), 24.99, "&fSpeed II Potion"),
                new ShopItem(new ItemBuilder(Material.POTION).setDura(16460).build(), 129.99, "&fDamage I Potion"),
                new ShopItem(new ItemBuilder(Material.POTION).setDura(16428).build(), 349.99, "&fDamage II Potion"),
                new ShopItem(new ItemBuilder(Material.DIAMOND_SWORD).build(), 19.99),
                new ShopItem(new ItemBuilder(Material.DIAMOND_HELMET).build(), 29.99),
                new ShopItem(new ItemBuilder(Material.DIAMOND_CHESTPLATE).build(), 29.99),
                new ShopItem(new ItemBuilder(Material.DIAMOND_LEGGINGS).build(), 29.99),
                new ShopItem(new ItemBuilder(Material.DIAMOND_BOOTS).build(), 29.99),
                new ShopItem(new ItemBuilder(Material.BOW).build(), 4.99),
                new ShopItem(new ItemBuilder(Material.ARROW).setAmount(32).build(), 9.99)
            );

    public ShopGUI() {
        super("shop", 6, "&4Shop");
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

                if (!item.hasItemMeta()) return;

                if (item.getItemMeta().hasLore()) {
                    trimLore(item);
                    ShopItem shopItem = getShopItem(item);
                    if (shopItem != null && e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                        shopItem.sell((Player) e.getWhoClicked());
                    }
                }
                if (e.getCurrentItem().isSimilar(EXIT)) {
                    e.getView().close();
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
