package me.elijuh.kitpvp.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class PlayerDamagePlayerEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Player damager;
    private final Player damaged;
    private final ItemStack weapon;
    private boolean cancelled;

    public PlayerDamagePlayerEvent(Player damager, Player damaged) {
        this.damager = damager;
        this.damaged = damaged;
        this.weapon = damaged.getItemInHand();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
