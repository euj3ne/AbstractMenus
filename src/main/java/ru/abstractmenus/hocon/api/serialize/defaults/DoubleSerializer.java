package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class DoubleSerializer implements NodeSerializer<Double> {

    @Override
    public Double deserialize(Class<Double> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        Object obj = node.rawValue();
        if (obj instanceof Double) return (Double) obj;
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            throw new NodeSerializeException(node, "Cannot parse double from '" + obj + "'");
        }
    }

}
