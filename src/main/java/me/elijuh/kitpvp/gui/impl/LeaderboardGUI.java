package me.elijuh.kitpvp.gui.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.Pair;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class LeaderboardGUI extends GUI {
    private final List<Pair<String, Integer>> kills = new ArrayList<>();
    private final List<Pair<String, Integer>> deaths = new ArrayList<>();
    private final List<Pair<String, Integer>> streak = new ArrayList<>();
    public LeaderboardGUI() {
        super("leaderboard", 3, "&4Leaderboards");

        Bukkit.getScheduler().runTaskTimer(KitPvP.i(), ()-> {
            List<String> categories = ImmutableList.of("kills", "deaths", "streak");
            for (String category : categories) {
                try {
                    List<Pair<String, Integer>> lb = getLeaderboard(category);
                    lb.clear();
                    Statement statement = KitPvP.i().getDatabaseManager().getConnection().createStatement();
                    ResultSet result = statement.executeQuery("SELECT * FROM userdata");
                    result.findColumn(category);

                    while (result.next()) {
                        OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(result.getString("UUID")));
                        lb.add(new Pair<>(p.getName(), result.getInt(category)));
                    }

                    statement.close();

                    lb.sort(Comparator.comparingInt(Pair::getY));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0L, 1200L);
    }

    @Override
    public void setItems(Player p) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            getInventory().setItem(i, GUI.FILLER);
        }
        getInventory().setItem(11, getItem("kills"));
        getInventory().setItem(13, getItem("deaths"));
        getInventory().setItem(15, getItem("streak"));
    }

    @Override
    public void handle(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(getInventory().getTitle())) {
            e.setCancelled(true);
        }
    }

    private ItemStack getItem(String category) {
        if (!(category.equals("kills") || category.equals("deaths") || category.equals("streak"))) return null;

        ItemBuilder builder = new ItemBuilder();

        switch (category.toLowerCase()) {
            case "kills": {
                builder.setMaterial(Material.IRON_SWORD)
                        .setName("&6&lTop Kills")
                        .addEnchant(Enchantment.DAMAGE_ALL, 1)
                        .addFlag(ItemFlag.HIDE_ENCHANTS)
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES);
                break;
            }
            case "deaths": {
                builder.setMaterial(Material.SKULL_ITEM)
                        .setName("&6&lTop Deaths");
                break;
            }
            case "streak": {
                builder.setMaterial(Material.BLAZE_POWDER)
                        .setName("&6&lTop Streaks");
                break;
            }
        }

        for (int i = getLeaderboard(category).size() - 1; i > Math.max(getLeaderboard(category).size() - 6, -1); i--) {
            Pair<String, Integer> pair = getLeaderboard(category).get(i);
            builder.addLore("&6" + pair.getX() + " &7- &e" + pair.getY() + " " + (pair.getY() == 1 && !category.equals("streak")
                    ? ChatUtil.capitalize(category).substring(0, category.length() - 1) : ChatUtil.capitalize(category)));
        }

        return builder.build();
    }

    public List<Pair<String, Integer>> getLeaderboard(String category) {
        switch (category) {
            case "kills": {
                return this.kills;
            }
            case "deaths": {
                return this.deaths;
            }
            case "streak": {
                return this.streak;
            }
            default: {
                return null;
            }
        }
    }
}
