package predicatemap;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;

public class PredicateMapBenchmark
{
    public static final Map<Predicate<Key>, Value> PREDICATE_MAP = new HashMap<>();
    public static final Map<Key, List<Pair<Predicate<Key>, Value>>> INDEX_MAP = new HashMap<>();
    public static final Map<Key, Value> FAST_INDEX_MAP = new HashMap<>();

    public static final List<Key> ALL_KEYS = new ArrayList<>();
    public static final Random RANDOM = new Random();

    static
    {
        // Generate a set of 400 keys to start with
        for (int i = 0; i < 400; i++)
        {
            ALL_KEYS.add(new Key());
        }

        // 100 direct key -> value matches with simple predicates
        for (int i = 0; i < 100; i++)
        {
            Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
            Value value = new Value();

            PREDICATE_MAP.put(keyIn -> key == keyIn, value);
            INDEX_MAP.computeIfAbsent(key, keyIn -> new ArrayList<>()).add(new Pair<>(keyIn -> key == keyIn, value));
            if (!FAST_INDEX_MAP.containsKey(key))
            {
                FAST_INDEX_MAP.put(key, value);
            }
        }

        // 10 grouped keys of 10 each -> value with hash set based predicates
        for (int i = 0; i < 10; i++)
        {
            Set<Key> groupKeys = IntStream.range(0, 10).mapToObj(j -> ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()))).collect(Collectors.toSet());
            Value value = new Value();

            PREDICATE_MAP.put(groupKeys::contains, value);
            groupKeys.forEach(key -> {
                INDEX_MAP.computeIfAbsent(key, keyIn -> new ArrayList<>()).add(new Pair<>(keyIn2 -> key == keyIn2, value));
                if (!FAST_INDEX_MAP.containsKey(key))
                {
                    FAST_INDEX_MAP.put(key, value);
                }
            });
        }
    }

    @Benchmark
    public Value benchmarkPredicateMapQueryStream()
    {
        Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
        return PREDICATE_MAP.entrySet().stream().filter(entry -> entry.getKey().test(key)).map(Map.Entry::getValue).findFirst().orElse(null);
    }

    @Benchmark
    public Value benchmarkPredicateMapQueryLoop()
    {
        Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
        for (Map.Entry<Predicate<Key>, Value> entry : PREDICATE_MAP.entrySet())
        {
            if (entry.getKey().test(key))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    @Benchmark
    public Value benchmarkIndexedRecipeQueryStream()
    {
        Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
        return INDEX_MAP.getOrDefault(key, Collections.emptyList()).stream().filter(pair -> pair.key.test(key)).findFirst().map(pair -> pair.value).orElse(null);
    }

    @Benchmark
    public Value benchmarkIndexedQueryLoop()
    {
        Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
        List<Pair<Predicate<Key>, Value>> possibleValues = INDEX_MAP.get(key);
        if (possibleValues != null)
        {
            for (Pair<Predicate<Key>, Value> pair : possibleValues)
            {
                if (pair.key.test(key))
                {
                    return pair.value;
                }
            }
        }
        return null;
    }

    @Benchmark
    public Value benchmarkFastIndexedQuery()
    {
        Key key = ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
        return FAST_INDEX_MAP.get(key);
    }

    @Benchmark
    public Key benchmarkGetRandomKey()
    {
        return ALL_KEYS.get(RANDOM.nextInt(ALL_KEYS.size()));
    }

    static class Key {}
    static class Value {}

    static final class Pair<K, V>
    {
        final K key;
        final V value;

        Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }
}
