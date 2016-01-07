package com.crossover.trial.properties.providers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class ClasspathProvider extends PropertyProvider {

    @Override
    protected InputStream openResource(URI uri) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(uri.getRawSchemeSpecificPart());
    }

}
