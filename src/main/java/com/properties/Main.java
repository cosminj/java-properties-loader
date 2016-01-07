package com.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Provides example usage of the API and classes
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        // process command line arguments into URIs
        File outputFile = new File(args[0]);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        List<String> propertySourceUris = Arrays.asList(args).subList(1, args.length);

        // invoke the property parser and print out properties alphabetically
        AppPropertiesManager m = new JavaAppPropertiesManager();
        AppProperties props = m.loadProps(propertySourceUris);
        m.printProperties(props, new PrintStream(new FileOutputStream(outputFile)));
    }

}
