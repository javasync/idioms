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
import java.util.stream.Stream;

import static java.nio.file.Paths.get;

/**
 * Based on introductory example of section 15.2 Synchronous and asynchronous APIs
 * of Modern Java in Action.
 * Blocking IO through java.nio.file.Files API.
 *
 * Part of Approach 1 (avoid it) of https://github.com/javasync/idioms
 */
public class Threads1 {

    private Threads1() {
    }

    public static long countLines(String path1, String path2) throws InterruptedException {
        Result result = new Result();
        Thread t1 = new Thread(() -> result.left = nrOfLines(path1));
        Thread t2 = new Thread(() -> result.right = nrOfLines(path2));
        t1.start();
        t2.start();
        t1.join(); // Wait for the result
        t2.join(); // Wait for the result
        return result.left + result.right;
    }

    private static long nrOfLines(String path) {
        try(Stream<String> lines = Files.lines(get(path))) {
            return lines.count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static class Result {
        private long left;
        private long right;
    }
}