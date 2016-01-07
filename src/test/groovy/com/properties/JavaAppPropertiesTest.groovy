package com.properties

import spock.lang.Specification

class JavaAppPropertiesTest extends Specification {

    def "should return all known properties"() {
        given:
        AppProperties tap = new JavaAppProperties()

        expect:
        tap.getKnownProperties() == ReferenceProperty.values().collect { it.toString() }
    }

    def "should return all missing properties and is not valid"() {
        given:
        JavaAppProperties tap = new JavaAppProperties()

        and: "save some properties - to simulate we loaded"
        tap.saveAll(
                [
                        new Property(new Key(ReferenceProperty.auth_endpoint_uri), Optional.of(new URI('http://aaa'))),
                        new Property(new Key(ReferenceProperty.aws_access_key), Optional.of('abcdef'))
                ] as Set)

        when:
        def missing = tap.getMissingProperties()

        then:
        missing.size() == ReferenceProperty.values().length - 2
        !missing.contains(ReferenceProperty.auth_endpoint_uri.name())
        !missing.contains(ReferenceProperty.aws_access_key.name())
        !tap.isValid()

        when: "let's clear the map as well on exit"
        tap.clear()

        then:
        tap.props.isEmpty()
    }

    def "should return a correct value for existing loaded property"() {
        given:
        JavaAppProperties tap = new JavaAppProperties()

        and: "save some properties - to simulate we loaded"

        def uri = new URI('http://aaa')
        tap.saveAll(
                [
                        new Property(new Key(ReferenceProperty.auth_endpoint_uri), Optional.of(uri)),
                        new Property(new Key(ReferenceProperty.aws_access_key), Optional.of('abcdef'))
                ] as Set)

        expect:
        tap.get('auth.endpoint.uri') == uri
        tap.get('endpoint.auth.uri') == uri
        tap.get('uri.endpoint.auth') == uri
        tap.get('auth_endpoint_uri') == uri
        tap.get('endpoint_auth_uri') == uri
        tap.get('uri_endpoint_auth') == uri
        tap.get('aws_access_key') == 'abcdef'
        tap.get('aws.access.key') == 'abcdef'

        tap.toString() == 'auth_endpoint_uri, java.net.URI, http://aaa\n' +
                'aws_access_key, java.lang.String, abcdef'
    }

}