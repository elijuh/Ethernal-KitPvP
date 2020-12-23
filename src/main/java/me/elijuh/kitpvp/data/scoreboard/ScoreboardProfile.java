package me.elijuh.kitpvp.data.scoreboard;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.Pair;
import me.elijuh.kitpvp.data.User;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.StaffUtil;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.entity.Player;

import java.util.List;

public enum ScoreboardProfile {
    NORMAL,
    COMBAT,
    PEARL,
    COMBAT_PEARL,
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
                        new Pair<>("&c&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&c&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f" + kills),
                        new Pair<>("&8» &7Deaths: ", "&f" + deaths),
                        new Pair<>("&8» &7Streak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&c&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
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
                        new Pair<>("&c&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&c&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f" + kills),
                        new Pair<>("&8» &7Deaths: ", "&f" + deaths),
                        new Pair<>("&8» &7Streak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&c&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
                        new Pair<>("", ""),
                        new Pair<>("&c&lCombat: ", "&f%kitpvp_combattag%s"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case PEARL: {
                int level = StatsUtil.getStat(player.getUniqueId().toString(), "level");
                int kills = StatsUtil.getStat(player.getUniqueId().toString(), "kills");
                int deaths = StatsUtil.getStat(player.getUniqueId().toString(), "deaths");
                int streak = StatsUtil.getStat(player.getUniqueId().toString(), "streak");
                String color = ChatUtil.getLevelColor(level);
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&c&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&c&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f" + kills),
                        new Pair<>("&8» &7Deaths: ", "&f" + deaths),
                        new Pair<>("&8» &7Streak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&c&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
                        new Pair<>("", ""),
                        new Pair<>("&b&lEnderpearl: ", "&f%kitpvp_pearl%s"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case COMBAT_PEARL: {
                int level = StatsUtil.getStat(player.getUniqueId().toString(), "level");
                int kills = StatsUtil.getStat(player.getUniqueId().toString(), "kills");
                int deaths = StatsUtil.getStat(player.getUniqueId().toString(), "deaths");
                int streak = StatsUtil.getStat(player.getUniqueId().toString(), "streak");
                String color = ChatUtil.getLevelColor(level);
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&c&lLevel &8»", " &7[" + color + level + "&7]"),
                        new Pair<>("", ""),
                        new Pair<>("&c&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f" + kills),
                        new Pair<>("&8» &7Deaths: ", "&f" + deaths),
                        new Pair<>("&8» &7Streak: ", "&f" + streak),
                        new Pair<>("", ""),
                        new Pair<>("&c&lBalance &8» ", "&a$" + ChatUtil.formatMoney(KitPvP.getInstance().getEconomy().getBalance(player))),
                        new Pair<>("", ""),
                        new Pair<>("&c&lCombat: ", "&f%kitpvp_combattag%s"),
                        new Pair<>("&b&lEnderpearl: ", "&f%kitpvp_pearl%s"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case STAFF: {
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m------------"),
                        new Pair<>("&c&lStaff ", "&c&lMode »"),
                        new Pair<>("&8» &7Vanish: ", "%core_vanish%"),
                        new Pair<>("&8» &7Gamemode: ", "&f" + ChatUtil.capitalize(player.getGameMode().toString().toLowerCase())),
                        new Pair<>("&8» &7Players: ", "&f%core_playercount%"),
                        new Pair<>("&8» &7Chat: ", "&f%core_chat_cooldown%"),
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

    public static ScoreboardProfile match(Player player) {
        User user = KitPvP.getInstance().getUserManager().getUser(player);
        if (StaffUtil.isStaffMode(player)) {
            return ScoreboardProfile.STAFF;
        } else if (user.getCombatTimer().isTagged() && System.currentTimeMillis() - user.getUserdata().getLastPearl() < 15000) {
            return ScoreboardProfile.COMBAT_PEARL;
        } else if (user.getCombatTimer().isTagged()) {
            return COMBAT;
        } else if (System.currentTimeMillis() - user.getUserdata().getLastPearl() < 15000) {
            return PEARL;
        } else {
            return NORMAL;
        }
    }
}
