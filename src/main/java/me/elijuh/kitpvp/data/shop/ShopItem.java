package me.elijuh.kitpvp.data.shop;

import lombok.Data;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.MathUtil;
import me.elijuh.kitpvp.utils.PlayerUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public class ShopItem {
    private final ItemStack item;
    private final double price;
    private final ShopType type;

    public ShopItem(ItemStack item, double price, ShopType type) {
        this.item = item;
        this.price = MathUtil.roundTo(price, 2);
        this.type = type;
    }

    public void sell(Player player) {
        if (PlayerUtil.hasRoom(player, item)) {
            if (KitPvP.getInstance().getEconomy().has(player, price)) {
                KitPvP.getInstance().getEconomy().withdrawPlayer(player, price);
                player.getInventory().addItem(item);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                player.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&aYou have purchased &r" +
                        (item.getItemMeta().getDisplayName() == null ? ChatUtil.capitalize(item.getType().toString().toLowerCase().replace("_", " "))
                                : item.getItemMeta().getDisplayName()) + "&a!"));
            } else {
                player.sendMessage(ChatUtil.color("&cYou can't afford this item!"));
            }
        } else {
            player.sendMessage(ChatUtil.color("&cYou don't have room in your inventory!"));
        }
    }
}
