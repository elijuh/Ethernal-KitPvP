package me.elijuh.kitpvp.data;

import lombok.Getter;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.utils.ChatUtil;
import org.bukkit.entity.Player;

public class CombatTimer {
    private static final KitPvP plugin = KitPvP.getInstance();
    private final Player p;
    @Getter private int count;

    public CombatTimer(Player p) {
        this.p = p;
    }

    public void handle() {
        User user = plugin.getUserManager().getUser(p);
        if (!isTagged()) {
            user.sendMessage(KitPvP.getInstance().getPrefix() + "&cYou have entered combat with &7" + user.getLastFoughtWith().getPlayer().getName() + "&c!");
        }
        count = 20;
        user.getScoreboard().refresh();
    }

    public void end() {
        count = 0;
        p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color("&aYou are no longer in combat!"));
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
