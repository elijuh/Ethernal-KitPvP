package me.elijuh.kitpvp.manager;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.elijuh.kitpvp.gui.GUI;
import me.elijuh.kitpvp.gui.impl.KitsGUI;
import me.elijuh.kitpvp.gui.impl.LeaderboardGUI;
import me.elijuh.kitpvp.gui.impl.ShopGUI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class GUIManager {
    private final Set<GUI> GUIs = new HashSet<>();
    private final List<Class<? extends GUI>> GUIS = ImmutableList.of(
            KitsGUI.class,
            ShopGUI.class,
            LeaderboardGUI.class
    );

    public GUIManager() {
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