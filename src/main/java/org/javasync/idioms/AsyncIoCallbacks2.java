package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 *
 * !!!! For now ignoring error handling !!!!
 */

public class AsyncIoCallbacks2 {
    public static int countLines(String...paths) throws InterruptedException, URISyntaxException {
        final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger count = new AtomicInteger(0);

        for(String path : paths) {
            AsyncFiles.readAll(path, (err, body) -> {
                int n = body.split("\n").length;
                total.addAndGet(n);
                count.incrementAndGet();
            });
        }
        while (count.get() < paths.length)
            Thread.yield();

        return total.get();
    }
}
