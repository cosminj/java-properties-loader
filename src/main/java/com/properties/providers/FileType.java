package com.properties.providers;

import com.properties.Property;

import java.io.InputStream;
import java.util.Set;
import java.util.function.Function;

public enum FileType {
    properties(Loaders::loadFromPropertyFile),
    json(Loaders::loadFromJsonFile);

    private final Function<InputStream, Set<Property>> func;

    FileType(Function<InputStream, Set<Property>> func) {
        this.func = func;
    }

    Set<Property> parseFile(InputStream is) {
        return func.apply(is);
    }
}
