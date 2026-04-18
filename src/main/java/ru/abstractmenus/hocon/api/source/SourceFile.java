package ru.abstractmenus.hocon.api.source;

import java.io.*;
import java.nio.file.Files;

public class SourceFile extends AbstractConfigSource {

    private final File file;

    public SourceFile(File file){
        super(file.getName());
        this.file = file;
    }

    @Override
    public BufferedReader getSourceReader() throws IOException {
        return new BufferedReader(new FileReader(file));
    }

    @Override
    public BufferedWriter getSourceWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath());
    }

    @Override
    public InputStream getStream() throws IOException {
        return Files.newInputStream(file.toPath());
    }
}
