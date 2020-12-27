package me.elijuh.kitpvp.tasks;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRefresh extends BukkitRunnable {

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User user = KitPvP.i().getUserManager().getUser(p);
            if (user != null) {
                if (user.getScoreboard().isEnabled()) {
                    user.getScoreboard().refresh();
                }
            }
        }
    }
}
