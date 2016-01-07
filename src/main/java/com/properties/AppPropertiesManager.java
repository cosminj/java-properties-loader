package com.properties;

import java.io.PrintStream;
import java.util.List;


/**
 * This provides programmatic interface to loading properties
 */
public interface AppPropertiesManager {

    /**
     * Given a list of URIs and set of required keys, construct an AppProperties object.
     *
     * @param propUris an ordered list of properties files to load, keys in later URIs override old keys
     * @return a fully constructed AppProperties object
     */
    AppProperties loadProps(List<String> propUris);

    /**
     * Prints out all properties to the given PrintStream in sorted, case insensitive, order by key name
     *
     * @param props properties to print
     * @param sync  a stream to write the properties to
     */
    void printProperties(AppProperties props, PrintStream sync);
}
