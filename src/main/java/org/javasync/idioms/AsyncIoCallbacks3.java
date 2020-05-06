package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 */

public class AsyncIoCallbacks3 {
    public static void countLines(BiConsumer<Throwable, Integer> callback, String...paths) throws InterruptedException, URISyntaxException {
        final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger count = new AtomicInteger(0);

        for(String path : paths) {
            AsyncFiles.readAll(path, (err, body) -> {
                int n = body.split("\n").length;
                int res = total.addAndGet(n);
                if(count.incrementAndGet() >= paths.length)
                    callback.accept(null, res);
            });
        }
    }
}
