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

package org.javasync.idioms.http;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import org.javaync.io.AsyncFiles;
import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Non-blocking IO with org.asynchttpclient.AsyncHttpClient library.
 *
 * Part of Approach 3.ii of https://github.com/javasync/idioms
 */
public class AsyncHttpRx {

    private AsyncHttpRx() {
    }

    /**
     * folder --->* file ---->* urls
     * Each file has a Url per line.
     * @return
     */
    public static @NonNull CompletionStage<Long> countLinesFromUrlsInFiles(String folder) throws IOException, URISyntaxException {
        Stream<Publisher<String>> lines = Files
            .walk(pathFrom(folder))                              // Stream<String>
            .filter(file -> new File(file.toString()).isFile())  // ""
            .map(path -> AsyncFiles.lines(path.toString()));     // Stream<Publisher<String>>

        // Goal: Seq<String>
        // option 1: Stream<Stream<String>>       -> flatMap() !!!! bloqueio
        // option 2: Publisher<Publisher<String>> -> flatMap() !!! Publisher has not flatMap()

        return Observable
            .fromStream(lines)                    // Observable<Publisher<String>>
            .flatMap(Observable::fromPublisher)   // Observable<String>
            .map(AsyncHttpCf::fetchAndCountLines) // Observable<CF<Long>>
            .flatMap(Observable::fromFuture)      // Observable<Long>
            .reduce(Long::sum)                    // Maybe<Long> <=> CF<Long>
            .toCompletionStage();  // CF = CompletionStage + join()
    }

    private static Path pathFrom(String file) throws URISyntaxException {
        URL url = getSystemResource(file);
        return Paths.get(url.toURI());
    }
}
