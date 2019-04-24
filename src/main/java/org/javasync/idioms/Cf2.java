package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletableFuture;

/**
 * Based on chapter
 * 15.4 CompletableFuture and combinators for concurrency
 *
 * Example 2 with two CompletableFutures and combining them.
 *
 *  !!!! For now ignoring error handling !!!!
 */
public class Cf2 {
    public static int countLines(String path1, String path2) {

        CompletableFuture<Integer> count1 = new CompletableFuture<>();
        CompletableFuture<Integer> count2 = new CompletableFuture<>();
        CompletableFuture<Integer> res = count1.thenCombine(count2, Integer::sum);
        AsyncFiles.readAll(path1, (err, body) -> {
            count1.complete(body.split("\n").length);
        });
        AsyncFiles.readAll(path2, (err, body) -> {
            count2.complete(body.split("\n").length);
        });
        return count1.join() + count2.join();
    }
}
