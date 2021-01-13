package me.elijuh.kitpvp.listeners;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.events.PlayerDamagePlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CustomEventHandler implements Listener {

    public CustomEventHandler() {
        Bukkit.getPluginManager().registerEvents(this, KitPvP.i());
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER) {
            PlayerDamagePlayerEvent event = new PlayerDamagePlayerEvent((Player) e.getDamager(), (Player) e.getEntity());
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
}
