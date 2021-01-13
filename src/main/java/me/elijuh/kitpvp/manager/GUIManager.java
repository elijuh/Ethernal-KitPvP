package me.elijuh.kitpvp.manager;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.gui.impl.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class GUIManager {
    private final Set<GUI> GUIs = new HashSet<>();

    public GUIManager() {
        List<Class<? extends GUI>> GUIS = ImmutableList.of(
                AbilitiesGUI.class,
                KitsGUI.class,
                LeaderboardGUI.class,
                ShopGUI.class,
                RepairGUI.class
        );
        for (Class<? extends GUI> gui : GUIS) {
            try {
                GUIs.add(gui.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GUI getGUI(String id) {
        for (GUI gui : GUIs) {
            if (gui.getId().equals(id)) {
                return gui;
            }
        }
        return null;
    }

}