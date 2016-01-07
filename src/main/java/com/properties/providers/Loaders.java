package com.properties.providers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.properties.Key;
import com.properties.Property;
import com.properties.ReferenceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

class Loaders {

    private static final Logger logger = LoggerFactory.getLogger(Loaders.class);

    static Set<Property> loadFromPropertyFile(InputStream is) {
        try {
            return loadFromPropertyFileUnchecked(is);
        } catch (IOException ex) {
            logger.error("Cannot parse properties file ", ex);
        }
        return Collections.emptySet();
    }

    private static Set<Property> loadFromPropertyFileUnchecked(InputStream is) throws IOException {
        Properties props = new Properties();
        props.load(is);
        return props
                .entrySet()
                .stream()
                .filter(Loaders::filterOutUnknownOrBadFormat)
                .map(Loaders::extractProperty)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Filter out properties which are not "known" (they don't exist in ReferenceProperty) and those with wrong values
     */
    private static boolean filterOutUnknownOrBadFormat(Map.Entry<Object, Object> e) {
        final Key key = getKeyFromEntry(e);
        return ReferenceProperty.isKnown(key.name()) && extractProperty(e).value().isPresent();
    }

    private static Key getKeyFromEntry(Map.Entry<Object, Object> e) {
        return new Key((String) e.getKey());
    }

    private static Property extractProperty(Map.Entry<Object, Object> e) {
        final Key key = getKeyFromEntry(e);
        final Optional<?> value = ReferenceProperty.from(key.name()).getValue((String) e.getValue());
        return new Property(key, value);
    }

    static Set<Property> loadFromJsonFile(InputStream is) {
        try {
            return loadFromJsonFileUnchecked(is);
        } catch (IOException ex) {
            logger.error("Cannot parse json file ", ex);
        }
        return Collections.emptySet();
    }

    private static Set<Property> loadFromJsonFileUnchecked(InputStream is) throws IOException {
        Set<Property> properties = new LinkedHashSet<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(is);
        rootNode.fields().forEachRemaining(node -> {
            if (ReferenceProperty.isKnown(node.getKey())) {
                Key key = new Key(node.getKey());
                Optional<?> value = ReferenceProperty.from(node.getKey()).getValue(node.getValue().asText());
                if (value.isPresent()) {
                    properties.add(new Property(key, value));
                }
            }
        });
        return properties;
    }
}
