package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class IntegerSerializer implements NodeSerializer<Integer> {

    @Override
    public Integer deserialize(Class<Integer> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        Object obj = node.rawValue();
        if (obj instanceof Integer) return (Integer) obj;
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            throw new NodeSerializeException(node, "Cannot parse int from '" + obj + "'");
        }
    }

}
