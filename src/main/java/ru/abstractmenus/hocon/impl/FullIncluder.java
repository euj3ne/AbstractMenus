/**
 *   Copyright (C) 2011-2012 Typesafe Inc. <http://typesafe.com>
 */
package ru.abstractmenus.hocon.impl;

import ru.abstractmenus.hocon.ConfigIncluder;
import ru.abstractmenus.hocon.ConfigIncluderClasspath;
import ru.abstractmenus.hocon.ConfigIncluderFile;
import ru.abstractmenus.hocon.ConfigIncluderURL;

interface FullIncluder extends ConfigIncluder, ConfigIncluderFile, ConfigIncluderURL,
        ConfigIncluderClasspath {

}
