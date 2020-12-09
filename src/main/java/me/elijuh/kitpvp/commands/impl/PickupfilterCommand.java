package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.elijuh.kitpvp.commands.SpigotCommand;
import me.elijuh.kitpvp.data.Userdata;
import me.elijuh.kitpvp.utils.ChatUtil;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PickupfilterCommand extends SpigotCommand {
    private final List<String> SUBCOMMANDS = Lists.newArrayList(
            "add",
            "remove",
            "clear",
            "list"
    );

    public PickupfilterCommand() {
        super("pickupfilter");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        List<String> completion = new ArrayList<>();
        if (args.length == 1) {
            for (String s : SUBCOMMANDS) {
                if (StringUtil.startsWithIgnoreCase(s, args[0])) {
                    completion.add(s);
                }
            }
        } else if (args.length > 1) {
            if (args[0].equalsIgnoreCase("add")) {
                for (Material type : Arrays.stream(Material.values()).filter(type -> Item.getById(type.getId()) != null).collect(Collectors.toList())) {
                    if (StringUtil.startsWithIgnoreCase(type.toString(), args[1])) {
                        completion.add(type.toString());
                    }
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                for (Material type : plugin.getUserManager().getUser(p).getUserdata().getPickupFilter()) {
                    if (StringUtil.startsWithIgnoreCase(type.toString(), args[1])) {
                        completion.add(type.toString());
                    }
                }
            } else {
                return ImmutableList.of();
            }
        } else {
            return ImmutableList.of();
        }
        return completion;
    }

    @Override
    public void onExecute(Player p, String[] args) {
        if (args.length > 0) {
            Userdata userdata = plugin.getUserManager().getUser(p).getUserdata();
            if (args.length > 1) {
                Material type;
                try {
                    type = Material.valueOf(args[1].toUpperCase());
                    if (Item.getById(type.getId()) == null) {
                        p.sendMessage(ChatUtil.color("&cInvalid item type, please use tab complete for a list of types."));
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    p.sendMessage(ChatUtil.color("&cInvalid item type, please use tab complete for a list of types."));
                    return;
                }
                switch (args[0].toLowerCase()) {
                    case "add": {
                        if (userdata.getPickupFilter().contains(type)) {
                            p.sendMessage(ChatUtil.color("&cThat item is already in your pickup filter!"));
                        } else {
                            userdata.getPickupFilter().add(type);
                            p.sendMessage(ChatUtil.color("&aSuccessfully added &f" + type.toString() + " &ato your pickup filter."));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                        }
                        break;
                    }
                    case "remove": {
                        if (userdata.getPickupFilter().contains(type)) {
                            userdata.getPickupFilter().remove(type);
                            p.sendMessage(ChatUtil.color("&aSuccessfully removed &f" + type.toString() + " &afrom your pickup filter."));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                        } else {
                            p.sendMessage(ChatUtil.color("&cThat item is not in your pickup filter!"));
                        }
                        break;
                    }
                    default: {
                        p.sendMessage(ChatUtil.color("&cUsage: /pickupfilter <add | remove | clear> [items...]"));
                    }
                }
            } else if (args[0].equalsIgnoreCase("clear")) {
                userdata.getPickupFilter().clear();
                p.sendMessage(plugin.getPrefix() + ChatUtil.color("&aSuccessfully cleared your pickup filter."));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            } else if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(ChatUtil.color("&6&lPickup Filter »"));
                if (userdata.getPickupFilter().isEmpty()) {
                    p.sendMessage(ChatUtil.color("&7&oEmpty!"));
                } else {
                    for (Material type : userdata.getPickupFilter()) {
                        p.sendMessage(ChatUtil.color("&8» &7" + type.toString()));
                    }
                    p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
                }
            } else {
                p.sendMessage(ChatUtil.color("&cUsage: /pickupfilter <add | remove | clear | list> [items...]"));
            }
        } else {
            p.sendMessage(ChatUtil.color("&cUsage: /pickupfilter <add | remove | clear | list> [items...]"));
        }
    }
}
