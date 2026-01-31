package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class FloatSerializer implements NodeSerializer<Float> {

    @Override
    public Float deserialize(Class<Float> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        Object obj = node.rawValue();
        if (obj instanceof Float) return (Float) obj;
        try {
            return Float.parseFloat(obj.toString());
        } catch (NumberFormatException e) {
            throw new NodeSerializeException(node, "Cannot parse float from '" + obj + "'");
        }
    }

}
