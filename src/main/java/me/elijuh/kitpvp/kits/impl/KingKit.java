package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KingKit extends Kit {
    public KingKit() {
        super("king", 86400, ChatColor.DARK_GREEN);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.INK_SACK).setDura(2).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).build(),
                new ItemBuilder(Material.IRON_HELMET).setName("&2&lKing &fHelmet").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).setName("&2&lKing &fChestplate").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).setName("&2&lKing &fLeggings").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.IRON_BOOTS).setName("&2&lKing &fBoots").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.DURABILITY, 1).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(25).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).setAmount(1).build(),
                new ItemBuilder(Material.POTION).setDura(16460).build(),
                new ItemBuilder(Material.POTION).setDura(16460).build(),
                new ItemBuilder(Material.POTION).setDura(8194).build(),
                new ItemBuilder(Material.ARROW).setAmount(1).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_KNOCKBACK, 2).addEnchant(Enchantment.DURABILITY, 1).addEnchant(Enchantment.ARROW_INFINITE, 1).build()
        );
    }
}
