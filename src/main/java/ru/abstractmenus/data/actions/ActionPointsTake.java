package ru.abstractmenus.data.actions;

import org.bukkit.entity.Player;
import ru.abstractmenus.api.Action;
import ru.abstractmenus.api.Handlers;
import ru.abstractmenus.api.inventory.Item;
import ru.abstractmenus.api.inventory.Menu;
import ru.abstractmenus.datatype.TypeInt;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class ActionPointsTake implements Action {

    private final TypeInt amount;

    private ActionPointsTake(TypeInt amount) {
        this.amount = amount;
    }

    @Override
    public void activate(Player player, Menu menu, Item clickedItem) {
        if (Handlers.getPointsHandler() != null) {
            Handlers.getPointsHandler().takePoints(player, amount.getInt(player, menu));
        }
    }

    public static class Serializer implements NodeSerializer<ActionPointsTake> {

        @Override
        public ActionPointsTake deserialize(Class type, ConfigNode node) throws NodeSerializeException {
            return new ActionPointsTake(node.getValue(TypeInt.class));
        }

    }
}
