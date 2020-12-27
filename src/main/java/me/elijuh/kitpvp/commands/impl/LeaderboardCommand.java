package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.commands.SpigotCommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaderboardCommand extends SpigotCommand {
    public LeaderboardCommand() {
        super("leaderboard", "lb", "topstats");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(Player p, String[] args) {
        KitPvP.i().getGuiManager().getGUI("leaderboard").open(p);
        p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
    }
}
