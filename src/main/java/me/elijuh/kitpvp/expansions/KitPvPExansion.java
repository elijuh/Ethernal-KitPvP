package me.elijuh.kitpvp.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.data.User;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.entity.Player;

public class KitPvPExansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "kitpvp";
    }

    @Override
    public String getAuthor() {
        return "elijuh";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        User user = KitPvP.i().getUserManager().getUser(p);
        if (user == null) {
            return "null";
        }
        switch (params.toLowerCase()) {
            case "level": {
                return String.valueOf(StatsUtil.getStat(p.getUniqueId().toString(), "level"));
            }
            case "exp": {
                return String.valueOf(StatsUtil.getStat(p.getUniqueId().toString(), "exp"));
            }
            case "kills": {
                return String.valueOf(StatsUtil.getStat(p.getUniqueId().toString(), "kills"));
            }
            case "deaths": {
                return String.valueOf(StatsUtil.getStat(p.getUniqueId().toString(), "deaths"));
            }
            case "streak": {
                return String.valueOf(StatsUtil.getStat(p.getUniqueId().toString(), "streak"));
            }
            case "combattag": {
                return String.valueOf(user.getCombatTimer().getCount() / 10D);
            }
            case "pearl": {
                long l = Math.round(150D - (System.currentTimeMillis() - user.getUserdata().getLastPearl()) / 100D);
                return String.valueOf(Math.max(l, 0) / 10.0);
            }
            default: {
                return "null";
            }
        }
    }
}
