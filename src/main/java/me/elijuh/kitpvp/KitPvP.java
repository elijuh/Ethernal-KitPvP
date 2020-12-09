package me.elijuh.kitpvp;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.elijuh.kitpvp.commands.impl.*;
import me.elijuh.kitpvp.data.User;
import me.elijuh.kitpvp.listeners.PlayerListener;
import me.elijuh.kitpvp.manager.DatabaseManager;
import me.elijuh.kitpvp.manager.GUIManager;
import me.elijuh.kitpvp.manager.KitManager;
import me.elijuh.kitpvp.manager.UserManager;
import me.elijuh.kitpvp.placeholders.KitPvPExansion;
import me.elijuh.kitpvp.tasks.ScoreboardRefresh;
import me.elijuh.kitpvp.utils.ChatUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class KitPvP extends JavaPlugin {
    private static KitPvP instance;
    private DatabaseManager databaseManager;
    private Economy economy;
    private KitManager kitManager;
    private GUIManager guiManager;
    private UserManager userManager;
    private ScoreboardRefresh scoreboardRefresh;
    private KitPvPExansion expansion;
    private String prefix;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("mysql.host", "");
        getConfig().addDefault("mysql.database", "");
        getConfig().addDefault("mysql.username", "");
        getConfig().addDefault("mysql.password", "");
        saveConfig();

        instance = this;
        databaseManager = new DatabaseManager();
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        kitManager = new KitManager();
        guiManager = new GUIManager();
        userManager = new UserManager();
        scoreboardRefresh = new ScoreboardRefresh();
        expansion = new KitPvPExansion();
        prefix = ChatUtil.color("&6&lKitPvP &8» &7");

        expansion.register();

        if (!databaseManager.isConnected()) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&4ERROR | &6Failed to connect to database, plugin shutting down."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        scoreboardRefresh.runTaskTimerAsynchronously(this, 20L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(KitPvP.getInstance(), ()-> {
            for (User user : userManager.getUsers()) {
                if (user.getCombatTimer().isTagged()) {
                    user.getCombatTimer().run();
                }
            }
        }, 20L, 20L);

        new KitCommand();
        new ShopCommand();
        new StatsCommand();
        new LevelCommand();
        new LeaderboardCommand();
        new PickupfilterCommand();
        new PlayerListener();
    }

    public void onDisable() {
        instance = null;
        if (databaseManager != null) {
            databaseManager.disconnect();
            databaseManager = null;
        }
        economy = null;
        kitManager = null;
        guiManager = null;
        scoreboardRefresh = null;
        prefix = null;

        if (userManager != null) {
            for (User user : userManager.getUsers()) {
                if (user.getScoreboard().isEnabled()) {
                    user.getScoreboard().disable();
                }
                user.getUserdata().save();
            }
            userManager = null;
        }
        if (expansion != null) {
            PlaceholderAPI.unregisterExpansion(expansion);
            expansion = null;
        }
    }

    public static KitPvP getInstance() {
        return instance;
    }
}
