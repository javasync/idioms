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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ObjIntConsumer;

/**
 * Non-blocking IO with org.javasync.io.AsyncFiles API (org.javasync.RxIo library).
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Here we are using the callbacks based API of AsyncFiles.
 *
 * Part of Approach 3.i of https://github.com/javasync/idioms
 */
public class AsyncIoCallbacks3 {

    private AsyncIoCallbacks3() {
    }

    public static void countLines(ObjIntConsumer<Throwable> callback, String...paths) {
        final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger count = new AtomicInteger(0);

        for(String path : paths) {
            AsyncFiles.readAll(path, (err, body) -> {
                if(err != null) { callback.accept(err, 0); return; }
                int n = body.split("\n").length;
                total.addAndGet(n);
                if(count.incrementAndGet() >= paths.length)
                    callback.accept(null, total.get());
            });
        }
    }
}
