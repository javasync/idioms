package org.javasync.idioms;

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
