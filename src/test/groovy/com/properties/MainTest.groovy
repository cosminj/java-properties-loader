package com.properties

import spock.lang.Specification

class MainTest extends Specification {


    def "the arguments provided to main() should be EXACTLY : 1st argument = output file and then the list of URIs to load "() {
        given:
        Main main = new Main()

        when:

        main.main(['output.txt', 'classpath:someTest.properties', 'classpath:someOtherTest.json'].toArray(new String[3]))

        then:
        new File('output.txt').text == 'jdbc_driver, java.lang.Class, class com.mysql.jdbc.driver\n' +
                'job.timeout, java.lang.Integer, 3600\n'
    }
}