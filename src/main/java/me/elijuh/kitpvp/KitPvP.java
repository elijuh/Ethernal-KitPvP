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

import java.io.File;
import java.util.logging.Level;

@Getter
public class KitPvP extends JavaPlugin {
    private final String prefix = ChatUtil.color("&4&lKitPvP &8⏐ &7");
    private static KitPvP instance;
    private DatabaseManager databaseManager;
    private Economy economy;
    private KitManager kitManager;
    private GUIManager guiManager;
    private UserManager userManager;
    private ScoreboardRefresh scoreboardRefresh;
    private KitPvPExansion expansion;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("mysql.host", "localhost:3306");
        getConfig().addDefault("mysql.database", "kitpvp");
        getConfig().addDefault("mysql.username", "root");
        getConfig().addDefault("mysql.password", "");
        saveConfig();

        if (new File(getDataFolder() + File.separator + "userdata").mkdir()) {
            Bukkit.getLogger().log(Level.INFO, "Successfully created userdata folder.");
        }

        instance = this;
        databaseManager = new DatabaseManager();
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        kitManager = new KitManager();
        guiManager = new GUIManager();
        userManager = new UserManager();
        scoreboardRefresh = new ScoreboardRefresh();
        expansion = new KitPvPExansion();

        expansion.register();

        if (!databaseManager.isConnected()) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&4ERROR | &6Failed to connect to database, plugin shutting down."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        scoreboardRefresh.runTaskTimerAsynchronously(this, 2L, 2L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, ()-> {
                for (User user : userManager.getUsers()) {
                    if (user.getCombatTimer().isTagged()) {
                        user.getCombatTimer().run();
                    }
                }
        }, 2L, 2L);

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

    public static KitPvP i() {
        return instance;
    }
}
