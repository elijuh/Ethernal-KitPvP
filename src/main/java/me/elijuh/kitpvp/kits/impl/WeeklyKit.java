package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WeeklyKit extends Kit {

    public WeeklyKit() {
        super("weekly", 604800, ChatColor.YELLOW);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.ENDER_PORTAL_FRAME).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).build(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(20).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).setAmount(1).build(),
                new ItemBuilder(Material.POTION).setDura(8226).build(),
                new ItemBuilder(Material.POTION).setDura(8226).build(),
                new ItemBuilder(Material.ARROW).setAmount(1).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).addEnchant(Enchantment.ARROW_INFINITE, 1).build()
        );
    }
}
