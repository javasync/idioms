package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 *
 * !!!! For now ignoring error handling !!!!
 */

public class Callbacks1 {
    public static int countLines(String path1, String path2) {
        final Result res = new Result();

        AsyncFiles.readAll(path1, (err, body) -> {
            res.left = body.split("\n").length;
        });

        AsyncFiles.readAll(path2, (err, body) -> {
            res.right = body.split("\n").length;
        });

        while(res.left < 0 || res.right< 0) {
            Thread.yield();
        }

        return res.left + res.right;
    }

    private static class Result {
        private int left = -1;
        private int right = -1;
    }
}
