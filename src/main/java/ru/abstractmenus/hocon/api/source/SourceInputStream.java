package ru.abstractmenus.hocon.api.source;

import java.io.*;

public class SourceInputStream extends AbstractConfigSource {

    private final InputStream stream;

    public SourceInputStream(String name, InputStream stream){
        super(name);
        this.stream = stream;
    }

    @Override
    public BufferedReader getSourceReader() throws IOException {
        return new BufferedReader(new InputStreamReader(stream));
    }

    @Override
    protected BufferedWriter getSourceWriter() throws IOException {
        return null;
    }

    @Override
    public InputStream getStream() throws IOException {
        return stream;
    }
}
