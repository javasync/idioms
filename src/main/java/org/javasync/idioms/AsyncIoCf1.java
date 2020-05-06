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
 *  !!!! For now ignoring error handling !!!!
 */
public class AsyncIoCf1 {
    public static long countLines(String path1, String path2) throws IOException {
        CompletableFuture<Integer> cf1 = AsyncFiles
            .readAll(path1)
            .thenApply(body -> body.split("\n").length);
        CompletableFuture<Integer> cf2 = AsyncFiles
            .readAll(path2)
            .thenApply(body -> body.split("\n").length);
        return cf1.join() + cf2.join();
    }
}
