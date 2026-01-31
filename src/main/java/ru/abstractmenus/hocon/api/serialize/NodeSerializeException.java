package ru.abstractmenus.hocon.api.serialize;

import ru.abstractmenus.hocon.api.ConfigNode;

public class NodeSerializeException extends Exception {

    private ConfigNode causeNode;

    public NodeSerializeException(String message) {
        super(message);
    }

    public NodeSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeSerializeException(Throwable cause) {
        super(cause);
    }

    public NodeSerializeException(ConfigNode node, String message) {
        super(message);
        this.causeNode = node;
    }

    public ConfigNode getCauseNode() {
        return causeNode;
    }
}
