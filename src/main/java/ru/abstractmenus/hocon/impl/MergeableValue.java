package ru.abstractmenus.hocon.impl;

import ru.abstractmenus.hocon.ConfigMergeable;
import ru.abstractmenus.hocon.ConfigValue;

interface MergeableValue extends ConfigMergeable {
    // converts a Config to its root object and a ConfigValue to itself
    ConfigValue toFallbackValue();
}
