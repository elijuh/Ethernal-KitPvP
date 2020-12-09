package me.elijuh.kitpvp.manager;

import lombok.Getter;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Getter
public class UserManager implements Listener {
    private final Set<User> users = new HashSet<>();

    public UserManager() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            users.add(new User(p));
        }

        Bukkit.getPluginManager().registerEvents(this, KitPvP.getInstance());
    }

    public boolean dataExists(OfflinePlayer player) {
        try {
            PreparedStatement statement = KitPvP.getInstance().getDatabaseManager().getConnection().prepareStatement("SELECT * FROM userdata WHERE UUID=?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getPlayer().getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(Player p) {
        return getUser(p.getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerJoinEvent e) {
        users.add(new User(e.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerQuitEvent e) {
        getUser(e.getPlayer()).getScoreboard().disable();
        users.remove(getUser(e.getPlayer()));
    }

}
