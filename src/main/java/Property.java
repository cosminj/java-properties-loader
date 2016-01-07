package com.crossover.trial.properties;

import java.util.Objects;
import java.util.Optional;

public class Property {

    private final Key key;
    private final Optional<?> value;

    public Property(Key key, Optional<?> value) {
        this.key = key;
        this.value = value;
    }

    public Key key() {
        return key;
    }

    public Optional<?> value() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property property = (Property) o;
        return Objects.equals(key, property.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
