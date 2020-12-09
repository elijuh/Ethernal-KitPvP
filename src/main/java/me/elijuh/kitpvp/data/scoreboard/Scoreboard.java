package me.elijuh.kitpvp.data.scoreboard;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.Pair;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.StaffUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;

@Getter
public class Scoreboard {
    private static final ScoreboardManager manager = KitPvP.getInstance().getServer().getScoreboardManager();
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final org.bukkit.scoreboard.Scoreboard previous;
    private final Player player;
    private Objective objective;
    private boolean enabled;
    private ScoreboardProfile lastProfile;

    private List<Pair<String, String>> lines;

    public Scoreboard(Player player) {
        this.player = player;
        this.previous = player.getScoreboard();
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("sb", "dummy");

        objective.setDisplayName(ChatUtil.color("&6&lEthernal &7| &fKitPvP"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void enable() {
        if (!isEnabled()) {
            lastProfile = ScoreboardProfile.NORMAL;
            if (StaffUtil.isStaffMode(player)) {
                lastProfile = ScoreboardProfile.STAFF;
            } else if (KitPvP.getInstance().getUserManager().getUser(player).getCombatTimer().isTagged()) {
                lastProfile = ScoreboardProfile.COMBAT;
            }
            lines =  ScoreboardProfile.getLines(lastProfile, player);

            int score = lines.size();
            for (int i = 0; i < lines.size(); i++) {
                Team team = scoreboard.getTeam(ChatColor.values()[i].toString()) == null ? scoreboard.registerNewTeam(ChatColor.values()[i].toString()) :
                        scoreboard.getTeam(ChatColor.values()[i].toString());

                team.addEntry(ChatColor.values()[i].toString());
                String prefix = PlaceholderAPI.setPlaceholders(player, lines.get(i).getX());
                String suffix = PlaceholderAPI.setPlaceholders(player, lines.get(i).getY());
                if (prefix.length() > 16) {
                    prefix = prefix.substring(0, 15);
                }
                if (suffix.length() > 16) {
                    suffix = suffix.substring(0, 15);
                }

                team.setPrefix(prefix);
                team.setSuffix(suffix);

                objective.getScore(ChatColor.values()[i].toString()).setScore(score);
                score--;
            }

            player.setScoreboard(scoreboard);
            enabled = true;
        }
    }

    public void disable() {
        if (isEnabled()) {
            player.setScoreboard(previous);
            enabled = false;
        }
    }

    public void refresh() {
        ScoreboardProfile profile = ScoreboardProfile.NORMAL;
        if (StaffUtil.isStaffMode(player)) {
            profile = ScoreboardProfile.STAFF;
        } else if (KitPvP.getInstance().getUserManager().getUser(player).getCombatTimer().isTagged()) {
            profile = ScoreboardProfile.COMBAT;
        }
        lines = ScoreboardProfile.getLines(profile, player);

        if (profile != lastProfile) {
            disable();
            scoreboard.getTeams().forEach(Team::unregister);
            objective.unregister();
            objective = scoreboard.registerNewObjective("sb", "dummy");
            objective.setDisplayName(ChatUtil.color("&6&lEthernal &7| &fKitPvP"));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (int i = 0; i < lines.size(); i++) {
            boolean created = false;
            Team team = scoreboard.getTeam(ChatColor.values()[i].toString());

            if (team == null) {
                team = scoreboard.registerNewTeam(ChatColor.values()[i].toString());
                created = true;

                String prefix = PlaceholderAPI.setPlaceholders(player, lines.get(i).getX());
                if (prefix.length() > 16) {
                    prefix = prefix.substring(0, 15);
                }

                team.addEntry(ChatColor.values()[i].toString());
                team.setPrefix(prefix);
            }

            String suffix = PlaceholderAPI.setPlaceholders(player, lines.get(i).getY());
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 15);
            }

            team.setSuffix(suffix);

            if (created) {
                objective.getScore(ChatColor.values()[i].toString()).setScore(lines.size() - i);
            }
        }

        if (profile != lastProfile) {
            lastProfile = profile;
            player.setScoreboard(scoreboard);
            enabled = true;
        }
    }
}
