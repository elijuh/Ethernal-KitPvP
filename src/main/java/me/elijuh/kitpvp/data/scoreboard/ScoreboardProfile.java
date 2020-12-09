package me.elijuh.kitpvp.data.scoreboard;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.data.Pair;
import me.elijuh.kitpvp.utils.ChatUtil;
import org.bukkit.entity.Player;

import java.util.List;

public enum ScoreboardProfile {
    NORMAL,
    COMBAT,
    STAFF;

    public static List<Pair<String, String>> getLines(ScoreboardProfile profile, Player player) {
        switch (profile) {
            case NORMAL: {
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&6&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f%kitpvp_kills%"),
                        new Pair<>("&8» &7Deaths: ", "&f%kitpvp_deaths%"),
                        new Pair<>("&8» &7Streak: ", "&f%kitpvp_streak%"),
                        new Pair<>("&8» &7Level: ", "&f%kitpvp_level%"),
                        new Pair<>("", ""),
                        new Pair<>("&6Money: ", "&a$%vault_eco_balance_formatted%"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case COMBAT: {
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m--------"),
                        new Pair<>("&6&lStats »", ""),
                        new Pair<>("&8» &7Kills: ", "&f%kitpvp_kills%"),
                        new Pair<>("&8» &7Deaths: ", "&f%kitpvp_deaths%"),
                        new Pair<>("&8» &7Streak: ", "&f%kitpvp_streak%"),
                        new Pair<>("&8» &7Level: ", "&f%kitpvp_level%"),
                        new Pair<>("", ""),
                        new Pair<>("&6Balance: ", "&a$%vault_eco_balance_formatted%"),
                        new Pair<>("", ""),
                        new Pair<>("&cCombat: &f", "&f%kitpvp_combattag%s"),
                        new Pair<>("", ""),
                        new Pair<>("&7&oethernal", "&7&omc.com"),
                        new Pair<>("&7&m------------", "&7&m--------")
                );
            }
            case STAFF: {
                return ImmutableList.of(
                        new Pair<>("&7&m------------", "&7&m------------"),
                        new Pair<>("&6&lStaff ", "&6&lModules »"),
                        new Pair<>("&8» &eStaff Mode", "&7: " + "%core_staffmode%"),
                        new Pair<>("&8» &eVanish: ", "%core_vanish%"),
                        new Pair<>("&8» &eGamemode: ", "&6" + ChatUtil.capitalize(player.getGameMode().toString().toLowerCase())),
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
