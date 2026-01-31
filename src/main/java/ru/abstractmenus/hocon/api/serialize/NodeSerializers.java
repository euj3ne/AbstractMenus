package ru.abstractmenus.hocon.api.serialize;

import ru.abstractmenus.hocon.api.serialize.defaults.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Collection of serializers
 */
public class NodeSerializers {

    private final Map<Class<?>, NodeSerializer<?>> serializers;

    public NodeSerializers(Map<Class<?>, NodeSerializer<?>> serializers) {
        this.serializers = serializers;
    }

    /**
     * Create new empty collection
     */
    public NodeSerializers() {
        this(new HashMap<>());
    }

    /**
     * Get serializer of specified type
     * @param type Value type
     * @param <T> Value type
     * @return Found serializer
     * @throws NodeSerializeException if serializer for specified type not found
     */
    public <T> NodeSerializer<T> getSerializer(Class<T> type) throws NodeSerializeException {
        NodeSerializer<T> serializer = (NodeSerializer<T>) serializers.get(type);
        if (serializer == null)
            throw new NodeSerializeException("No serializer for type " + type.getName());
        return serializer;
    }

    /**
     * Register own serializer for some type
     * @param type Type of value
     * @param serializer Serializer implementation
     * @param <T> Type of value
     * @return Current serializers collection instance
     */
    public <T> NodeSerializers register(Class<T> type, NodeSerializer<T> serializer) {
        serializers.put(type, serializer);
        return this;
    }

    /**
     * Merge tho serializers collection to new collection
     * @param serializers Another serializers collection
     * @return New serializers collection with serializers both from current and another collection
     */
    public NodeSerializers merge(NodeSerializers serializers) {
        NodeSerializers ser = new NodeSerializers();
        ser.serializers.putAll(this.serializers);
        ser.serializers.putAll(serializers.serializers);
        return ser;
    }

    /**
     * Create new default serializers list,
     * which contains serializers for all primitive types
     * @return New serializers collection
     */
    public static NodeSerializers defaults() {
        Map<Class<?>, NodeSerializer<?>> map = new HashMap<>();

        map.put(Boolean.class, new BooleanSerializer());
        map.put(Integer.class, new IntegerSerializer());
        map.put(Long.class, new LongSerializer());
        map.put(Float.class, new FloatSerializer());
        map.put(Double.class, new DoubleSerializer());
        map.put(String.class, new StringSerializer());
        map.put(UUID.class, new UuidSerializer());
        map.put(List.class, new ListSerializer());

        return new NodeSerializers(map);
    }

}
