package me.elijuh.kitpvp.data;

import lombok.Getter;
import me.elijuh.kitpvp.KitPvP;
import org.bukkit.entity.Player;

public class CombatTimer {
    private static final KitPvP plugin = KitPvP.i();
    private final Player p;
    @Getter private int count;

    public CombatTimer(Player p) {
        this.p = p;
    }

    public void handleAttack() {
        User user = plugin.getUserManager().getUser(p);
        if (!isTagged()) {
            user.sendMessage(KitPvP.i().getPrefix() + "&cYou have entered combat with &7" + user.getLastFoughtWith().getPlayer().getName() + "&c!");
        }
        count = 200;
        user.getScoreboard().refresh();
    }

    public void end() {
        count = 0;
        User user = plugin.getUserManager().getUser(p);
        user.sendMessage(KitPvP.i().getPrefix() + "&aYou are no longer in combat!");
        user.getScoreboard().refresh();
    }

    public boolean isTagged() {
        return count > 0;
    }

    public void run() {
        if (isTagged()) {
            count--;
            if (count == 0) {
                end();
            }
        }
    }
}
