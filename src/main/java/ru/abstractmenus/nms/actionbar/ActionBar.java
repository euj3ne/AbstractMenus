package ru.abstractmenus.nms.actionbar;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ActionBar {

    private static final ActionBar INSTANCE = new ActionBar();

    public static ActionBar create() {
        return INSTANCE;
    }

    public void send(Player player, String message) {
        player.sendActionBar(LegacyComponentSerializer.legacySection().deserialize(message));
    }
}
