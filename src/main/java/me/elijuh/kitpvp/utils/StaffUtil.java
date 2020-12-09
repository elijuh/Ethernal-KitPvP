package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

@UtilityClass
public class StaffUtil {

    public boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean())
                return true;
        }
        return false;
    }

    public boolean isStaffMode(Player player) {
        for (MetadataValue meta : player.getMetadata("staffmode")) {
            if (meta.asBoolean())
                return true;
        }
        return false;
    }

    public boolean isFrozen(Player player) {
        for (MetadataValue meta : player.getMetadata("frozen")) {
            if (meta.asBoolean())
                return true;
        }
        return false;
    }
}
