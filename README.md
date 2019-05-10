# idioms
Examples of asynchronous usage.

## Thinking reactive

I would love if Java has given consistent names for the same operations among these types:

|`Stream<T>`|`Optional<T>`|`CF<T>`|
|----|----|----|
| `void forEach(Consumer<T>)` | `void ifPresent(Consumer<T>)` | `CF<Void> thenAccept(Consumer<T>)`|
| `Stream<R> map(Function<T, R>)` | `Optional<R> map(Function<T, R>)` | `CF<R> thenApply(Function<T, R>)`|
| `Stream<R> flatMap(Function<T, Stream<R>>)` | `Optional<R> flatMap(Function<T, Optional<R>>)` | `CF<R> thenCompose(Function<T, CF<R>>)`|
| `Stream<T> peek(Consumer<T>)` | -- | `CF<T>	whenComplete(BiConsumer<T,Throwable>)`|
| `Stream<R> zip(Stream<U>, BiFunction<T, U, R>)` (*) | -- | `CF<R> thenCombine(CF<U>, BiFunction<T, U, R>)`|

(*) does not exist in JDK.

