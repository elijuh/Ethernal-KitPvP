package me.elijuh.kitpvp.abilities;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.MathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class Ability {
    private final Map<String, Long> cooldowns = Maps.newHashMap();
    private final String id;
    private final int cooldown;
    private final ItemStack item;
    private final boolean isAttack;
    private final boolean consumes;

    public long getCooldown(String name) {
        return Math.max(1000L * cooldown - (System.currentTimeMillis() - cooldowns.getOrDefault(name, 0L)), 0);
    }

    public boolean isOnCooldown(String name) {
        return getCooldown(name) > 0;
    }

    public void refundItem(Player p) {
        p.getInventory().addItem(item);
        p.sendMessage(ChatUtil.color("&7Your " + item.getItemMeta().getDisplayName() + " &7has been refunded."));
        cooldowns.remove(p.getName());
    }

    public final void handleInteract(PlayerInteractEvent e) {
        if (e.getItem() == null || isAttack) return;

        if (e.getItem().isSimilar(item)) {
            if (isOnCooldown(e.getPlayer().getName())) {
                e.getPlayer().sendMessage(ChatUtil.color("&4&lAbilities &8⏐ &r" + item.getItemMeta().getDisplayName() +
                        " &cis on cooldown for &7" + MathUtil.roundTo(getCooldown(e.getPlayer().getName()) / 1000.0, 1) + " &cmore seconds."));
                e.setCancelled(true);
            } else {
                if (e.getAction().toString().contains("RIGHT")) {
                    onInteract(e);
                    cooldowns.put(e.getPlayer().getName(), System.currentTimeMillis());

                    if (e.getItem().getAmount() > 1) {
                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                    } else e.getPlayer().setItemInHand(null);
                }
            }
        }
    }

    public final void handleAttack(EntityDamageByEntityEvent e) {
        ItemStack weapon = ((Player) e.getDamager()).getItemInHand();
        if (weapon == null || !isAttack) return;

        if (weapon.isSimilar(item)) {
            if (isOnCooldown(e.getDamager().getName())) {
                e.getDamager().sendMessage(ChatUtil.color("&4&lAbilities &8⏐ &r" + item.getItemMeta().getDisplayName() +
                        " &cis on cooldown for &7" + getCooldown(e.getDamager().getName()) + " &cmore seconds."));
            } else {
                onAttack(e);
                cooldowns.put(e.getDamager().getName(), System.currentTimeMillis());

                if (weapon.getAmount() > 1) {
                    weapon.setAmount(weapon.getAmount() - 1);
                } else ((Player) e.getDamager()).setItemInHand(null);
            }
        }
    }

    public abstract void onInteract(PlayerInteractEvent e);

    public abstract void onAttack(EntityDamageByEntityEvent e);
}
