package ru.abstractmenus.hocon.api.source;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ConfigSource factory
 */
public final class ConfigSources {

    private ConfigSources(){}

    /**
     * Create source by file
     * @param file Configuration file
     * @return Created config source
     */
    public static ConfigSource file(File file){
        return new SourceFile(file);
    }

    /**
     * Create source by path
     * @param path Path to configuration file
     * @return Created config source
     */
    public static ConfigSource path(Path path){
        return new SourcePath(path);
    }

    /**
     * Create source by path string
     * @param path Path to configuration file
     * @return Created config source
     */
    public static ConfigSource path(String path){
        return new SourcePath(Paths.get(path));
    }

    /**
     * Create source by file url
     * @param name Name of the config file
     * @param url Url to configuration file
     * @return Created config source
     */
    public static ConfigSource url(String name, URL url){
        return new SourceUrl(name, url);
    }

    /**
     * Create source by jar resource file
     * @param name Name of the config file. This name includes directories to required file
     * @param app Application instance. It also may be plugin instance
     * @return Created config source
     */
    public static ConfigSource resource(String name, Object app){
        return new SourceResource(name, app);
    }

    /**
     * Create source by input stream
     * @param name Name of the config file
     * @param stream InputStream instance. For example it may be file stream
     * @return Created config source
     */
    public static ConfigSource inputStream(String name, InputStream stream){
        return new SourceInputStream(name, stream);
    }

}
