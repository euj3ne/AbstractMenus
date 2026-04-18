package ru.abstractmenus.handlers;

import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;
import ru.abstractmenus.api.handler.PointsHandler;

public class PlayerPointsHandler implements PointsHandler {

    private final PlayerPointsAPI api;

    public PlayerPointsHandler(PlayerPointsAPI api) {
        this.api = api;
    }

    @Override
    public boolean hasPoints(Player player, int amount) {
        return api.look(player.getUniqueId()) >= amount;
    }

    @Override
    public void takePoints(Player player, int amount) {
        api.take(player.getUniqueId(), amount);
    }

    @Override
    public void givePoints(Player player, int amount) {
        api.give(player.getUniqueId(), amount);
    }
}
