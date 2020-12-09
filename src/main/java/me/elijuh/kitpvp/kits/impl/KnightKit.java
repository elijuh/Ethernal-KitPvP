package me.elijuh.kitpvp.kits.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KnightKit extends Kit {
    public KnightKit() {
        super("knight", 86400, ChatColor.BLUE);
    }

    @Override
    public ItemStack getMenuItem() {
        return new ItemBuilder(Material.INK_SACK).setDura(4).build();
    }

    @Override
    public List<ItemStack> getItems() {
        return ImmutableList.of(
                new ItemBuilder(Material.DIAMOND_SWORD).setName("&9&lKnight &fSword").addEnchant(Enchantment.DAMAGE_ALL, 1).build(),
                new ItemBuilder(Material.DIAMOND_HELMET).setName("&9&lKnight &fHelmet").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&9&lKnight &fChestplate").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("&9&lKnight &fLeggings").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.DIAMOND_BOOTS).setName("&9&lKnight &fBoots").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setAmount(15).build(),
                new ItemBuilder(Material.GOLDEN_APPLE).setDura(1).setAmount(1).build(),
                new ItemBuilder(Material.POTION).setDura(8194).build(),
                new ItemBuilder(Material.ARROW).setAmount(24).build(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).build()
        );
    }
}
