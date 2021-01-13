package me.elijuh.kitpvp.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Getter;
import me.elijuh.kitpvp.abilities.Ability;
import me.elijuh.kitpvp.abilities.impl.HealthBuffAbility;

import java.util.List;

@Getter
public class AbilityManager {
    private final List<Ability> abilities = Lists.newArrayList();

    public AbilityManager() {
        for (Class<? extends Ability> ability : ImmutableList.of(
                HealthBuffAbility.class
        )) {
            try {
                abilities.add(ability.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Ability getAbility(String id) {
        for (Ability ability : abilities) {
            if (ability.getId().equals(id)) {
                return ability;
            }
        }
        return null;
    }

}
