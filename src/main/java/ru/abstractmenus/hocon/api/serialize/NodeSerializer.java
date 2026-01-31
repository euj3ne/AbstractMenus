package ru.abstractmenus.hocon.api.serialize;

import ru.abstractmenus.hocon.api.ConfigNode;

/**
 * Serializer for values
 * @param <T> Type of serializable value
 */
public interface NodeSerializer<T> {

    /**
     * Deserialize value from parsed node
     * @param type Type of value
     * @param node Parsed config node
     * @return Deserialized value
     * @throws NodeSerializeException if value cannot be deserialized
     */
    T deserialize(Class<T> type, ConfigNode node) throws NodeSerializeException;

}
