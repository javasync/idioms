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

import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Here we are using the CompletableFuture based API of AsyncFiles.
 * Non-blocking IO with org.javasync.io.AsyncFiles API (org.javasync.RxIo library).
 *
 * Part of Approach 3.ii of https://github.com/javasync/idioms
 */
public class AsyncIoCf3 {
    public static CompletableFuture<Integer> countLines(String...paths) throws IOException {

        return Stream
            .of(paths)
            .map(AsyncFiles::readAll)
            .map(cf -> cf.thenApply(body -> body.split("\n").length))
            .reduce((prev, next) -> prev.thenCombine(next, Integer::sum))
            .get();
    }
}
