package me.elijuh.kitpvp.data;

import lombok.Getter;
import lombok.Setter;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.scoreboard.Scoreboard;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter @Setter
public class User {
    private static final KitPvP plugin = KitPvP.i();
    private final Player player;
    private final Userdata userdata;
    private final Scoreboard scoreboard;
    private final CombatTimer combatTimer;
    private User lastFoughtWith;

    public User(Player player) {
        this.player = player;
        userdata = new Userdata(this);
        combatTimer = new CombatTimer(player);
        scoreboard = new Scoreboard(player);

        Bukkit.getScheduler().runTaskLater(plugin, scoreboard::enable, 20L);
    }

    public void sendMessage(String s) {
        player.sendMessage(ChatUtil.color(s));
    }

    public void sendActionBar(String s) {
        PlayerUtil.sendActionBar(player, s);
    }
}
