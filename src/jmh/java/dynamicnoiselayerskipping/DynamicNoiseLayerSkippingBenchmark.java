package dynamicnoiselayerskipping;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;

public class DynamicNoiseLayerSkippingBenchmark
{
    public static final int SEED = (int) System.currentTimeMillis();
    public static final Noise2D BASE = SimplexNoise2D.defaultSimplex(SEED);
    public static final Noise2D DYNAMIC = SimplexNoise2D.dynamicSimplex(SEED);

    public static final Noise2D BASE_OCTAVES = BASE.octaves(4, 0.5f);
    public static final Noise2D DYNAMIC_OCTAVES = DYNAMIC.octaves(4, 0.5f);

    public static final Noise2D BASE_MANY_OCTAVES = BASE.octaves(8, 0.5f);
    public static final Noise2D DYNAMIC_MANY_OCTAVES = DYNAMIC.octaves(8, 0.5f);

    public static final Random RANDOM = new Random();

    @Benchmark
    public boolean baseAboveZeroOctaves()
    {
        return BASE_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, 0);
    }

    @Benchmark
    public boolean dynamicAboveZeroOctaves()
    {
        return DYNAMIC_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, 0);
    }

    @Benchmark
    public boolean baseAboveZeroManyOctaves()
    {
        return BASE_MANY_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, 0);
    }

    @Benchmark
    public boolean dynamicAboveZeroManyOctaves()
    {
        return DYNAMIC_MANY_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, 0);
    }

    @Benchmark
    public boolean baseAboveRandomOctaves()
    {
        return BASE_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, RANDOM.nextFloat());
    }

    @Benchmark
    public boolean dynamicAboveRandomOctaves()
    {
        return DYNAMIC_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, RANDOM.nextFloat());
    }

    @Benchmark
    public boolean baseAboveRandomManyOctaves()
    {
        return BASE_MANY_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, RANDOM.nextFloat());
    }

    @Benchmark
    public boolean dynamicAboveRandomManyOctaves()
    {
        return DYNAMIC_MANY_OCTAVES.above(RANDOM.nextFloat() * 1000, RANDOM.nextFloat() * 1000, RANDOM.nextFloat());
    }
}
