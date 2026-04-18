package ru.abstractmenus.hocon.api.source;

import java.io.*;
import java.net.URL;

public class SourceUrl extends AbstractConfigSource {

    private final URL url;

    public SourceUrl(String name, URL url){
        super(name);
        this.url = url;
    }

    @Override
    public BufferedReader getSourceReader() throws IOException {
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }

    @Override
    protected BufferedWriter getSourceWriter() throws IOException {
        return null;
    }

    @Override
    public InputStream getStream() throws IOException {
        return url.openStream();
    }
}
