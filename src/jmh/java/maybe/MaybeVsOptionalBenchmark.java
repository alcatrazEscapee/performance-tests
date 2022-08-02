package maybe;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class MaybeVsOptionalBenchmark
{
    @State(Scope.Thread)
    public static class Context
    {
        public @Nullable Object value, value2;
        public Optional<Object> optional, optional2;
        public Maybe<Object> maybe, maybe2;

        @Setup(Level.Invocation)
        public void setup()
        {
            value = Math.random() < 0.5 ? new Object() : null;
            value2 = Math.random() < 0.5 ? new Object() : null;
            optional = Optional.ofNullable(value);
            maybe = Maybe.wrap(value);
            optional2 = Optional.ofNullable(value2);
            maybe2 = Maybe.wrap(value2);
        }
    }

    @Benchmark
    public Optional<Object> benchmarkOptionalNew(Context context)
    {
        return Optional.ofNullable(context.value);
    }

    @Benchmark
    public Object benchmarkOptionalIsPresent(Context context)
    {
        return context.optional.isPresent();
    }

    @Benchmark
    public Object benchmarkOptionalOrElse(Context context)
    {
        return context.optional.orElse(context.value2);
    }

    @Benchmark
    public Object benchmarkOptionalOr(Context context)
    {
        return context.optional.or(() -> context.optional2);
    }

    @Benchmark
    public Optional<String> benchmarkOptionalMapToString(Context context)
    {
        return context.optional.map(Object::toString);
    }

    @Benchmark
    public Optional<Object> benchmarkOptionalFilterHashCode(Context context)
    {
        return context.optional.filter(t -> t.hashCode() % 2 == 0);
    }

    @Benchmark
    public Maybe<Object> benchmarkMaybeNew(Context context)
    {
        return Maybe.wrap(context.value);
    }

    @Benchmark
    public Object benchmarkMaybeIsPresent(Context context)
    {
        return context.maybe.isJust();
    }

    @Benchmark
    public Object benchmarkMaybeOrElse(Context context)
    {
        return context.maybe.unwrapOr(context.value2);
    }

    @Benchmark
    public Object benchmarkMaybeOr(Context context)
    {
        return context.maybe.or(() -> context.maybe2);
    }

    @Benchmark
    public Maybe<String> benchmarkMaybeMapToString(Context context)
    {
        return context.maybe.map(Object::toString);
    }

    @Benchmark
    public Maybe<Object> benchmarkMaybeFilterHashCode(Context context)
    {
        return context.maybe.filter(t -> t.hashCode() % 2 == 0);
    }
}
