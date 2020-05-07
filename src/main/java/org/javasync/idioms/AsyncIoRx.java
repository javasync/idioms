package org.javasync.idioms;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static io.reactivex.rxjava3.core.Observable.fromPublisher;

public class AsyncIoRx {
    public static CompletionStage<Integer> countLines(String...paths) throws IOException {
        return Observable             // RxJava implementation for Publisher without back-pressure
            .fromArray(paths)
            .flatMap(path -> fromPublisher(AsyncFiles.lines(path)))
            .map(body -> body.split("\n").length)
            .reduce(0, Integer::sum)
            .toCompletionStage();

    }
}
