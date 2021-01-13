package me.elijuh.kitpvp.commands.impl;

import com.google.common.collect.Lists;
import me.elijuh.kitpvp.abilities.Ability;
import me.elijuh.kitpvp.commands.SpigotCommand;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.List;

public class AbilitiesCommand extends SpigotCommand {

    public AbilitiesCommand() {
        super("abilities");
    }

    @Override
    public List<String> onTabComplete(Player p, String[] args) {
        List<String> completion = Lists.newArrayList();
        switch (args.length) {
            case 1: {
                if (p.hasPermission("kitpvp.ability.admin")) {
                    for (String sub : new String[]{"give", "list"})
                    if (StringUtil.startsWithIgnoreCase(sub, args[0])) {
                        completion.add(sub);
                    }
                }
                break;
            }
            case 2: {
                if (p.hasPermission("kitpvp.abilities.admin") && args[0].equalsIgnoreCase("give")) {
                    for (Ability ability : plugin.getAbilityManager().getAbilities()) {
                        if (StringUtil.startsWithIgnoreCase(ability.getId(), args[1])) {
                            completion.add(ability.getId());
                        }
                    }
                }
                break;
            }
        }
        return completion;
    }

    @Override
    public void onExecute(Player p, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "give": {
                    if (p.hasPermission("kitpvp.abilities.admin")) {
                        if (args.length > 1) {
                            Ability ability = plugin.getAbilityManager().getAbility(args[1]);
                            if (ability == null) {
                                p.sendMessage(ChatUtil.color("&cThat ability does not exist! use &7/abilities list &cfor a list of abilities."));
                            } else {
                                int amount = 1;
                                if (args.length > 2) {
                                    try {
                                        amount = Math.max(Integer.parseInt(args[2]), 1);
                                    } catch (NumberFormatException e) {
                                        p.sendMessage(ChatUtil.color("&cPlease provide a valid amount!"));
                                        return;
                                    }
                                }
                                ItemStack item = ability.getItem();
                                item.setAmount(amount);

                                if (PlayerUtil.hasRoom(p, item)) {
                                    p.getInventory().addItem(item);
                                    p.sendMessage(ChatUtil.color("&7You have been given " + item.getItemMeta().getDisplayName() + " &fx" + amount + "&7!"));
                                } else {
                                    p.sendMessage(ChatUtil.color("&cYou do not have enough room in your inventory!"));
                                }
                            }
                        } else {
                            p.sendMessage(ChatUtil.color("&cUsage: /abilities give <ability> [amount]"));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /abilities"));
                    }
                    break;
                }
                case "list": {
                    if (p.hasPermission("kitpvp.abilities.admin")) {
                        p.sendMessage(ChatUtil.color("&4&lAbilities &8⏐ &7Listing all available abilities:"));
                        for (Ability ability : plugin.getAbilityManager().getAbilities()) {
                            p.sendMessage(ChatUtil.color("&c- &f" + ability.getId()));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color("&cUsage: /abilities"));
                    }
                    break;
                }
                default: {
                    p.sendMessage(ChatUtil.color("&cUsage: /abilities"));
                }
            }
        } else {
            plugin.getGuiManager().getGUI("abilities").open(p);
        }
    }
}
