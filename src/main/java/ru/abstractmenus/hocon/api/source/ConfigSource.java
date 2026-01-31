package ru.abstractmenus.hocon.api.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface ConfigSource {

    /**
     * Get configuration name with extension
     * @return Configuration name
     */
    String getName();

    /**
     * Get path of the directory to copy config data
     * @return Copy directory or Optional.EMPTY if directory not defined
     */
    Optional<Path> getCopyDirectory();

    /**
     *
     * @param path The directory to copy configuration file
     * @return ConfigSource instance
     */
    ConfigSource copyTo(Path path);

    /**
     * Copy provided source to some file
     * @param path Folder in which file will be copied
     * @return ConfigSource instance
     */
    default ConfigSource copyTo(String path){
        return copyTo(Paths.get(path));
    }

    /**
     * Create and get reader of this source
     * @return BufferedReader object
     * @throws IOException if some error while reader creating
     */
    BufferedReader getReader() throws IOException;

    /**
     * Create and get writer of this source
     * @return BufferedWriter object
     * @throws IOException if some error while writer creating
     */
    BufferedWriter getWriter() throws IOException;

    /**
     * Get source as byte stream
     * @return InputStream of this source
     * @throws IOException if some error while stream creating
     */
    InputStream getStream() throws IOException;

}
