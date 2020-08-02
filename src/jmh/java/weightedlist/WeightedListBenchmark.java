package weightedlist;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;

public class WeightedListBenchmark
{
    public static final Random RANDOM = new Random();

    public static final IWeightedList<String> PARALLEL_LIST_1000 = new ParallelListWeightedList<>();
    public static final IWeightedList<String> TREE_MAP_LIST_1000 = new TreeMapWeightedList<>();

    public static final IWeightedList<String> PARALLEL_LIST_100 = new ParallelListWeightedList<>();
    public static final IWeightedList<String> TREE_MAP_LIST_100 = new TreeMapWeightedList<>();

    public static final IWeightedList<String> PARALLEL_LIST_10 = new ParallelListWeightedList<>();
    public static final IWeightedList<String> TREE_MAP_LIST_10 = new TreeMapWeightedList<>();

    public static final IWeightedList<String> DEDICATED_LIST_1 = IWeightedList.singleton("test");
    public static final IWeightedList<String> PARALLEL_LIST_1 = new ParallelListWeightedList<>();
    public static final IWeightedList<String> TREE_MAP_LIST_1 = new TreeMapWeightedList<>();

    static
    {
        for (int i = 0; i < 1000; i++)
        {
            String id = String.valueOf(RANDOM.nextInt());
            double weight = RANDOM.nextDouble();

            PARALLEL_LIST_1000.add(weight, id);
            TREE_MAP_LIST_1000.add(weight, id);
        }

        for (int i = 0; i < 100; i++)
        {
            String id = String.valueOf(RANDOM.nextInt());
            double weight = RANDOM.nextDouble();

            PARALLEL_LIST_100.add(weight, id);
            TREE_MAP_LIST_100.add(weight, id);
        }

        for (int i = 0; i < 10; i++)
        {
            String id = String.valueOf(RANDOM.nextInt());
            double weight = RANDOM.nextDouble();

            PARALLEL_LIST_10.add(weight, id);
            TREE_MAP_LIST_10.add(weight, id);
        }

        PARALLEL_LIST_1.add(1.0, "test");
        TREE_MAP_LIST_1.add(1.0, "test");
    }

    @Benchmark
    public String benchmarkTreeMapWeightedListGet1000()
    {
        return TREE_MAP_LIST_1000.get(RANDOM);
    }

    @Benchmark
    public String benchmarkParallelListWeightedListGet1000()
    {
        return PARALLEL_LIST_1000.get(RANDOM);
    }

    @Benchmark
    public String benchmarkTreeMapWeightedListGet100()
    {
        return TREE_MAP_LIST_100.get(RANDOM);
    }

    @Benchmark
    public String benchmarkParallelListWeightedListGet100()
    {
        return PARALLEL_LIST_100.get(RANDOM);
    }

    @Benchmark
    public String benchmarkTreeMapWeightedListGet10()
    {
        return TREE_MAP_LIST_10.get(RANDOM);
    }

    @Benchmark
    public String benchmarkParallelListWeightedListGet10()
    {
        return PARALLEL_LIST_10.get(RANDOM);
    }

    @Benchmark
    public String benchmarkTreeMapWeightedListGet1()
    {
        return TREE_MAP_LIST_1.get(RANDOM);
    }

    @Benchmark
    public String benchmarkParallelListWeightedListGet1()
    {
        return PARALLEL_LIST_1.get(RANDOM);
    }

    @Benchmark
    public String benchmarkDedicatedListWeightedListGet1()
    {
        return DEDICATED_LIST_1.get(RANDOM);
    }
}
