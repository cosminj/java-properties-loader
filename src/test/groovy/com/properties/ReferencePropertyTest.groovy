package com.properties

import spock.lang.Specification


class ReferencePropertyTest extends Specification {

    def "all reference properties should be findable by mingled strings"() {
        given:
        def allKeys = ReferenceProperty.values().collect {
            it.toString().split("_").reverseEach { it.toUpperCase() }.join(".")
        }

        expect:
        allKeys.each {
            assert ReferenceProperty.isKnown(it)
        }
    }

}