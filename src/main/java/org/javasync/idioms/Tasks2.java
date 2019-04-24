package org.javasync.idioms;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Based on chapter
 * 15.2 Synchronous and asynchronous APIs
 */
public class Tasks2 implements AutoCloseable {
    private final ExecutorService executorService;

    public Tasks2(int nrOfThreads) {
        executorService = Executors.newFixedThreadPool(nrOfThreads);
    }


    public long countLines(String...paths) throws ExecutionException, InterruptedException {

        List<Future<Long>> fs = Stream
            .of(paths)
            .map(path -> executorService.submit(() -> nrOfLines(path)))
            .collect(toList());

        return fs
            .stream()
            .map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            })
            .reduce((y, z) -> y + z)
            .get();
    }
    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }

    private static long nrOfLines(String path) {
        try {
            Path p = Paths.get(path);
            return Files.lines(p).count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}