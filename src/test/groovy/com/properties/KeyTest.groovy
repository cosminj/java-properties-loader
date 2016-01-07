package com.properties

import spock.lang.Specification
import spock.lang.Unroll

class KeyTest extends Specification {

    @Unroll("should create a new property with key parts #keyParts from key #key")
    def "should create a new property with key parts #keyParts from key #key"() {
        when:
        Key prop = new Key(key)

        then:
        prop.keyParts == keyParts as Set
        prop.name() == key
        prop.toString() == key

        where:
        key   || keyParts
        'a'   || ['a']
        'a_b' || ['a', 'b']
        'a.b' || ['a', 'b']
    }

    @Unroll("properties with keys #interchangeableKeys must be all equal")
    def "properties with keys #interchangeableKeys must be all equal"() {
        given:
        def properties = interchangeableKeys.collect { new Key<>(it) } as Set

        expect: "expect that all of the given keys are actually identical as required by the contract of method AppProperties.get()"
        properties.size() == 1

        where:
        interchangeableKeys << [
                ['a.b', 'b.a', 'b_a', 'a_b'],
                ['jpa.showSQL', 'jpa_showsql', 'JPA_showSql']
        ]
    }

}