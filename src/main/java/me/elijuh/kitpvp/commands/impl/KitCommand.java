package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.commands.SpigotCommand;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class KitCommand extends SpigotCommand {
    public KitCommand() {
        super("kit", "kits");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        if (args.length == 1) {
            List<String> completion = new ArrayList<>();
            for (Kit kit : plugin.getKitManager().getKits()) {
                if (StringUtil.startsWithIgnoreCase(kit.getName(), args[0])) {
                    completion.add(kit.getName());
                }
            }
            return completion;
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public void onExecute(Player p, String[] args) {
        if (args.length == 1) {
            Kit kit = plugin.getKitManager().getKit(args[0]);
            if (kit == null) {
                p.sendMessage(plugin.getPrefix() + ChatUtil.color("&7That kit does not exist!"));
            } else {
                kit.apply(plugin.getUserManager().getUser(p));
            }
        } else if (args.length == 0) {
            plugin.getGuiManager().getGUI("kits").open(p);
        } else {
            p.sendMessage(ChatUtil.color("&cToo many arguments provided!"));
        }
    }
}
