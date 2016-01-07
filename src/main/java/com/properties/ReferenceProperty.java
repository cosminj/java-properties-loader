package com.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ReferenceProperty {

    jdbc_driver(Value::asClass),
    jdbc_url(Value::asString),
    jdbc_username(Value::asString),
    jdbc_password(Value::asString),
    jpa_showsql(Value::asBool),
    aws_access_key(Value::asString),
    aws_secret_key(Value::asString),
    aws_account_id(Value::asInt),
    aws_region_id(Value::asAwsRegion),
    auth_endpoint_uri(Value::asUri),
    job_timeout(Value::asInt),
    sns_broadcast_topic_name(Value::asString),
    score_factor(Value::asDouble);

    private static final Map<Key, ReferenceProperty> allKnownProperties = Arrays
            .stream(values())
            .collect(Collectors.toMap(
                    Key::new,
                    Function.<ReferenceProperty>identity()
            ));

    private final Function<String, Optional<?>> func;

    ReferenceProperty(Function<String, Optional<?>> func) {
        this.func = func;
    }

    public static ReferenceProperty from(String key) {
        return allKnownProperties.get(new Key(key));
    }

    public static boolean isKnown(String key) {
        return allKnownProperties.containsKey(new Key(key));
    }

    public Optional<?> getValue(String v) {
        return func.apply(v);
    }
}