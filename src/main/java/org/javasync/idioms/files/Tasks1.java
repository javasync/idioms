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

/**
 * Based on example of section 15.2.1 Synchronous and asynchronous APIs
 * of Modern Java in Action.
 * Blocking IO through java.nio.file.Files API.
 *
 * Part of Approach 2 (avoid it) of https://github.com/javasync/idioms
 */
public class Tasks1 {
    public static long countLines(String path1, String path2) throws ExecutionException, InterruptedException {
        ExecutorService executorService = null;
        try{
            executorService = Executors.newFixedThreadPool(2);
            Future<Long> y = executorService.submit(() -> nrOfLines(path1)); // Callable <=> Supplier
            Future<Long> z = executorService.submit(() -> nrOfLines(path2));
            return y.get() + z.get();
        } finally {
            if(executorService != null)
                executorService.shutdown();
        }
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