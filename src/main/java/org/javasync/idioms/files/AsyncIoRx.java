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

import io.reactivex.rxjava3.core.Observable;
import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletionStage;

import static io.reactivex.rxjava3.core.Observable.fromPublisher;

/**
 * Provide consistent asynchronous API according to the underlying API that we are using.
 * Here we are using the reactive API of AsyncFiles.
 * Non-blocking IO with org.javasync.io.AsyncFiles API (org.javasync.RxIo library).
 *
 * Part of Approach 3.iii of https://github.com/javasync/idioms
 */
public class AsyncIoRx {

    private AsyncIoRx() {
    }

    public static CompletionStage<Integer> countLines(String...paths) {
        return Observable     // RxJava implementation for Publisher without back-pressure
            .fromArray(paths)
            .flatMap(path -> fromPublisher(AsyncFiles.lines(path)))
            .map(body -> body.split("\n").length)
            .reduce(0, Integer::sum)
            .toCompletionStage();
    }
}
