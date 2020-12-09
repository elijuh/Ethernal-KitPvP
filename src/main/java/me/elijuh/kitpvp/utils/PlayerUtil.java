package me.elijuh.kitpvp.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;

@UtilityClass
public class PlayerUtil {

    public void sendActionBar(Player player, String message) {
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatUtil.color(message) + "\"}");
        p.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(cbc, (byte) 2));
    }

    public boolean hasRoom(Player p, ItemStack item) {
        Iterator<ItemStack> iterator = Arrays.stream(p.getInventory().getContents()).iterator();
        int maxAmount = 0;
        while (iterator.hasNext()) {
            ItemStack next = iterator.next();
            if (next == null) {
                maxAmount += item.getMaxStackSize();
            } else if (next.getType().equals(item.getType())) {
                maxAmount += next.getMaxStackSize() - next.getAmount();
            }
        }
        return item.getAmount() <= maxAmount;
    }

    public boolean isInSafezone(Player p) {
        for (ProtectedRegion region : WorldGuardPlugin.inst().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
            if (region.getFlag(DefaultFlag.PVP) == StateFlag.State.DENY) {
                return true;
            }
        }
        return false;
    }
}
