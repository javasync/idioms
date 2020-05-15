# idioms

[![Build Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.javasync%3Aidioms&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.javasync%3Aidioms)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.javasync%3Aidioms&metric=coverage)](https://sonarcloud.io/component_measures?id=com.github.javasync%3Aidioms&metric=Coverage)


Different ways of dealing with _asynchronicity_, which is informally,
to allow multiple things to happen "_at the same time_", i.e. _concurrently_.

You may find
<a href="https://github.com/javasync/idioms/tree/master/src/main/java/org/javasync/idioms" target="_blank">here</a>
5 different approaches of using asynchronicity to count the total number
of lines of given files (e.g. `countLines(String...paths)`), namely:
1. [Threads and blocking IO](https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/files/Threads2.java) (**avoid it**)
2. [Tasks and blocking IO](https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/files/Tasks2.java) (**avoid it**)
3. Asynchronous IO
    <ol type="a">
        <li><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/files/AsyncIoCallbacks3.java">
            Callbacks
        </a></li>
        <li><code><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/files/AsyncIoCf3.java">
            CompletableFuture
        </a></code></li>
        <li><em><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/files/AsyncIoRx.java">
            Reactive Streams
        </a></em></li>
    </ol>

## Motivation

Teaching
<a href="https://www.isel.pt/en/subjects/modeling-and-design-patterns-leic" target="_blank">
Modelling and Design Patterns</a> course at ISEL we should achieve
<a href="_black" target="_blank">Reactive Streams</a> by the end of the semester.
To that end, we start from the basis: _Reactive_ + _Streams_.
For familiarity, we start by:
1. _Streams_ and first with the `Iterator`, which is a well-known pattern for all 
students at 4th semester and later we move to `java.util.stream.Stream`.
2. After that, we tackle _Reactive Streams_ starting with _asynchronous programming_ (this repo).

<a name="streams-cat">At the end, students are able to manage</a>:

||Multiplicity|Access||Call|
|----|----|----|----|----|
|`T`|1| |`item`| |
|`Optional<T>`|1|Internal <br> External|`op.ifPresent(item -> …)` <br> `item = op.get()`|Blocking|
|`Iterator<T>`|*|External|`item = iter.next()`|Blocking|
|`Spliterator<T>`|*|Internal|`iter.tryAdvance(item -> …)` <br> `iter.forEachRemaining(item -> …)`|Blocking|
||||||
|`CompletableFuture<T>`|1|Internal|`cf.thenAccept(item -> …)`|Non-blocking|
|`Publisher<T>` <br><small>(e.g. RxJava, Reactor, Kotlin Flow)</small>|*|Internal|`pub.subscribe(item -> …)`|Non-blocking|
|Async Iterator <br><small>(e.g C# and .Net)<small>|*|External|`item = await iter.next()` <br> `for await(const item of iter) …`|Non-blocking|

(*) Notice that `Spliterator` is not only related with the `Stream` parallelism. For a quick explanation about its advantages in sequential processing you may read the answer of Brian Goetz here: [Iterator versus Stream of Java 8](https://stackoverflow.com/a/31212695/1140754).

<!--
Regarding the 2nd point, we need a simple context to exercise asynchronous IO in 
different idioms.
Also, it should be quite simple to use.
For instance, I would not consider  `AsynchronousFileChannel` a valid option.
Our students are used to Kotlin and Javascript where 
-->

## `CompletableFuture` in a nutshell 

|`Stream<T>`|`CompletableFuture<T>`|
|----|----|
| `void forEach(Consumer<T>)` | `CF<Void> thenAccept(Consumer<T>)`|
| `Stream<R> map(Function<T, R>)` | `CF<R> thenApply(Function<T, R>)`|
| `Stream<R> flatMap(Function<T, Stream<R>>)`   | `CF<R> thenCompose(Function<T, CF<R>>)`|
| `Stream<T> peek(Consumer<T>)` | `CF<T>	whenComplete(BiConsumer<T,Throwable>)`|
| `Stream<R> zip(Stream<U>, BiFunction<T, U, R>)` (*) | `CF<R> thenCombine(CF<U>, BiFunction<T, U, R>)`|

(*) does not exist in JDK.

