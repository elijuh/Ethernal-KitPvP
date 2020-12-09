package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.commands.SpigotCommand;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsCommand extends SpigotCommand {
    private final KitPvP plugin = KitPvP.getInstance();

    public StatsCommand() {
        super("stats");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        List<String> completion = new ArrayList<>();
        switch (args.length) {
            case 1: {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (StringUtil.startsWithIgnoreCase(all.getName(), args[0])) {
                        completion.add(all.getName());
                    }
                }
                return completion;
            }
            case 2: {
                if (p.hasPermission("kitpvp.stats.edit")) {
                    for (String s : ImmutableList.of(
                            "setlevel",
                            "setkills",
                            "setdeaths",
                            "setstreak",
                            "reset"
                    )) {
                        if (StringUtil.startsWithIgnoreCase(s, args[1])) {
                            completion.add(s);
                        }
                    }
                }
            }
            default: {
                return ImmutableList.of();
            }
        }
    }

    @Override
    public void onExecute(Player p, String[] args) {
        OfflinePlayer target = p;
        if (args.length > 0) {
            target = Bukkit.getOfflinePlayer(args[0]);
            if (!plugin.getUserManager().dataExists(target)) {
                p.sendMessage(plugin.getPrefix() + ChatUtil.color("&cThat player does not have a profile."));
                return;
            }
        }

        if (args.length > 1) {
            if (!p.hasPermission("kitpvp.stats.edit")) {
                p.sendMessage(ChatUtil.color("&cUsage: /stats <player>"));
                return;
            }
            switch (args[1].toLowerCase()) {
                case "reset": {
                    if (plugin.getUserManager().dataExists(target)) {
                        try {
                            PreparedStatement statement = plugin.getDatabaseManager().getConnection()
                                    .prepareStatement("UPDATE userdata SET level = 0, kills = 0, deaths = 0, streak = 0 WHERE UUID=?");
                            statement.setString(1, target.getUniqueId().toString());
                            statement.executeUpdate();
                            statement.close();
                            p.sendMessage(ChatUtil.color("&aStats for " + target.getName() + " were reset."));
                        } catch (SQLException e) {
                            p.sendMessage(ChatUtil.color("&cError occured while trying to reset data profile."));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cThat player does not have a profile."));
                    }
                    break;
                }
                case "setlevel": {
                    if (args.length > 2) {
                        try {
                            int level = Integer.parseInt(args[2]);
                            PreparedStatement statement = plugin.getDatabaseManager().getConnection()
                                    .prepareStatement("UPDATE userdata SET level=? WHERE UUID=?");
                            statement.setString(1, String.valueOf(level));
                            statement.setString(2, target.getUniqueId().toString());
                            statement.executeUpdate();
                            statement.close();
                            p.sendMessage(ChatUtil.color("&aStats for " + target.getName() + " were updated."));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatUtil.color("&cPlease provide a valid integer!"));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /stats <player> setlevel <level>"));
                    }
                    break;
                }
                case "setkills": {
                    if (args.length > 2) {
                        try {
                            int kills = Integer.parseInt(args[2]);
                            PreparedStatement statement = plugin.getDatabaseManager().getConnection()
                                    .prepareStatement("UPDATE userdata SET kills=? WHERE UUID=?");
                            statement.setString(1, String.valueOf(kills));
                            statement.setString(2, target.getUniqueId().toString());
                            statement.executeUpdate();
                            statement.close();
                            p.sendMessage(ChatUtil.color("&aStats for " + target.getName() + " were updated."));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatUtil.color("&cPlease provide a valid integer!"));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /stats <player> setkills <kills>"));
                    }
                    break;
                }
                case "setdeaths": {
                    if (args.length > 2) {
                        try {
                            int deaths = Integer.parseInt(args[2]);
                            PreparedStatement statement = plugin.getDatabaseManager().getConnection()
                                    .prepareStatement("UPDATE userdata SET deaths=? WHERE UUID=?");
                            statement.setString(1, String.valueOf(deaths));
                            statement.setString(2, target.getUniqueId().toString());
                            statement.executeUpdate();
                            statement.close();
                            p.sendMessage(ChatUtil.color("&aStats for " + target.getName() + " were updated."));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatUtil.color("&cPlease provide a valid integer!"));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /stats <player> setdeaths <deaths>"));
                    }
                    break;
                }
                case "setstreak": {
                    if (args.length > 2) {
                        try {
                            int streak = Integer.parseInt(args[2]);
                            PreparedStatement statement = plugin.getDatabaseManager().getConnection()
                                    .prepareStatement("UPDATE userdata SET streak=? WHERE UUID=?");
                            statement.setString(1, String.valueOf(streak));
                            statement.setString(2, target.getUniqueId().toString());
                            statement.executeUpdate();
                            statement.close();
                            p.sendMessage(ChatUtil.color("&aStats for " + target.getName() + " were updated."));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatUtil.color("&cPlease provide a valid integer!"));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /stats <player> setstreak <streak>"));
                    }
                    break;
                }
                default: {
                    p.sendMessage(ChatUtil.color("&cUsage: /stats <player>"));
                }
            }
        } else {
            int kills = StatsUtil.getStat(target.getUniqueId().toString(), "kills");
            int deaths = StatsUtil.getStat(target.getUniqueId().toString(), "deaths");
            int streak = StatsUtil.getStat(target.getUniqueId().toString(), "streak");
            int level = StatsUtil.getStat(target.getUniqueId().toString(), "level");

            p.sendMessage(" ");
            p.sendMessage(plugin.getPrefix() + ChatUtil.color("&7Showing Stats for &6" + target.getName() + "&7:"));
            p.sendMessage(ChatUtil.color("&8» &eKills: &f" + kills));
            p.sendMessage(ChatUtil.color("&8» &eDeaths: &f" + deaths));
            p.sendMessage(ChatUtil.color("&8» &eStreak: &f" + streak));
            p.sendMessage(ChatUtil.color("&8» &eLevel: &f" + level));
        }
    }
}
