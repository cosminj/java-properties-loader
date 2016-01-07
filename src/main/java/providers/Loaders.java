package com.crossover.trial.properties.providers;

import com.crossover.trial.properties.Key;
import com.crossover.trial.properties.Property;
import com.crossover.trial.properties.ReferenceProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Loaders {

    private static final Logger logger = LoggerFactory.getLogger(Loaders.class);

    protected static Set<Property> loadFromPropertyFile(InputStream is) {
        try {
            return loadFromPropertyFileUnchecked(is);
        } catch (IOException ex) {
            logger.error("Cannot parse properties file ", ex);
        }
        return Collections.emptySet();
    }

    protected static Set<Property> loadFromPropertyFileUnchecked(InputStream is) throws IOException {
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
        final Key key = getKeyfromEntry(e);
        return ReferenceProperty.isKnown(key.name()) && extractProperty(e).value().isPresent();
    }

    private static Key getKeyfromEntry(Map.Entry<Object, Object> e) {
        return new Key((String) e.getKey());
    }

    private static Property extractProperty(Map.Entry<Object, Object> e) {
        final Key key = getKeyfromEntry(e);
        final Optional<?> value = ReferenceProperty.from(key.name()).getValue((String) e.getValue());
        return new Property(key, value);
    }

    protected static Set<Property> loadFromJsonFile(InputStream is) {
        try {
            return loadFromJsonFileUnchecked(is);
        } catch (IOException ex) {
            logger.error("Cannot parse json file ", ex);
        }
        return Collections.emptySet();
    }

    protected static Set<Property> loadFromJsonFileUnchecked(InputStream is) throws IOException {
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
