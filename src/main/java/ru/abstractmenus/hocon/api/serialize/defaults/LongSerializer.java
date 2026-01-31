package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class LongSerializer implements NodeSerializer<Long> {

    @Override
    public Long deserialize(Class<Long> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        Object obj = node.rawValue();
        if (obj instanceof Long) return (Long) obj;
        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            throw new NodeSerializeException(node, "Cannot parse long from '" + obj + "'");
        }
    }

}
