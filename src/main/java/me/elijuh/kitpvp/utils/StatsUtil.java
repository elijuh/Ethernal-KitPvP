package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;
import me.elijuh.kitpvp.KitPvP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class StatsUtil {
private static final Connection connection = KitPvP.getInstance().getDatabaseManager().getConnection();

    public int getStat(String uuid, String stat) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM userdata WHERE UUID = ?");
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(stat);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setStat(String uuid, String stat, int value) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userdata SET ? = ? WHERE UUID = ?");
            statement.setString(1, stat);
            statement.setString(2, String.valueOf(value));
            statement.setString(3, uuid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStat(String uuid, String stat, int value) {
        setStat(uuid, stat, getStat(uuid, stat) + value);
    }
}