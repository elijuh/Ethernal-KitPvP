package me.elijuh.kitpvp.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder() {
        this.item = new ItemStack(Material.DIRT);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder setMaterial(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemBuilder setDura(int dura) {
        this.item.setDurability((short) dura);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(Math.min(amount, 64));
        return this;
    }

    public ItemBuilder setName(String name) {
        this.meta.setDisplayName(ChatUtil.color(name));
        return this;
    }

    public ItemBuilder addLore(String line) {
        if (line.isEmpty()) return this;

        List<String> lore = this.meta.hasLore() ? this.meta.getLore() : new ArrayList<>();
        lore.add(ChatUtil.color("&r" + line));
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        this.meta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return item;
    }
}
