package com.properties

import com.amazonaws.regions.Regions
import spock.lang.Specification
import spock.lang.Unroll

class ValueTest extends Specification {

    @Unroll("validates boolean string: #boolStr as optional: #optionalValue")
    def "validates a boolean value from string as strictly true or false"() {
        expect:
        Value.asBool(boolStr) == optionalValue

        where:
        boolStr | optionalValue
        'TRUE'  | Optional.empty()
        'FALSE' | Optional.empty()
        'true'  | Optional.of(Boolean.TRUE)
        'false' | Optional.of(Boolean.FALSE)
        'aa'    | Optional.empty()

    }

    @Unroll("correctly validates a double #doubleStr")
    def "correctly validates a double from string"() {
        expect:
        Value.asDouble(doubleStr) == optionalValue

        where:
        doubleStr | optionalValue
        '123'     | Optional.of(123d)
        '12.22'   | Optional.of(12.22d)
        'abc'     | Optional.empty()
    }

    @Unroll("correctly validates an URI #uriStr")
    def "correctly validates an uri"() {
        expect:
        Value.asUri(uriStr) == optionalValue

        where:
        uriStr                  | optionalValue
        'http://www.google.com' | Optional.of(new URI('http://www.google.com'))
        'a wrong Uri'           | Optional.empty()
    }

    @Unroll("correctly validates an AWS Region #awsRegionStr")
    def "correctly validates an AWS region"() {
        expect:
        Value.asAwsRegion(awsRegionStr) == optionalValue

        where:
        awsRegionStr                     | optionalValue
        Regions.AP_NORTHEAST_1.getName() | Optional.of(Regions.AP_NORTHEAST_1)
        'wrongRegion'                    | Optional.empty()
    }

    def "no validation is done as String"() {
        expect:
        Value.asString('abc') == Optional.of('abc')
    }

    @Unroll("validates correctly a Class #val if it exists in the classpath and can be loaded")
    def "validates correctly a Class if it exists in the classpath and can be loaded"() {
        expect:
        Value.asClass(val) == optional

        where:
        val                     | optional
        'com.mysql.jdbc.Driver' | Optional.of(com.mysql.jdbc.Driver)
        'com.nonexistent.Class' | Optional.empty()
    }
}