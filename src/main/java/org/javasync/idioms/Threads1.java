package org.javasync.idioms;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Based on chapter
 * 15.2 Synchronous and asynchronous APIs
 *
 * Example 1
 */
public class Threads1 {

    public static long countLines(String path1, String path2) throws InterruptedException {
        Result result = new Result();
        Thread t1 = new Thread(() -> { result.left = nrOfLines(path1); } );
        Thread t2 = new Thread(() -> { result.right = nrOfLines(path2); });
        t1.start();
        t2.start();
        t1.join(); // Wait for the result
        t2.join(); // Wait for the result
        return result.left + result.right;
    }
    private static long nrOfLines(String path) {
        try {
            Path p = Paths.get(path);
            return Files.lines(p).count(); // Blocking
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private static class Result {
        private long left;
        private long right;
    }
}