package com.properties.providers

import org.codehaus.groovy.runtime.IOGroovyMethods
import spock.lang.Specification

class FileProviderTest extends Specification {

    def "should open an input stream to an existing file"() {
        given:
        FileProvider fileProvider = new FileProvider()
        File buildGradle = new File('build.gradle')

        when:
        InputStream is = fileProvider.openResource(buildGradle.toURI());

        then:
        is
        IOGroovyMethods.getText(is).contains("testCompile 'org.spockframework:spock-core:1.0-groovy-2.4'")
    }

}