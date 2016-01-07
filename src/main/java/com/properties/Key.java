package com.properties;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.quote;
import static java.util.stream.Collectors.joining;

public class Key {

    public static final String DOT_SEPARATOR = ".";
    private static final String UNDERSCORE_SEPARATOR = "_";
    private static final Set<String> SEPARATORS = new HashSet<>(asList(DOT_SEPARATOR, UNDERSCORE_SEPARATOR));

    private final Set<String> keyParts;
    private String separator;

    public Key(String key) {
        this.keyParts = splitKey(key);
    }

    public Key(ReferenceProperty rp) {
        this(rp.name());
    }

    /**
     * Assumption made when computing key parts: The key should NOT contain a combination of . and _
     */
    private Set<String> splitKey(String key) {
        this.separator = SEPARATORS.stream()
                .filter(key::contains)
                .findFirst()
                .orElse("");

        return Arrays
                .stream(key.split(quote(separator)))
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String name() {
        return keyParts.stream().collect(joining(separator));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key property = (Key) o;
        return Objects.equals(keyParts, property.keyParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyParts);
    }

    @Override
    public String toString() {
        return name();
    }
}