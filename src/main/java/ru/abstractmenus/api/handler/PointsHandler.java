package ru.abstractmenus.api.handler;

import org.bukkit.entity.Player;

/**
 * Points handler needs for points actions and rules
 */
public interface PointsHandler {

    /**
     * Check is player has points
     * @param player Player to check
     * @param points Required points
     * @return true if player has required amount of points
     */
    boolean hasPoints(Player player, int points);

    /**
     * Withdraw some points from player's balance
     * @param player Required player
     * @param points Required points
     */
    void takePoints(Player player, int points);

    /**
     * Give some points to player's balance
     * @param player Required player
     * @param points Required points
     */
    void givePoints(Player player, int points);
}
