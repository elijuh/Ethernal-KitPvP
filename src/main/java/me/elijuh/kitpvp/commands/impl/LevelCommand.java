package me.elijuh.kitpvp.commands.impl;

import me.elijuh.kitpvp.KitPvP;
import me.elijuh.kitpvp.commands.SpigotCommand;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.MathUtil;
import me.elijuh.kitpvp.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class LevelCommand extends SpigotCommand {
    public LevelCommand() {
        super("level", "levels", "experience", "exp");
    }
    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        return null;
    }

    @Override
    public void onExecute(Player p, String[] args) {
        OfflinePlayer target = p;
        if (args.length > 0) {
            target = Bukkit.getOfflinePlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatUtil.color("&cThat player does not have a profile!"));
                return;
            }
        }

        int level = StatsUtil.getStat(target.getUniqueId().toString(), "level");
        int exp = StatsUtil.getStat(target.getUniqueId().toString(), "exp");
        int needed = MathUtil.getNeededExp(level) - exp;

        p.sendMessage(KitPvP.getInstance().getPrefix() + ChatUtil.color(
                (target.getName().equals(p.getName()) ? "&7You currently have &e" : "&6" + target.getName() + " &7currently has &e") + level
                        + (level == 1 ? " &7level" : " &7levels") + " &8(&b" + exp + "/" + MathUtil.getNeededExp(level)
                        + " &7Until level " + (level + 1) + "&8)"));
    }
}
