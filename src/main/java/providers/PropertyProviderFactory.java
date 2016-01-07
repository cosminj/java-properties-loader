package com.crossover.trial.properties.providers;

import com.crossover.trial.properties.UnsupportedSchemeException;

import java.net.URI;

public class PropertyProviderFactory {

    public PropertyProvider getProvider(URI uri) throws UnsupportedSchemeException {
        try {
            return ProviderType.valueOf(uri.getScheme()).getProvider();
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new UnsupportedSchemeException(ex);
        }
    }

    /**
     * It's ok to use 1 instance only of provider for all possible properties sources
     * because providers are stateless objects.
     */
    private enum ProviderType {

        classpath(new ClasspathProvider()),
        file(new FileProvider()),
        http(new HttpProvider());

        private PropertyProvider provider;

        ProviderType(PropertyProvider provider) {
            this.provider = provider;
        }

        PropertyProvider getProvider() {
            return provider;
        }
    }
}