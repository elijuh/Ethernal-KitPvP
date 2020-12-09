package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.commands.SpigotCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopCommand extends SpigotCommand {
    public ShopCommand() {
        super("shop");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(Player p, String[] args) {
        KitPvP.getInstance().getGuiManager().getGUI("shop").open(p);
    }
}
