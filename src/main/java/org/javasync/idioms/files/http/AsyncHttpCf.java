/*
 * Copyright (c) 2020, Fernando Miguel Gamboa Carvalho, mcarvalho@cc.isel.ipl.pt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.javasync.idioms.files.http;

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
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Here we are using the CompletableFuture based API of AsyncHttpClient.
 * Non-blocking IO with org.asynchttpclient.AsyncHttpClient library.
 *
 * Part of Approach 3.ii of https://github.com/javasync/idioms
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
