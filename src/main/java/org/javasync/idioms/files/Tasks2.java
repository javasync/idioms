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

package org.javasync.idioms.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Based on example of section 15.2.1 Synchronous and asynchronous APIs
 * of Modern Java in Action.
 * Blocking IO through java.nio.file.Files API.
 *
 * Part of Approach 2 (avoid it) of https://github.com/javasync/idioms
 */
public class Tasks2 implements AutoCloseable {
    private final ExecutorService executorService;

    public Tasks2(int nrOfThreads) {
        executorService = Executors.newFixedThreadPool(nrOfThreads);
    }

    public long countLines(String...paths) throws ExecutionException, InterruptedException {
        return Stream
            .of(paths)
            .map(path -> executorService.submit(() -> nrOfLines(path)))
            .collect(toList()) // Force processing pipeline and tasks submission
            .stream()
            .map(Tasks2::join)
            .reduce((y, z) -> y + z)
            .get();
    }

    private static <T> T join(Future<T> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }

    private static long nrOfLines(String path) {
        try {
            Path p = Paths.get(path);
            return Files.lines(p).count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}