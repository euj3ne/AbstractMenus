package ru.abstractmenus.hocon.api.source;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SourceResource extends AbstractConfigSource {

    private final String fileName;
    private final Object app;

    public SourceResource(String fileName, Object app) {
        super(new File(fileName).getName());
        this.fileName = fileName;
        this.app = app;
    }

    @Override
    public BufferedReader getSourceReader() throws IOException {
        InputStream stream = app.getClass().getResourceAsStream(fileName);
        if (stream == null)
            throw new FileNotFoundException("Resource file " + fileName + " not found");
        return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    @Override
    protected BufferedWriter getSourceWriter() throws IOException {
        return null;
    }

    @Override
    public InputStream getStream() throws IOException {
        return app.getClass().getResourceAsStream(fileName);
    }
}
