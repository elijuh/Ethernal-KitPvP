package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SpartanKit extends Kit {
    public SpartanKit() {
        super("spartan", 86400, ChatColor.DARK_RED);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.INK_SACK).setDura(1).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).build(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(45).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).setAmount(1).build(),
                new ItemBuilder(Material.POTION).setDura(16428).build(),
                new ItemBuilder(Material.POTION).setDura(8226).build(),
                new ItemBuilder(Material.POTION).setDura(8226).build(),
                new ItemBuilder(Material.ENDER_PEARL).setAmount(2).build(),
                new ItemBuilder(Material.FISHING_ROD).build(),
                new ItemBuilder(Material.ARROW).setAmount(1).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).addEnchant(Enchantment.ARROW_KNOCKBACK, 2).addEnchant(Enchantment.DURABILITY, 2).addEnchant(Enchantment.ARROW_INFINITE, 1).build()
        );
    }
}
