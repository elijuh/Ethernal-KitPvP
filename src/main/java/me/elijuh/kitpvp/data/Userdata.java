package me.elijuh.kitpvp.data;

import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.gui.impl.KitsGUI;
import me.elijuh.kitpvp.gui.impl.PreviewGUI;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.manager.DatabaseManager;
import me.elijuh.kitpvp.utils.MathUtil;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Userdata {
    private static final KitPvP plugin = KitPvP.getInstance();
    private final DatabaseManager databaseManager;
    private final CustomConfig customConfig;
    private final User user;
    private long lastPearl;
    private List<Material> pickupFilter;
    private PreviewGUI previewGUI;
    private KitsGUI kitsGUI;

    public Userdata(User user) {
        this.user = user;
        databaseManager = plugin.getDatabaseManager();
        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("INSERT IGNORE INTO userdata(UUID) VALUES(?)");
            statement.setString(1, user.getPlayer().getUniqueId().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customConfig = new CustomConfig("userdata", user.getPlayer().getUniqueId().toString() + ".yml");
        customConfig.getConfiguration().options().copyDefaults(true);
        customConfig.getConfiguration().addDefault("pickup-filter", new ArrayList<>());
        customConfig.save();
        pickupFilter = getConfigPickupFilter();
    }

    public boolean canUse(Kit kit) {
        return user.getPlayer().hasPermission("kits." + kit.getName()) || user.getPlayer().hasPermission("kits.*");
    }

    public void setCooldown(Kit kit) {
        customConfig.getConfiguration().set("timestamps." + kit.getName(), System.currentTimeMillis());
        customConfig.save();
    }

    public List<Material> getConfigPickupFilter() {
        List<Material> types = new ArrayList<>();
        for (String s : customConfig.getConfiguration().getStringList("pickup-filter")) {
            try {
                Material type = Material.valueOf(s);
                types.add(type);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return types;
    }

    public void save() {
        List<String> filter = new ArrayList<>();
        pickupFilter.forEach(type -> filter.add(type.toString()));
        customConfig.getConfiguration().set("pickup-filter", filter);
        customConfig.save();
    }

    public void handleDeath() {
        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("UPDATE userdata SET deaths = deaths + 1, streak = 0 WHERE UUID=?");
            statement.setString(1, user.getPlayer().getUniqueId().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user.getLastFoughtWith().getPlayer() != null) {
            user.sendMessage(plugin.getPrefix() + "You were killed by &6" + user.getLastFoughtWith().getPlayer().getName() + " &7with &c❤" +
                    Integer.parseInt(PlaceholderAPI.setPlaceholders(user.getLastFoughtWith().getPlayer(), "%core_health%")) / 2.0 + "&7.");
        }
    }

    public void handleKill() {
        int random = MathUtil.getRandom(15, 30);
        int streak = StatsUtil.getStat(user.getLastFoughtWith().getPlayer().getUniqueId().toString(), "streak");
        int money = streak > 20 ? random + streak : random;
        int exp = MathUtil.getRandom(50, 80);

        plugin.getEconomy().depositPlayer(user.getPlayer(), money);
        user.sendMessage(plugin.getPrefix() + "You have killed &6" + user.getLastFoughtWith().getPlayer().getName() + "&7.");
        user.sendActionBar("&f&l(!) &a+ $" + money + ", &b" + exp + " Exp");
        user.sendMessage("&f&l(!) &a+ $" + money + ", &b" + exp + " Exp");
        user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);

        StatsUtil.addStat(user.getPlayer().getUniqueId().toString(), "exp", exp);
        StatsUtil.addStat(user.getPlayer().getUniqueId().toString(), "kills", 1);
        StatsUtil.addStat(user.getPlayer().getUniqueId().toString(), "streak", 1);

        int level = StatsUtil.getStat(user.getPlayer().getUniqueId().toString(), "level");
        int currentExp = StatsUtil.getStat(user.getPlayer().getUniqueId().toString(), "exp");

        if (currentExp >= MathUtil.getNeededExp(level)) {
            StatsUtil.setStat(user.getPlayer().getUniqueId().toString(), "exp", 0);
            StatsUtil.addStat(user.getPlayer().getUniqueId().toString(), "level", 1);
            user.sendMessage(plugin.getPrefix() + "&e&lLevel Up! &7You have leveled up to level &e" + (level + 1) + "&7!");
        }
    }

    public boolean isOnCooldown(Kit kit) {
        return getCooldown(kit) > 0;
    }

    public long getCooldown(Kit kit) {
        return customConfig.getConfiguration().contains("timestamps." + kit.getName())
                ? Math.max(kit.getCooldown() * 1000 - (System.currentTimeMillis()
                - customConfig.getConfiguration().getLong("timestamps." + kit.getName())), 0) / 1000 : 0;
    }
}
