package ru.abstractmenus.handlers;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;
import ru.abstractmenus.api.handler.PointsHandler;

public class PlayerPointsHandler implements PointsHandler {

    private final PlayerPointsAPI api;

    public PlayerPointsHandler(PlayerPoints plugin) {
        this.api = plugin.getAPI();
    }

    @Override
    public boolean hasPoints(Player player, int points) {
        return api.look(player.getUniqueId()) >= points;
    }

    @Override
    public void takePoints(Player player, int points) {
        api.take(player.getUniqueId(), points);
    }

    @Override
    public void givePoints(Player player, int points) {
        api.give(player.getUniqueId(), points);
    }
}
