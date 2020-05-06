package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Based on chapter
 * 15.4 CompletableFuture and combinators for concurrency
 */
public class AsyncIoCf3 {
    public static CompletableFuture<Integer> countLines(String...paths) throws IOException {

        return Stream
            .of(paths)
            .map(path -> AsyncFiles
                .readAll(path)
                .thenApply(body -> body.split("\n").length))
            .reduce((prev, next) -> prev.thenCombine(next, Integer::sum))
            .get();
    }
}
