package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ArcherKit extends Kit {

    public ArcherKit() {
        super("archer", 7200, ChatColor.RED);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.BOW).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.STONE_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.DURABILITY, 2).build(),
                new ItemBuilder(Material.LEATHER_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 5).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 5).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 5).build(),
                new ItemBuilder(Material.LEATHER_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 5).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(15).build(),
                new ItemBuilder(Material.ARROW).setAmount(1).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_KNOCKBACK, 2).addEnchant(Enchantment.ARROW_INFINITE, 1).build()
        );
    }
}
