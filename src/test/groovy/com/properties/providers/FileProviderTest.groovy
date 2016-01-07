package com.properties.providers

import org.codehaus.groovy.runtime.IOGroovyMethods
import spock.lang.Specification

class FileProviderTest extends Specification {

    def "should open an input stream to an existing file"() {
        given:
        FileProvider fileProvider = new FileProvider()
        File pomXml = new File('pom.xml')

        when:
        InputStream is = fileProvider.openResource(pomXml.toURI());

        then:
        is
        IOGroovyMethods.getText(is).contains('<groupId>com.properties</groupId>')
    }

}