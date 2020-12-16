package me.elijuh.kitpvp.manager;

import com.zaxxer.hikari.HikariDataSource;
import me.elijuh.kitpvp.KitPvP;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private HikariDataSource dataSource;

    public DatabaseManager() {
        init();
    }

    private void init() {
        Connection c;
        String host = KitPvP.getInstance().getConfig().getString("mysql.host");
        String database = KitPvP.getInstance().getConfig().getString("mysql.database");
        String username = KitPvP.getInstance().getConfig().getString("mysql.username");
        String password = KitPvP.getInstance().getConfig().getString("mysql.password");
        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://" + host + "/" + database + "?useSSL=false&autoReconnect=true");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setConnectionInitSql("CREATE TABLE IF NOT EXISTS userdata(UUID VARCHAR(36) PRIMARY KEY, level INT DEFAULT 0, exp INT DEFAULT 0, kills INT DEFAULT 0, deaths INT DEFAULT 0, streak INT DEFAULT 0)");
        dataSource.setMinimumIdle(100);
        dataSource.setMaximumPoolSize(10000);
        dataSource.setAutoCommit(true);
        try {
            c = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            c = null;
        }
        connection = c;
    }

    public Connection getConnection() {
        if (!isConnected()) {
            init();
        }
        return connection;
    }

    public boolean isConnected() {
        if (connection != null) {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
