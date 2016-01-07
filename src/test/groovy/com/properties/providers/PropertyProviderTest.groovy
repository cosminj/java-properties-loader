package com.properties.providers

import com.properties.ReferenceProperty
import com.properties.UnsupportedSuffixException
import spock.lang.Specification
import spock.lang.Unroll


class PropertyProviderTest extends Specification {

    @Unroll("parsing #uri should detect a fileType #fileType")
    def "should correctly compute the suffix of a given uri"() {
        given:
        PropertyProvider propertyProvider = new FileProvider()

        expect:
        propertyProvider.parseSuffix(new URI(uri)) == fileType

        where:
        uri                       | fileType
        'file:///aa.properties'   | FileType.properties
        'classpath:a.properties'  | FileType.properties
        'http://www/a.properties' | FileType.properties
        'file:///a.json'          | FileType.json
        'classpath:///a.json'     | FileType.json
        'http:///a.json'          | FileType.json
    }

    @Unroll("for #uri should throw exception")
    def "should throw an error for wrong suffix on uri"() {
        given:
        PropertyProvider propertyProvider = new FileProvider()

        when:
        propertyProvider.parseSuffix(new URI(uri))

        then:
        thrown(UnsupportedSuffixException)

        where:
        uri                        | fileType
        'file:///aa.properties1'   | FileType.properties
        'classpath:a.properties2'  | FileType.properties
        'http://www/a.properties3' | FileType.properties
        'file:///a.json4'          | FileType.json
        'classpath:///a.json5'     | FileType.json
        'http:///a.json6'          | FileType.json
    }

    def "should correctly load properties from a classpath file"() {
        given:
        PropertyProvider propertyProvider = new ClasspathProvider()

        when:
        def props = propertyProvider.load(new URI('classpath:someTest.properties'))

        then:
        props.size() == 1
        props[0].key().name() == ReferenceProperty.jdbc_driver.toString()
        props[0].value().isPresent()
        props[0].value().get() == com.mysql.jdbc.Driver
    }

    def "should load correctly a json file from classpath"() {
        given:
        PropertyProvider propertyProvider = new ClasspathProvider()

        when:
        def props = propertyProvider.load(new URI('classpath:someOtherTest.json'))

        then:
        props.size() == 1
        props[0].key().name() == 'job.timeout'
        props[0].value().isPresent()
        props[0].value().get() == 3600
    }

    def "should log an error and return empty set when wrong file content"() {
        given:
        PropertyProvider propertyProvider = new ClasspathProvider()

        when:
        def props = propertyProvider.load(new URI('classpath:malformed.json'))

        then:
        props.isEmpty()
    }
}