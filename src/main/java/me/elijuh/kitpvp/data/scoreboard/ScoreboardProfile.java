package me.elijuh.kitpvp.data.scoreboard;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.Pair;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.entity.Player;

import java.util.List;

public enum ScoreboardProfile {
    NORMAL,
    COMBAT,
    STAFF;

    public static List<Pair<String, String>> getLines(ScoreboardProfile profile, Player player) {
        switch (profile) {
            case NORMAL: {
                int level = StatsUtil.getStat(player.getUniqueId().toString(), "level");
                int kills = StatsUtil.getStat(player.getUniqueId().toString(), "kills");
                int deaths = StatsUtil.getStat(player.getUniqueId().toString(), "deaths");
                int streak = StatsUtil.getStat(player.getUniqueId().toString(), "streak");
                String color = ChatUtil.getLevelColor(level);
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&6&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&6&lStats »", ""),
                        new Pair<>("&8» &eKills: ", "&f" + kills),
                        new Pair<>("&8» &eDeaths: ", "&f" + deaths),
                        new Pair<>("&8» &eStreak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&6&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case COMBAT: {
                int level = StatsUtil.getStat(player.getUniqueId().toString(), "level");
                int kills = StatsUtil.getStat(player.getUniqueId().toString(), "kills");
                int deaths = StatsUtil.getStat(player.getUniqueId().toString(), "deaths");
                int streak = StatsUtil.getStat(player.getUniqueId().toString(), "streak");
                String color = ChatUtil.getLevelColor(level);
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&6&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&6&lStats »", ""),
                        new Pair<>("&8» &eKills: ", "&f" + kills),
                        new Pair<>("&8» &eDeaths: ", "&f" + deaths),
                        new Pair<>("&8» &eStreak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&6&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
                        new Pair<>("", ""),
                        new Pair<>("&cCombat: ", "&f%kitpvp_combattag%s"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case STAFF: {
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m------------"),
                        new Pair<>("&6&lStaff ", "&6&lMode »"),
                        new Pair<>("&8» &eVanish: ", "%core_vanish%"),
                        new Pair<>("&8» &eGamemode: ", "&f" + ChatUtil.capitalize(player.getGameMode().toString().toLowerCase())),
                        new Pair<>("&8» &ePlayers: ", "&f%core_playercount%"),
                        new Pair<>("&8» &eChat: ", "&f%core_chat_cooldown%"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m------------")
                );
            }
            default: {
                return ImmutableList.of();
            }
        }
    }
}
