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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Based on introductory example of section 15.2 Synchronous and asynchronous APIs
 * of Modern Java in Action.
 * Blocking IO through java.nio.file.Files API.
 *
 * Part of Approach 1 (avoid it) of https://github.com/javasync/idioms
 */
public class Threads2 {

    public static long countLines(String...paths) {
        AtomicLong total = new AtomicLong(0);
        Stream
            .of(paths)
            .map(path -> new Thread(() -> total.addAndGet(nrOfLines(path))))
            .peek(Thread::start)
            .collect(toList())
            .forEach(Threads2::join);
        return total.get();
    }

    private static void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
    private static void join(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}