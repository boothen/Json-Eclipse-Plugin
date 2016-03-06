package com.boothen.jsonedit.core;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.osgi.framework.Bundle;

/**
 * TODO: describe
 */
public final class BundleUtils {

    private BundleUtils() {
        // no instances
    }

    /**
     * Uses UTF-8 character encoding
     * @param bundle the bundle that contains the text resource
     * @param path the path of the resource
     * @return the content as string (including line delimiters)
     * @throws IOException if the resource cannot be read
     */
    public static String readFile(Bundle bundle, String path) throws IOException {
        return readFile(bundle, path, StandardCharsets.UTF_8);
    }

    /**
     * @param bundle the bundle that contains the text resource
     * @param path the path of the resource
     * @param cs the character set to use
     * @return the content as string (including line delimiters)
     * @throws IOException if the resource cannot be read
     */
    public static String readFile(Bundle bundle, String path, Charset cs) throws IOException {
        URL jsonUrl = bundle.getEntry(path);
        try (Scanner scanner = new Scanner(jsonUrl.openStream(), cs.name())) {
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                throw new IOException("no content");
            }
        }
    }
}
