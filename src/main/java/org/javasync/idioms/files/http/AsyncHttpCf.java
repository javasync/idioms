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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Here we are using the CompletableFuture based API of AsyncHttpClient.
 * Non-blocking IO with org.asynchttpclient.AsyncHttpClient library.
 *
 * Part of Approach 3.ii of https://github.com/javasync/idioms
 */
public class AsyncHttpCf {

    static final CompletableFuture<Long> ZERO = completedFuture(0L);

    private AsyncHttpCf() {
    }

    /**
     * folder --->* file ---->* urls
     * Each file has a Url per line.
     * @return Sum of the number of lines of the HTTP body responses.
     */
    public static CompletableFuture<Long> countLinesFromUrlsInFiles(String folder) throws IOException, URISyntaxException {
        try(Stream<Path> files = Files.walk(pathFrom(folder))){
            return files
                .filter(file -> new File(file.toString()).isFile())        // Stream<String>
                .map(AsyncFiles::readAll)                                  // Stream<CF<String>>
                .map(cf -> cf.thenApply(body -> body.split("\n")))         // Stream<CF<String[]>>
                .map(cf -> cf.thenCompose(AsyncHttpCf::countLinesFromUrls))// Stream<CF<Long>>
                .reduce(ZERO, (p, c) -> p.thenCombine(c, Long::sum));
        }
    }

    public static CompletableFuture<Long> countLinesFromUrls(String...urls) {
        return Stream
            .of(urls)
            .map(AsyncHttpCf::fetchAndCountLines)
            .reduce(ZERO, (prev, next) -> prev.thenCombine(next, Long::sum));
    }
    public static CompletableFuture<Long> fetchAndCountLines(String url) {
        AsyncHttpClient ahc = Dsl.asyncHttpClient();
        return ahc
            .prepareGet(url)
            .execute()
            .toCompletableFuture()
            .thenApply(Response::getResponseBodyAsStream)
            .thenApply(in -> new BufferedReader(new InputStreamReader(in)))
            .thenApply(reader -> reader.lines().count())
            .whenComplete((body, excep) -> close(ahc));
    }

    private static void close(AsyncHttpClient ahc) {
        try {
            ahc.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private static Path pathFrom(String file) throws URISyntaxException {
        URL url = getSystemResource(file);
        return Paths.get(url.toURI());
    }
}
