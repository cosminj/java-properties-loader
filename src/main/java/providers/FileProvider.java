package com.crossover.trial.properties.providers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class FileProvider extends PropertyProvider {

    @Override
    protected InputStream openResource(URI uri) throws IOException {
        return uri.toURL().openStream();
    }

}