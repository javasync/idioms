package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

/**
 * Based on chapter
 * 15.4 CompletableFuture and combinators for concurrency
 *
 * Example 1 with a single CompletableFuture and other running in the main thread.
 *
 *  !!!! For now ignoring error handling !!!!
 */
public class AsyncIoCf1 {
    public static long countLines(String path1, String path2) throws IOException {

        CompletableFuture<Integer> count1 = new CompletableFuture<>();
        AsyncFiles.readAll(path1, (err, body) -> {
            count1.complete(body.split("\n").length);
        });
        Path p2 = Paths.get(path2);
        long count2 = Files.lines(p2).count();
        return count1.join() + count2;
    }
}
