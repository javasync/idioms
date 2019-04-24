package org.javasync.idioms;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemResource;

public class Resources {

    public static String METAMORPHOSIS = pathFrom("Metamorphosis-by-Franz-Kafka.txt");
    public static String DISCOURSE_ON_THE_METHOD = pathFrom("Discourse-on-the-Method-Descartes.txt");
    public static String DIVINE_COMEDY = pathFrom("The-Divine-Comedy-Hell-Dante.txt");

    private static String pathFrom(String file) {
        try {
            URL url = getSystemResource(file);
            return Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
