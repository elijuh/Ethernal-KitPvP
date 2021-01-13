package me.elijuh.kitpvp.abilities.impl;

import com.google.common.collect.ImmutableList;
import me.elijuh.kitpvp.abilities.Ability;
import me.elijuh.kitpvp.events.PlayerDamagePlayerEvent;
import me.elijuh.kitpvp.utils.ChatUtil;
import me.elijuh.kitpvp.utils.ItemBuilder;
import me.elijuh.kitpvp.utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HealthBuffAbility extends Ability {
    private final List<PotionEffect> effects = ImmutableList.of(
            new PotionEffect(PotionEffectType.ABSORPTION, 600, 1),
            new PotionEffect(PotionEffectType.REGENERATION, 200, 1),
            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0)
    );

    public HealthBuffAbility() {
        super("health_buff", 60,
                new ItemBuilder(Material.SPECKLED_MELON).setName("&a&lHealth Buff")
                        .addLore("&7Right-Click to receive:")
                        .addLore("&7- &e4 Absorption hearts")
                        .addLore("&7- &eRegeneration 2 for 10s")
                        .addLore("&7- &eResistance 1 for 5s")
                        .build(),
                false,
                true);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        for (PotionEffect effect : effects) {
            PlayerUtil.applyEffect(p, effect);
        }
        p.sendMessage(ChatUtil.color("&4&lAbilities &8⏐ &7You have used a &a&lHealth Buff &7ability!"));
        p.playSound(p.getLocation(), Sound.EAT, 1.0F, 1.0F);
    }

    @Override
    public void onAttack(PlayerDamagePlayerEvent e) {

    }
}
