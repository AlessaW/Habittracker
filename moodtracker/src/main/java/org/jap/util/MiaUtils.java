package org.jap.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/*
    Created by Peter
    Code shared by Mia
*/

/**
 *
 */
public class MiaUtils {
    private static final Logger log = LogManager.getLogger(MiaUtils.class);
    
    public static Path GetResourcesDirectory() {
        return Paths.get(
                URI.create(
                        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("."),
                                        "Resource Folder not found ???")
                                .toExternalForm()));
    }
}
