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

import java.util.concurrent.CompletableFuture;

/**
 * Based on example of section 15.4 CompletableFuture and combinators for concurrency
 * of Modern Java in Action.
 * Non-blocking IO with org.javasync.io.AsyncFiles API (org.javasync.RxIo library).
 *
 * Part of Approach 3.ii of https://github.com/javasync/idioms
 */
public class AsyncIoCf2 {
    public static CompletableFuture<Integer> countLines(String path1, String path2) {
        CompletableFuture<Integer> cf1 = AsyncFiles
            .readAll(path1)
            .thenApply(body -> body.split("\n").length);
        CompletableFuture<Integer> cf2 = AsyncFiles
            .readAll(path2)
            .thenApply(body -> body.split("\n").length);
        return cf1.thenCombine(cf2, Integer::sum);
    }
}
