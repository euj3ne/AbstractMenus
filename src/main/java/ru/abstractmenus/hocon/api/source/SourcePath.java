package ru.abstractmenus.hocon.api.source;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourcePath extends AbstractConfigSource {

    private final Path path;

    public SourcePath(Path path){
        super(path.toFile().getName());
        this.path = path;
    }

    @Override
    public BufferedReader getSourceReader() throws IOException {
        return Files.newBufferedReader(path);
    }

    @Override
    public BufferedWriter getSourceWriter() throws IOException {
        return Files.newBufferedWriter(path);
    }

    @Override
    public InputStream getStream() throws IOException {
        return Files.newInputStream(path);
    }
}
