package ru.abstractmenus.data.rules;

import org.bukkit.entity.Player;
import ru.abstractmenus.api.Handlers;
import ru.abstractmenus.api.Rule;
import ru.abstractmenus.api.inventory.Item;
import ru.abstractmenus.api.inventory.Menu;
import ru.abstractmenus.datatype.TypeInt;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class RulePoints implements Rule {

    private final TypeInt points;

    private RulePoints(TypeInt points) {
        this.points = points;
    }

    @Override
    public boolean check(Player player, Menu menu, Item clickedItem) {
        if (Handlers.getPointsHandler() != null) {
            return Handlers.getPointsHandler().hasPoints(player, points.getInt(player, menu));
        }
        return false;
    }

    public static class Serializer implements NodeSerializer<RulePoints> {

        @Override
        public RulePoints deserialize(Class type, ConfigNode node) throws NodeSerializeException {
            return new RulePoints(node.getValue(TypeInt.class));
        }

    }
}
