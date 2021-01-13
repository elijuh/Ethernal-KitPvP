package me.elijuh.kitpvp.gui.impl;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.abilities.Ability;
import me.elijuh.kitpvp.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class AbilitiesGUI extends GUI {

    public AbilitiesGUI() {
        super("abilities", 6, "&4&lAbilities");
    }

    @Override
    public void setItems(Player p) {
        int itemIndex = 0;
        for (int i = 0; i < getInventory().getSize(); i++) {
            List<Ability> abilities = KitPvP.i().getAbilityManager().getAbilities();
            if (i % 9 != 8 && i % 9 != 0 && i > 9 && i < 44) {
                if (itemIndex < abilities.size()) {
                    getInventory().setItem(i, abilities.get(itemIndex).getItem());
                    itemIndex++;
                }
            } else {
                getInventory().setItem(i, GUI.FILLER);
            }
        }
    }

    @Override
    public void handle(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(getInventory().getTitle())) {
            event.setCancelled(true);
        }
    }
}
