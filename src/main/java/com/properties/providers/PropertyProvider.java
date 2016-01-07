package com.properties.providers;

import com.properties.Key;
import com.properties.Property;
import com.properties.UnsupportedSuffixException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Abstracts the source and source-handling of properties.
 * Stateless object, can be used as singleton from Enum.
 */
public abstract class PropertyProvider {

    public final Set<Property> load(URI uri) throws IOException {
        FileType fileType = parseSuffix(uri);
        try (InputStream is = openResource(uri)) {
            return fileType.parseFile(is);
        }
    }

    protected abstract InputStream openResource(URI uri) throws IOException;

    protected FileType parseSuffix(URI uri) {
        final String filePart = uri.getSchemeSpecificPart();
        final String[] fileParts = filePart.split(Pattern.quote(Key.DOT_SEPARATOR));
        final String lastPart = fileParts[fileParts.length - 1];
        try {
            return FileType.valueOf(lastPart);
        } catch (IllegalArgumentException ex) {
            throw new UnsupportedSuffixException(ex);
        }
    }
}
