package ru.abstractmenus.api.handler;

import org.bukkit.entity.Player;

public interface PointsHandler {

    boolean hasPoints(Player player, int amount);

    void takePoints(Player player, int amount);

    void givePoints(Player player, int amount);

}
