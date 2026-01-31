package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

public class StringSerializer implements NodeSerializer<String> {

    @Override
    public String deserialize(Class<String> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        Object obj = node.rawValue();
        if (obj instanceof String) return (String) obj;
        return obj.toString();
    }

}
