# idioms

Different ways of dealing with _asynchronicity_, which is informally,
to allow multiple things to happen "_at the same time_", i.e. _concurrently_.

You may find
<a href="https://github.com/javasync/idioms/tree/master/src/main/java/org/javasync/idioms" target="_blank">here</a>
5 different approaches of using asynchronicity to count the total number
of lines of given files (e.g. `countLines(String...paths)`), namely:
1. [Threads and blocking IO](https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/Threads2.java) (**avoid it**)
2. [Tasks and blocking IO](https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/Tasks2.java) (**avoid it**)
3. Asynchronous IO
    <ol type="a">
        <li><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/AsyncIoCallbacks3.java">
            Callbacks
        </a></li>
        <li><code><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/AsyncIoCf3.java">
            CompletableFuture
        </a></code></li>
        <li><em><a href="https://github.com/javasync/idioms/blob/master/src/main/java/org/javasync/idioms/AsyncIoRx.java">
            Reactive Streams
        </a></em></li>
    </ol>

## Motivation

Teaching
<a href="https://www.isel.pt/en/subjects/modeling-and-design-patterns-leic" target="_blank">
Modelling and Design Patterns</a> course at ISEL we would like to achieve
<a href="_black" target="_blank">Reactive Streams</a> by the end of the semester.
To that end, we start from the basis: _Reactive_ + _Streams_.
For familiarity, we start by:
1. _Streams_ and first with the `Iterable`, which is a well-known pattern for all 
students at 4th semester and later we move to `java.util.stream.Stream`.
2. After that, we tackle _Reactive_ starting with _asynchronous programming_.

At the end, students should be able to manage and have a clear idea about:

||Multiplicity||Access||Call|
|----|----|----|----|----|----|
|`T`|Single|1| |`item`| |
|`Optional<T>`|Single|1|Internal <br> External|`op.ifPresent(item -> …)` <br> `item = op.get()`|Blocking|
|`Iterator<T>`|Multiple|*|External|`item = iter.next()`|Blocking|
|`Spliterator<T>`|Multiple|*|Internal|`iter.tryAdvance(item -> …)` <br> `iter.forEachRemaining(item -> …)`|Blocking|
|||||||
|`CompletableFuture<T>`|Single|1|Internal|`cf.thenAccept(item -> …)`|Non-blocking|
|`Publisher<T>` <br><small>(e.g. RxJava, Reactor, Kotlin Flow)</small>|Multiple|*|Internal|`pub.subscribe(item -> …)`|Non-blocking|
|Async Iterator <br><small>(e.g C# and .Net)<small>|Multiple|*|External|`item = await iter.next()` <br> `for await(const item of iter) …`|Non-blocking|


<!--
Regarding the 2nd point, we need a simple context to exercise asynchronous IO in 
different idioms.
Also, it should be quite simple to use.
For instance, I would not consider  `AsynchronousFileChannel` a valid option.
Our students are used to Kotlin and Javascript where 
-->

## Thinking reactive

|`Stream<T>`|`CompletableFuture<T>`|
|----|----|
| `void forEach(Consumer<T>)` | `CF<Void> thenAccept(Consumer<T>)`|
| `Stream<R> map(Function<T, R>)` | `CF<R> thenApply(Function<T, R>)`|
| `Stream<R> flatMap(Function<T, Stream<R>>)`   | `CF<R> thenCompose(Function<T, CF<R>>)`|
| `Stream<T> peek(Consumer<T>)` | `CF<T>	whenComplete(BiConsumer<T,Throwable>)`|
| `Stream<R> zip(Stream<U>, BiFunction<T, U, R>)` (*) | `CF<R> thenCombine(CF<U>, BiFunction<T, U, R>)`|

(*) does not exist in JDK.

