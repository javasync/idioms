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

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RxStreams {

    @Test
    public void testBlockingStream() throws InterruptedException {
        IntStream
            .rangeClosed(1, 5)
            .forEach(System.out::println);
        System.out.println("Subscribed!");
    }
    @Test
    public void testNonBlockingStream() throws InterruptedException {
        Random rand = new Random();
        Disposable subscribe = Observable
            .intervalRange(1, 33, 0, 1000, TimeUnit.MILLISECONDS)
            .subscribe(System.out::println);
        System.out.println("Subscribed!");
        Thread.sleep(5000);
        subscribe.dispose();
        System.out.println("Disposed!");
        Thread.sleep(1000);
    }
}
