package me.elijuh.kitpvp.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ItemUtil {

    public boolean isArmor(final ItemStack itemStack) {
        if (itemStack == null)
            return false;
        final String type = itemStack.getType().name();
        return type.endsWith("_HELMET")
                || type.endsWith("_CHESTPLATE")
                || type.endsWith("_LEGGINGS")
                || type.endsWith("_BOOTS");
    }
}
