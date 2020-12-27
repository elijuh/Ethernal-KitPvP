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
    private final String display;

    public ShopItem(ItemStack item, double price) {
        this(item, price, null);
    }

    public ShopItem(ItemStack item, double price, String display) {
        this.item = item;
        this.price = MathUtil.roundTo(price, 2);
        if (display != null) {
            display = ChatUtil.color(display);
        }
        this.display = display;
    }

    public void sell(Player player) {
        if (PlayerUtil.hasRoom(player, item)) {
            if (KitPvP.i().getEconomy().has(player, price)) {
                KitPvP.i().getEconomy().withdrawPlayer(player, price);
                player.getInventory().addItem(item);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                player.sendMessage(KitPvP.i().getPrefix() + ChatUtil.color("&7You have purchased &r" +
                        (display == null ? ChatUtil.capitalize(item.getType().toString().toLowerCase().replace("_", " "))
                                : display) + " &7for &a$" + getPrice() + "&7!"));
            } else {
                player.sendMessage(ChatUtil.color("&cYou can't afford this item!"));
            }
        } else {
            player.sendMessage(ChatUtil.color("&cYou don't have room in your inventory!"));
        }
    }
}
