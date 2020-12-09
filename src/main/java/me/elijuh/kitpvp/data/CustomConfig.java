package me.elijuh.kitpvp.data;

import lombok.Getter;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class CustomConfig {
    private static final KitPvP plugin = KitPvP.getInstance();
    private File file;
    private YamlConfiguration configuration;

    public CustomConfig(String... paths) {
        StringBuilder path = new StringBuilder(plugin.getDataFolder().toString() + File.separator);
        for (int i = 0; i < paths.length; i++) {
            path.append(paths[i]);
            if (i != paths.length - 1) {
                path.append(File.separator);
            }
        }
        file = new File(path.substring(0, path.length() - paths[paths.length - 1].length()));

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        file = new File(path.toString());

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&6&lKitPvP: &aNew file created: " + file.getName()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reload();
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
