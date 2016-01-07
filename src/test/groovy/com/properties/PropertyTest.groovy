package com.properties

import spock.lang.Specification


class PropertyTest extends Specification {

    def "a property is a pojo and delegates equals to key"() {
        given:
        def key = Mock(Key)
        def value = Optional.of('a')
        def prop = new Property(key, value)
        def key2 = Mock(Key)

        def val2 = Optional.of('b')
        def prop2 = new Property(key2, val2)

        when:
        prop.equals(prop2)
        prop2.equals(prop)

        then:
        1 * key.equals(key2)

        then:
        1 * key2.equals(key)

        then:
        prop.key() == key
        prop2.key() == key2
        prop.value() == value
        prop2.value() == val2
    }
}