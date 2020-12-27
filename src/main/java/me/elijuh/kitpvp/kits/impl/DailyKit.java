package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DailyKit extends Kit {

    public DailyKit() {
        super("daily", 86400, ChatColor.YELLOW);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.EYE_OF_ENDER).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).build(),
                new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(20).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).setAmount(1).build(),
                new ItemBuilder(Material.POTION).setDura(8194).build(),
                new ItemBuilder(Material.ARROW).setAmount(1).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).build()
        );
    }
}
