package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DefaultKit extends Kit {
    public DefaultKit() {
        super("default", 20, ChatColor.GRAY);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.IRON_SWORD).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.IRON_SWORD).build(),
                new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(10).build(),
                new ItemBuilder(Material.ARROW).setAmount(16).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.DURABILITY, 1).build());
    }
}
