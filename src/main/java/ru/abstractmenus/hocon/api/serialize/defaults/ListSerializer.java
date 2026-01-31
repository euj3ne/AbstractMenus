package ru.abstractmenus.hocon.api.serialize.defaults;

import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.Preconditions;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

import java.util.LinkedList;
import java.util.List;

public class ListSerializer implements NodeSerializer<List> {

    @Override
    public List deserialize(Class<List> type, ConfigNode node) throws NodeSerializeException {
        Preconditions.checkNodeNull(node);
        List list = new LinkedList();
        for (ConfigNode el : node.childrenList()) {
            list.add(el.rawValue());
        }
        return list;
    }

}
