package com.properties

import com.mysql.jdbc.Driver
import spock.lang.Specification


class JavaAppPropertiesManagerTest extends Specification {

    def "can load from multiple URIs (at classpath)"() {
        given:
        JavaAppProperties tap = new JavaAppProperties()
        JavaAppPropertiesManager manager = new JavaAppPropertiesManager(tap)

        when:
        manager.loadProps(['classpath:someTest.properties', 'classpath:someOtherTest.json'])

        then:
        tap.get('job.timeout') == 3600
        tap.get('jdbc_driver') == Driver.class
    }

}