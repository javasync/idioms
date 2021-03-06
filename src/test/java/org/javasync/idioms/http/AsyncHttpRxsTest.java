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

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class AsyncHttpRxsTest {

    static final String [] URLs = {
        "http://example.com/",
        "https://martinfowler.com/books/eaa.html",
        "https://www.manning.com/books/functional-and-reactive-domain-modeling",
        "https://www.reactive-streams.org/"};

    @Test public void testRxWithUrlsInFiles() throws IOException, URISyntaxException {
        long expectedLines = Stream
            .of(URLs)
            .mapToLong(url -> httpGetSync(url).count())
            .sum();
        CompletableFuture<Long> cf = new CompletableFuture<>();
        AsyncHttpRx
            .countLinesFromUrlsInFiles("urls")
            .whenComplete((val, err) -> {
                if(err != null) cf.completeExceptionally(err);
                else cf.complete(val);
            });
        assertEquals(expectedLines, cf.join().longValue());
    }


    static Stream<String> httpGetSync(String url) {
        try {
            InputStream in = new URL(url).openStream();
            var reader = new BufferedReader(new InputStreamReader(in));
            return reader
                .lines()
                .onClose(() -> close(reader));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void close(Reader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
