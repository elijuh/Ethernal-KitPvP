package me.elijuh.kitpvp.listeners;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.User;
import me.elijuh.kitpvp.data.Userdata;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;

import java.util.Arrays;

public class PlayerListener implements Listener {
    private static final KitPvP plugin = KitPvP.getInstance();
    private final String[] CT_ALLOWED = {
            "msg", "m", "t", "w", "tell", "whisper", "message", "r", "reply", "sounds", "togglesounds",
            "ban", "tempban", "mute", "kick", "warn", "ss", "invsee", "inspect", "clearchat", "mod", "staff",
            "modmode", "staffmode", "h", "m", "v", "vanish", "fix", "eat", "feed", "ping", "latency"
    };

    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void handle(InventoryClickEvent e) {
        for (GUI gui : plugin.getGuiManager().getGUIs()) {
            gui.handle(e);
        }

        User user = plugin.getUserManager().getUser((Player) e.getWhoClicked());
        if (user != null) {
            if (user.getUserdata().getPreviewGUI() != null) {
                user.getUserdata().getPreviewGUI().handle(e);
            }
        }
    }

    @EventHandler
    public void on(InventoryDragEvent e) {
        if (e.getView().getTitle().equals(ChatUtil.color("&4Repair"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent e) {
        if (e.getView().getTitle().startsWith(ChatUtil.color("&4Preview:"))) {
            Bukkit.getScheduler().runTask(plugin, ()-> ((Player) e.getPlayer()).performCommand("kits"));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && e.getPlayer().hasPermission("kitpvp.build")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && e.getPlayer().hasPermission("kitpvp.build")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(ItemSpawnEvent e) {
        if (e.isCancelled()) return;

        Bukkit.getScheduler().runTaskLater(KitPvP.getInstance(), ()-> {
            if (e.getEntity() != null) {
                e.getEntity().remove();
            }
        }, 300L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;

        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            User hit = plugin.getUserManager().getUser((Player) e.getEntity());
            User attacker = plugin.getUserManager().getUser((Player) e.getDamager());

            if (hit != null && attacker != null) {
                hit.setLastFoughtWith(attacker);
                attacker.setLastFoughtWith(hit);

                hit.getCombatTimer().handle();
                attacker.getCombatTimer().handle();
            }
        } else if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
            Projectile projectile = ((Projectile) e.getDamager());

            if (!(projectile.getShooter() instanceof Player)) return;

            User hit = plugin.getUserManager().getUser((Player) e.getEntity());
            User attacker = plugin.getUserManager().getUser((Player) projectile.getShooter());

            if (hit != null && attacker != null) {
                hit.setLastFoughtWith(attacker);
                attacker.setLastFoughtWith(hit);

                hit.getCombatTimer().handle();
                attacker.getCombatTimer().handle();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(AsyncPlayerChatEvent e) {
        int level = StatsUtil.getStat(e.getPlayer().getUniqueId().toString(), "level");
        String color = ChatUtil.getLevelColor(level);
        e.setFormat(ChatUtil.color( "&7[" + color + level + "&7]&r ") + e.getFormat());
    }

    @EventHandler
    public void on(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        if (((Player) e.getEntity()).getFoodLevel() < 40) {
            ((Player) e.getEntity()).setFoodLevel(40);
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent e) {
        Userdata userdata = plugin.getUserManager().getUser(e.getPlayer()).getUserdata();
        if (userdata.getPickupFilter().contains(e.getItem().getItemStack().getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (plugin.getUserManager().getUser(p).getCombatTimer().isTagged() && !p.hasPermission("kitpvp.combattag.bypass")) {
            String command = e.getMessage().split(" ")[0].toLowerCase().substring(1);
            if (Arrays.stream(CT_ALLOWED).noneMatch(cmd -> cmd.equalsIgnoreCase(command))) {
                e.setCancelled(true);
                p.sendMessage(plugin.getPrefix() + ChatUtil.color("&cYou cannot execute that command in combat."));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerQuitEvent e) {
        User user = plugin.getUserManager().getUser(e.getPlayer());
        user.getUserdata().save();

        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && !StaffUtil.isVanished(e.getPlayer()) && !StaffUtil.isStaffMode(e.getPlayer())) {
            if (user.getCombatTimer().isTagged() && !PlayerUtil.isInSafezone(e.getPlayer())) {
                e.getPlayer().setHealth(0.0);
                Bukkit.broadcastMessage(plugin.getPrefix() + ChatUtil.color("&3" + e.getPlayer().getName() + " &bhas logged out in combat!"));
            }
        }
    }

    @EventHandler
    public void on(PlayerDeathEvent e) {
        e.setDeathMessage(null);

        User killed = plugin.getUserManager().getUser(e.getEntity());

        if (killed == null) return;

        User killer = killed.getLastFoughtWith();

        if (killer == null || killer == killed || !killed.getPlayer().isOnline() || !killer.getPlayer().isOnline()) return;

        int streak = StatsUtil.getStat(e.getEntity().getUniqueId().toString(), "streak");

        if (streak > 20) {
            Bukkit.broadcastMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&c" + killed.getPlayer().getName() + "'s &7Killstreak of &f" +
                    streak + " &7has been broken by &c" + killer.getPlayer().getName()) + "&7!");
        }

        killer.getUserdata().handleKill();
        killed.getUserdata().handleDeath();
        killed.getCombatTimer().end();
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.ANVIL) {
                e.setCancelled(true);
                plugin.getGuiManager().getGUI("repair").open(e.getPlayer());
            }
        }
        if (e.getAction().toString().contains("RIGHT")) {
            if (e.getPlayer().getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                User user = plugin.getUserManager().getUser(e.getPlayer());
                if (System.currentTimeMillis() - user.getUserdata().getLastPearl() < 15000) {
                    user.sendMessage(plugin.getPrefix() + ChatUtil.color("&cYou are currently on pearl cooldown for "
                            + MathUtil.roundTo((15.0 - (System.currentTimeMillis() - user.getUserdata().getLastPearl()) / 1000.0), 1) + "s!"));
                    e.setUseItemInHand(Event.Result.DENY);
                } else {
                    user.getUserdata().setLastPearl(System.currentTimeMillis());
                    user.getScoreboard().refresh();
                }
            }
        }
    }
}
