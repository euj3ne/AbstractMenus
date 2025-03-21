package ru.abstractmenus.util.bukkit;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ru.abstractmenus.api.Logger;

import java.util.UUID;

public final class Skulls {

    private Skulls() {
    }

    public static ItemStack getCustomSkull(String texture) {
        UUID uuid = UUID.nameUUIDFromBytes(("Skull:" + texture).getBytes());

        PlayerProfile profile = Bukkit.createProfile(uuid);
        profile.setProperty(new ProfileProperty("textures", texture));

        return getCustomSkull(profile);
    }

    public static ItemStack getCustomSkull(com.destroystokyo.paper.profile.PlayerProfile profile) {
        ItemStack head = createSkullItem();

        if (profile == null) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        if (headMeta == null) return null;

        headMeta.setPlayerProfile(profile);
        head.setItemMeta(headMeta);

        return head;
    }

    public static ItemStack getPlayerSkull(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            Logger.info("Player '" + playerName + "' is not online or not found.");
            return null;
        }

        PlayerProfile profile = player.getPlayerProfile();

        if (profile == null) {
            Logger.info("PlayerProfile for '" + playerName + "' not found.");
            return null;
        }

        return getCustomSkull(profile);
    }

    public static ItemStack createSkullItem() {
        try {
            return new ItemStack(ItemUtil.getHeadMaterial(), 1, (short) 3);
        } catch (Throwable t) {
            return new ItemStack(ItemUtil.getHeadMaterial());
        }
    }
}
