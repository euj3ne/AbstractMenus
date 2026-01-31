package ru.abstractmenus.hocon.api;

import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;

public final class Preconditions {

    private Preconditions() { }

    public static void checkNodeNull(ConfigNode node) throws NodeSerializeException {
        if (node == null || node.isNull())
            throw new NodeSerializeException("Node is null");
    }

}
