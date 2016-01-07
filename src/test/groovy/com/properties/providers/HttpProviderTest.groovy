package com.properties.providers

import org.codehaus.groovy.runtime.IOGroovyMethods
import spock.lang.Specification


class HttpProviderTest extends Specification {

    def "should open an input stream to http response from a GET resource"() {
        given:
        HttpProvider httpProvider = new HttpProvider()

        when:
        InputStream is = httpProvider.openResource(new URI('http://www.google.com'))

        then:
        is
        IOGroovyMethods.getText(is).contains('<!doctype html><html itemscope="" itemtype="http://schema.org/WebPage"')
    }
}