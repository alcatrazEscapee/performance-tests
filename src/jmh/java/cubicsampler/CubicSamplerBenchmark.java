package cubicsampler;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;

public class CubicSamplerBenchmark
{
    public static final Random RANDOM = new Random();
    public static final int SIZE = 1000;
    public static final int[] TEST_COLORS = new int[SIZE];
    public static final Vec3[] TEST_VECS = new Vec3[SIZE];

    static
    {
        for (int i = 0; i < 1000; i++)
        {
            TEST_VECS[i] = new Vec3(RANDOM.nextDouble() * 1000,RANDOM.nextDouble() * 1000, RANDOM.nextDouble() * 1000);
            TEST_COLORS[i] = RANDOM.nextInt(1000);
        }
    }

    @Benchmark
    public Vec3 defaultSample()
    {
        Vec3 sum = Vec3.ZERO;
        for (int i = 0; i < SIZE; i++)
        {
            final int idx = i;
            // half homogeneous
            sum = sum.add(CubicSampler.defaultSample(TEST_VECS[i], (x, y, z) -> new Vec3(5, 5, 5)));
            // half non-homogeneous
            sum = sum.add(CubicSampler.defaultSample(TEST_VECS[i], (x, y, z) -> TEST_VECS[idx]));
        }
        return sum;
    }

    @Benchmark
    public Vec3 fastSample()
    {
        Vec3 sum = Vec3.ZERO;
        for (int i = 0; i < SIZE; i++)
        {
            final int idx = i;
            // half homogeneous
            sum = sum.add(CubicSampler.precomputingSample(TEST_VECS[i], (x, y, z) -> 5));
            // half non-homogeneous
            sum = sum.add(CubicSampler.precomputingSample(TEST_VECS[i], (x, y, z) -> TEST_COLORS[idx]));
        }
        return sum;
    }
}
