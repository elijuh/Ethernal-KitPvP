package me.elijuh.kitpvp.kits;

import lombok.Getter;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.User;
import me.elijuh.kitpvp.gui.impl.PreviewGUI;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemUtil;
import me.elijuh.kitpvp.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public abstract class Kit {
    private static final KitPvP plugin = KitPvP.i();
    private final String name;
    private final long cooldown;
    private final ChatColor color;

    public Kit(String name, long cooldown, ChatColor color) {
        this.name = name.toLowerCase();
        this.cooldown = cooldown;
        this.color = color;
    }

    public void apply(User user) {
        Player p = user.getPlayer();
        if (!user.getUserdata().canUse(this)) {
            p.sendMessage(plugin.getPrefix() + ChatUtil.color("&7You don't have permission to use this kit!"));
            return;
        }
        if (user.getUserdata().isOnCooldown(this) && !user.getPlayer().hasPermission("kitpvp.bypasscooldown")) {
            p.sendMessage(plugin.getPrefix() + ChatUtil.color("&7That kit is on cooldown for another &e"
                    + ChatUtil.formatSeconds(user.getUserdata().getCooldown(this)) + "&7."));
            return;
        }
        boolean dropped = false;
        for (ItemStack item : getItems()) {
            if (ItemUtil.isArmor(item)) {
                switch(item.getType().toString().split("_")[1]) {
                    case "HELMET": {
                        if (p.getInventory().getHelmet() == null) {
                            p.getInventory().setHelmet(item);
                        } else if (PlayerUtil.hasRoom(p, item)) {
                            p.getInventory().addItem(item);
                        } else {
                            p.getWorld().dropItem(p.getLocation(), item);
                            dropped = true;
                        }
                        break;
                    }
                    case "CHESTPLATE": {
                        if (p.getInventory().getChestplate() == null) {
                            p.getInventory().setChestplate(item);
                        } else if (PlayerUtil.hasRoom(p, item)) {
                            p.getInventory().addItem(item);
                        } else {
                            p.getWorld().dropItem(p.getLocation(), item);
                            dropped = true;
                        }
                        break;
                    }
                    case "LEGGINGS": {
                        if (p.getInventory().getLeggings() == null) {
                            p.getInventory().setLeggings(item);
                        } else if (PlayerUtil.hasRoom(p, item)) {
                            p.getInventory().addItem(item);
                        } else {
                            p.getWorld().dropItem(p.getLocation(), item);
                            dropped = true;
                        }
                        break;
                    }
                    case "BOOTS": {
                        if (p.getInventory().getBoots() == null) {
                            p.getInventory().setBoots(item);
                        } else if (PlayerUtil.hasRoom(p, item)) {
                            p.getInventory().addItem(item);
                        } else {
                            p.getWorld().dropItem(p.getLocation(), item);
                            dropped = true;
                        }
                        break;
                    }
                }
            } else if (PlayerUtil.hasRoom(p, item)) {
                p.getInventory().addItem(item);
            } else {
                p.getWorld().dropItem(p.getLocation(), item);
                dropped = true;
            }
        }
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.5F, 1.0F);
        p.sendMessage(plugin.getPrefix() + ChatUtil.color("Kit &f" + ChatUtil.capitalize(getName()) + " &7has been applied."));
        if (dropped) {
            p.sendMessage(plugin.getPrefix() + ChatUtil.color("&cSome items could not fit in your inventory and have been dropped."));
        }
        user.getUserdata().setCooldown(this);
    }

    public void preview(User user) {
        user.getUserdata().setPreviewGUI(new PreviewGUI(this));
        PreviewGUI gui = user.getUserdata().getPreviewGUI();
        gui.open(user.getPlayer());
    }

    public abstract ItemStack getMenuItem();

    public abstract List<ItemStack> getItems();
}
