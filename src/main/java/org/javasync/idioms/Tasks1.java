package org.javasync.idioms;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Based on chapter
 * 15.2 Synchronous and asynchronous APIs
 *
 * Example 2
 */
public class Tasks1 {
    public static long countLines(String path1, String path2) throws ExecutionException, InterruptedException {
        ExecutorService executorService = null;
        try{
            executorService = Executors.newFixedThreadPool(2);
            Future<Long> y = executorService.submit(() -> nrOfLines(path1)); // Callable <=> Supplier
            Future<Long> z = executorService.submit(() -> nrOfLines(path2));
            return y.get() + z.get();
        } finally {
            if(executorService != null)
                executorService.shutdown();
        }
    }
    private static long nrOfLines(String path) {
        try {
            Path p = Paths.get(path);
            return Files.lines(p).count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}