package me.elijuh.kitpvp.manager;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.elijuh.kitpvp.kits.Kit;
import me.elijuh.kitpvp.kits.impl.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class KitManager {
    private final Set<Kit> kits = new HashSet<>();

    public KitManager() {
        List<Class<? extends Kit>> KITS = ImmutableList.of(
                DefaultKit.class,
                DailyKit.class,
                WeeklyKit.class,
                ArcherKit.class,
                NobleKit.class,
                KingKit.class,
                EmperorKit.class,
                LegendKit.class,
                MasterKit.class
        );
        for (Class<? extends Kit> kit : KITS) {
            try {
                kits.add(kit.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Kit getKit(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equals(name.toLowerCase())) {
                return kit;
            }
        }
        return null;
    }

}
