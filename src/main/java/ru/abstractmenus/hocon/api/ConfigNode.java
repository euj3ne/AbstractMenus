package ru.abstractmenus.hocon.api;

import ru.abstractmenus.hocon.ConfigValue;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;

import java.util.List;
import java.util.Map;

public interface ConfigNode {

    /**
     * Get wrapped config value
     * @return Wrapped value
     */
    ConfigValue wrapped();

    /**
     * Get node key
     * @return Node nke
     */
    String key();

    /**
     * Get path to this node
     * @return Path to this node
     */
    String[] path();

    /**
     * Get parent of this node
     * @return Parent of node or null if this is root node
     */
    ConfigNode parent();

    /**
     * Get child node by path
     * @param path Relative path to child node
     * @return Found or created node
     */
    ConfigNode node(String... path);

    /**
     * Get child node by key
     * @param key Key of child node
     * @return Found or created child node
     */
    ConfigNode child(String key);

    /**
     * Is ths node has children. Works with objects and lists
     * @return true if node has children or false otherwise
     */
    boolean hasChildren();

    /**
     * Is this node a list
     * @return true if node is list
     */
    boolean isList();

    /**
     * Is this node a map
     * @return true if node is map
     */
    boolean isMap();

    /**
     * Is this node a primitive (boolean, number, string)
     * @return true if node is list
     */
    boolean isPrimitive();

    /**
     * Is this node a null
     * @return true if node is null
     */
    boolean isNull();

    /**
     * Get raw native Java value
     * @return raw config value
     */
    Object rawValue();

    /**
     * Get deserialized value
     * @param type Type of the value
     * @param <T> Type of the value
     * @return Deserialized value or null
     * @throws NodeSerializeException in cases when object cannot be deserialized
     */
    default <T> T getValue(Class<T> type) throws NodeSerializeException {
        return getValue(type, null);
    }

    /**
     * Get deserialized value
     * @param type Type of the value
     * @param <T> Type of the value
     * @param def default value returned if value absent
     * @return Deserialized value or null
     * @throws NodeSerializeException in cases when object cannot be deserialized
     */
    <T> T getValue(Class<T> type, T def) throws NodeSerializeException;

    /**
     * Get deserialized list of values
     * @param type Type of the value
     * @param <T> Type of the value
     * @return Deserialized list
     * @throws NodeSerializeException in cases when object cannot be deserialized
     */
    <T> List<T> getList(Class<T> type) throws NodeSerializeException;

    /**
     * Get list of children if node is list
     * @return List of children
     */
    List<ConfigNode> childrenList();

    /**
     * Get map of children if node is map
     * @return Map of children
     */
    Map<String, ConfigNode> childrenMap();

    /**
     * Get boolean value
     * @param def value that will be returned if node is null
     * @return Boolean value
     */
    boolean getBoolean(boolean def);

    /**
     * Get boolean value or false as default
     * @return Boolean value or false as default
     */
    default boolean getBoolean() {
        return getBoolean(false);
    }

    /**
     * Get int value
     * @param def value that will be returned if node is null
     * @return int value
     */
    int getInt(int def);

    /**
     * Get int value or 0 as default
     * @return int value or 0 as default
     */
    default int getInt() {
        return getInt(0);
    }

    /**
     * Get long value
     * @param def value that will be returned if node is null
     * @return long value
     */
    long getLong(long def);

    /**
     * Get long value or 0 as default
     * @return long value or 0 as default
     */
    default long getLong() {
        return getLong(0L);
    }

    /**
     * Get float value
     * @param def value that will be returned if node is null
     * @return float value
     */
    float getFloat(float def);

    /**
     * Get float value or 0 as default
     * @return float value or 0 as default
     */
    default float getFloat() {
        return getFloat(0.0F);
    }

    /**
     * Get double value
     * @param def value that will be returned if node is null
     * @return double value
     */
    double getDouble(double def);

    /**
     * Get double value or 0 as default
     * @return double value or 0 as default
     */
    default double getDouble() {
        return getDouble(0.0);
    }

    /**
     * Get String value
     * @param def value that will be returned if node is null
     * @return String value
     */
    String getString(String def);

    /**
     * Get String value or null as default
     * @return String value or null as default
     */
    default String getString() {
        return getString(null);
    }

}
