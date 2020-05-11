package org.javasync.idioms;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Based on chapter
 * 15.4 CompletableFuture and combinators for concurrency
 */
public class AsyncHttpCf {
    /**
     * folder --->* file ---->* urls
     * Each file has a Url per line.
     */
    public static CompletableFuture<Integer> countLinesFromUrlsInFiles(String folder) throws IOException {
        return Files
            .walk(Paths.get(folder))  // Stream<String>
            .map(AsyncFiles::readAll) // Stream<CF<String>>
            .map(cf -> cf.thenApply(body -> 0 /* split per lines ---> countLinesFromUrls */))
            .reduce((p, c) -> p.thenCombine(c, Integer::sum))
            .get();
    }
    public static CompletableFuture<Integer> countLinesFromUrls(String...urls) {
        return Stream
            .of(urls)
            .map(AsyncHttpCf::fetchAndCountLines)
            .reduce((prev, next) -> prev.thenCombine(next, Integer::sum))
            .get();
    }
    public static CompletableFuture<Integer> fetchAndCountLines(String url) {
        AsyncHttpClient ahc = Dsl.asyncHttpClient();
        return ahc
            .prepareGet(url)
            .execute()
            .toCompletableFuture()
            .thenApply(Response::getResponseBody)
            .whenComplete((body, excep) -> close(ahc) )   // CF<String> same as previous
            .thenApply(body -> body.split("\n").length ); // CF<Integer>
    }

    private static void close(AsyncHttpClient ahc) {
        try {
            ahc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
