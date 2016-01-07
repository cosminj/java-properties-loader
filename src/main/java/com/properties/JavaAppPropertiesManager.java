package com.properties;

import com.properties.providers.PropertyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * A simple main method to load and print properties.
 */
public class JavaAppPropertiesManager implements AppPropertiesManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JavaAppProperties appProperties;

    JavaAppPropertiesManager() {
        this(new JavaAppProperties());
    }

    private JavaAppPropertiesManager(JavaAppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public AppProperties loadProps(List<String> propUris) {
        PropertyProviderFactory providerFactory = new PropertyProviderFactory();
        propUris
                .stream()
                .forEach(propUri -> {
                    try {
                        URI uri = new URI(propUri);
                        Set<Property> loadedProperties = providerFactory.getProvider(uri).load(uri);
                        appProperties.saveAll(loadedProperties);
                    } catch (URISyntaxException | IOException | UnsupportedSchemeException | UnsupportedSuffixException ex) {
                        log.error("Cannot read properties from URI: {}", propUri, ex);
                    }
                });
        return appProperties;
    }

    @Override
    public void printProperties(AppProperties props, PrintStream sync) {
        sync.println(props);
    }
}
