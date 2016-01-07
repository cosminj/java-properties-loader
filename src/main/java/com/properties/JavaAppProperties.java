package com.properties;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A pure java implementation of AppProperties. (No DI frameworks used so far)
 */
public class JavaAppProperties extends LinkedHashMap<Key, Property> implements AppProperties {

    /**
     * Convert reference enum to Keys (to benefit from the ./_ logic) and then remove all loaded properties
     *
     * @return the converted keys to string = name()
     */
    @Override
    public List<String> getMissingProperties() {
        return Arrays.stream(ReferenceProperty.values())
                .map(Key::new)
                // remove those already loaded in this.props
                .filter(k -> !containsKey(k))
                // re-map it to name() which gives us the string name of the key
                .map(Key::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getKnownProperties() {
        return Arrays.stream(ReferenceProperty.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid() {
        return getMissingProperties().isEmpty();
    }

    @Override
    public Object get(String keyName) {
        final Key key = new Key(keyName);
        Property property = get(key);
        return property != null && property.value().isPresent() ? property.value().get() : null;
    }

    void saveAll(Set<Property> props) {
        props.stream().forEach(p -> put(p.key(), p));
    }

    @Override
    public String toString() {
        return values().stream()
                .filter(p -> p.value().isPresent())
                .sorted((o1, o2) -> o1.key().name().compareTo(o2.key().name()))
                .map(p -> {
                    final StringBuilder printed = new StringBuilder(p.key().name());
                    p.value().ifPresent(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) {
                            printed
                                    .append(", ")
                                    .append(o.getClass().getName())
                                    .append(", ")
                                    .append(o.toString().toLowerCase());
                        }
                    });
                    return printed.toString();
                })
                .collect(Collectors.joining("\n"));
    }
}