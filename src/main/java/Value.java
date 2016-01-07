package com.crossover.trial.properties;

import com.amazonaws.regions.Regions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

/**
 * Provides a type safe value for a property.
 */
public class Value {

    private static Logger log = LoggerFactory.getLogger(Value.class);

    public static Optional<Boolean> asBool(String x) {
        return validateAndCompute(x, Value::parseBoolean);
    }

    public static Optional<Integer> asInt(String x) {
        return validateAndCompute(x, Integer::valueOf);
    }

    public static Optional<Double> asDouble(String x) {
        return validateAndCompute(x, Double::valueOf);
    }

    public static Optional<URI> asUri(String x) {
        return validateAndCompute(x, URI::create);
    }

    public static Optional<Regions> asAwsRegion(String x) {
        return validateAndCompute(x, Regions::fromName);
    }

    public static Optional<String> asString(String x) {
        return Optional.of(x);
    }

    public static Optional<Class> asClass(String x) {
        // validateAndCompute class (try to load it), else return empty
        try {
            return Optional.of(Class.forName(x));
        } catch (ClassNotFoundException ex) {
            log.error("Invalid Class name: {}", x, ex);
        }
        return Optional.empty();
    }

    private static <T> Optional<T> validateAndCompute(String x, Function<String, T> validator) {
        try {
            return Optional.of(validator.apply(x));
        } catch (Exception ex) {
            log.error("Invalid value: {}", x, ex);
        }
        return Optional.empty();
    }

    private static Boolean parseBoolean(String s) {
        if (s != null && s.equals("true")) {
            return Boolean.TRUE;
        } else if (s != null && s.equals("false")) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException();
    }
}
