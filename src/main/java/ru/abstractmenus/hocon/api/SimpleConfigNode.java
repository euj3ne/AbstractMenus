package ru.abstractmenus.hocon.api;

import ru.abstractmenus.hocon.ConfigList;
import ru.abstractmenus.hocon.ConfigObject;
import ru.abstractmenus.hocon.ConfigValue;
import ru.abstractmenus.hocon.ConfigValueType;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;
import ru.abstractmenus.hocon.api.serialize.NodeSerializers;

import java.util.*;

class SimpleConfigNode implements ConfigNode {

    private final NodeSerializers serializers;
    private final String key;
    private final ConfigNode parent;
    private final ConfigValue wrapped;

    public SimpleConfigNode(String key, ConfigNode parent, ConfigValue wrapped, NodeSerializers serializers) {
        this.key = key;
        this.parent = parent;
        this.wrapped = wrapped;
        this.serializers = serializers;
    }

    @Override
    public ConfigValue wrapped() {
        return wrapped;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String[] path() {
        LinkedList<String> path = new LinkedList<>();
        ConfigNode node = this;

        while (node != null) {
            path.addFirst(node.key());
            node = node.parent();
        }

        return path.toArray(new String[0]);
    }

    @Override
    public ConfigNode parent() {
        return parent;
    }

    @Override
    public ConfigNode node(String... path) {
        ConfigNode node = this;
        for (String key : path)
            node = node.child(key);
        return node;
    }

    @Override
    public ConfigNode child(String key) {
        if (key == null) throw new IllegalArgumentException("Node key cannot be null");

        if (wrapped instanceof ConfigObject) {
            ConfigObject obj = (ConfigObject) wrapped;
            ConfigValue val = obj.get(key);

            if (val != null)
                return new SimpleConfigNode(key, this, val, serializers);
        }

        return new SimpleConfigNode(key, this, null, serializers);
    }

    @Override
    public boolean hasChildren() {
        if (wrapped instanceof ConfigObject) {
            return !((ConfigObject) wrapped).isEmpty();
        }
        if (wrapped instanceof ConfigList) {
            return !((ConfigList) wrapped).isEmpty();
        }
        return false;
    }

    @Override
    public boolean isList() {
        return !isNull() && wrapped.valueType() == ConfigValueType.LIST;
    }

    @Override
    public boolean isMap() {
        return !isNull() && wrapped.valueType() == ConfigValueType.OBJECT;
    }

    @Override
    public boolean isPrimitive() {
        return !isNull() && (wrapped.valueType() == ConfigValueType.BOOLEAN
                || wrapped.valueType() == ConfigValueType.NUMBER
                || wrapped.valueType() == ConfigValueType.STRING);
    }

    @Override
    public boolean isNull() {
        return wrapped == null || wrapped.valueType() == ConfigValueType.NULL;
    }

    @Override
    public Object rawValue() {
        return !isNull() ? wrapped.unwrapped() : null;
    }

    @Override
    public <T> T getValue(Class<T> type, T def) throws NodeSerializeException {
        if (isNull()) return def;
        NodeSerializer<T> serializer = serializers.getSerializer(type);
        return serializer.deserialize(type, this);
    }

    @Override
    public <T> List<T> getList(Class<T> type) throws NodeSerializeException {
        if (!isNull()) {
            NodeSerializer<T> serializer = serializers.getSerializer(type);
            List<T> values = new ArrayList<>();

            if (isList()) {
                for (ConfigNode node : childrenList()) {
                    values.add(serializer.deserialize(type, node));
                }
                return values;
            }

            T value = serializer.deserialize(type, this);

            if (value != null)
                values.add(value);

            return values;
        }

        return Collections.emptyList();
    }

    @Override
    public List<ConfigNode> childrenList() {
        if (isList()) {
            ConfigList list = (ConfigList) wrapped;
            List<ConfigNode> nodes = new LinkedList<>();

            for (ConfigValue el : list) {
                nodes.add(new SimpleConfigNode(null, this, el, serializers));
            }

            return nodes;
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, ConfigNode> childrenMap() {
        if (isMap()) {
            ConfigObject obj = (ConfigObject) wrapped;
            Map<String, ConfigNode> map = new LinkedHashMap<>();

            for (Map.Entry<String, ConfigValue> entry : obj.entrySet()) {
                ConfigNode node = new SimpleConfigNode(entry.getKey(), this, entry.getValue(), serializers);
                map.put(entry.getKey(), node);
            }

            return map;
        }
        return Collections.emptyMap();
    }

    @Override
    public boolean getBoolean(boolean def) {
        try {
            return getValue(Boolean.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public int getInt(int def) {
        try {
            return getValue(Integer.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public long getLong(long def) {
        try {
            return getValue(Long.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public float getFloat(float def) {
        try {
            return getValue(Float.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public double getDouble(double def) {
        try {
            return getValue(Double.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public String getString(String def) {
        try {
            return getValue(String.class, def);
        } catch (NodeSerializeException e) {
            return def;
        }
    }

    @Override
    public String toString() {
        if (isNull()) {
            return "null";
        } else if (isMap()) {
            return childrenMap().toString();
        } else if (isList()) {
            return childrenList().toString();
        } else {
            return wrapped.unwrapped().toString();
        }
    }
}
