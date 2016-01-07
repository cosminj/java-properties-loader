package com.properties.providers

import org.codehaus.groovy.runtime.IOGroovyMethods
import spock.lang.Specification

class ClasspathProviderTest extends Specification {

    def "should open an input stream to an existing file on the classpath"() {
        given:
        ClasspathProvider cpProvider = new ClasspathProvider()

        when:
        InputStream is = cpProvider.openResource(new URI('classpath:someTest.properties'));

        then:
        is
        IOGroovyMethods.getText(is) == 'jdbc_driver=com.mysql.jdbc.Driver'
    }

}