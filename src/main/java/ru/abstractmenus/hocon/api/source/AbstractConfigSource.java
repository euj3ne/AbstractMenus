package ru.abstractmenus.hocon.api.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class AbstractConfigSource implements ConfigSource {

    private final String name;
    private Path copyTo;

    public AbstractConfigSource(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Path> getCopyDirectory() {
        return Optional.ofNullable(copyTo);
    }

    @Override
    public ConfigSource copyTo(Path path) {
        copyTo = path;

        Path file = Paths.get(path.toString(), name);

        try{
            if (!Files.exists(path)){
                Files.createDirectories(path);
            }

            if (!Files.exists(file)){
                Files.copy(getStream(), file);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return copyTo == null ? getSourceReader() : Files.newBufferedReader(Paths.get(copyTo.toString(), name));
    }

    @Override
    public BufferedWriter getWriter() throws IOException {
        return copyTo == null ? getSourceWriter() : Files.newBufferedWriter(Paths.get(copyTo.toString(), name));
    }

    protected abstract BufferedReader getSourceReader() throws IOException;

    protected abstract BufferedWriter getSourceWriter() throws IOException;
}
