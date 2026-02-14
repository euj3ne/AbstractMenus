package ru.abstractmenus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.abstractmenus.services.MenuManager;
import ru.abstractmenus.services.ProfileStorage;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        MenuManager.instance().getAndRemoveInputAction(event.getPlayer());
        ProfileStorage storage = ProfileStorage.instance();
        if (storage != null) {
            storage.remove(event.getPlayer());
        }
    }

}
