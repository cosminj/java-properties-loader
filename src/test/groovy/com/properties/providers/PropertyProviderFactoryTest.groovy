package com.properties.providers

import com.properties.UnsupportedSchemeException
import spock.lang.Specification
import spock.lang.Unroll


class PropertyProviderFactoryTest extends Specification {

    @Unroll("factory should build #providerClazz when uri is #uri")
    def "should build the correct provider based on "() {
        given:
        PropertyProviderFactory factory = new PropertyProviderFactory()

        when:
        PropertyProvider provider = factory.getProvider(new URI(uri))

        then:
        provider.class == providerClazz

        where:
        uri                     | providerClazz
        'classpath:aaaa'        | ClasspathProvider
        'file:///bbb'           | FileProvider
        'http://www.google.com' | HttpProvider
    }

    @Unroll("factory throws unsupported scheme exception when scheme is #scheme")
    def "factory throws unsupported scheme exception when scheme is #scheme"() {
        when:
        new PropertyProviderFactory().getProvider(new URI(scheme))
        then:
        thrown(UnsupportedSchemeException)
        where:
        scheme << ['mailto:java-net@java.sun.com', 'news:comp.lang.java', 'urn:isbn:096139210']
    }
}