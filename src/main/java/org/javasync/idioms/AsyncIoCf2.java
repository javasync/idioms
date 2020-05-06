package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletableFuture;

/**
 * Based on chapter
 * 15.4 CompletableFuture and combinators for concurrency
 */
public class AsyncIoCf2 {
    public static CompletableFuture<Integer> countLines(String path1, String path2) {
        CompletableFuture<Integer> cf1 = AsyncFiles
            .readAll(path1)
            .thenApply(body -> body.split("\n").length);
        CompletableFuture<Integer> cf2 = AsyncFiles
            .readAll(path2)
            .thenApply(body -> body.split("\n").length);
        return cf1.thenCombine(cf2, Integer::sum);
    }
}
