package cubicsampler;

import java.util.Random;

import org.openjdk.jmh.annotations.*;

public class CubicSamplerBenchmark
{
    public static final Random RANDOM = new Random();

    private static int nextInt()
    {
        return RANDOM.nextInt(1000);
    }

    @State(Scope.Thread)
    public static class Context
    {
        int constValue;
        Vec3 constVector;
        int[] manyValues;
        Vec3[] manyVectors;

        @Setup(Level.Invocation)
        public void setup()
        {
            constValue = nextInt();
            constVector = new Vec3(nextInt(), nextInt(), nextInt());
            manyValues = new int[16];
            manyVectors = new Vec3[16];
            for (int i = 0; i < 16; i++)
            {
                manyValues[i] = nextInt();
                manyVectors[i] = new Vec3(nextInt(), nextInt(), nextInt());
            }
        }
    }

    @Benchmark
    public Vec3 defaultHomogeneous(Context context)
    {
        return CubicSampler.defaultSample(context.constVector, (x, y, z) -> context.constVector);
    }

    @Benchmark
    public Vec3 defaultNonHomogeneous(Context context)
    {
        return CubicSampler.defaultSample(context.constVector, (x, y, z) -> context.manyVectors[(x ^ y ^ z) & 15]);
    }

    @Benchmark
    public Vec3 fastHomogeneous(Context context)
    {
        return CubicSampler.precomputingSample(context.constVector, (x, y, z) -> context.constValue);
    }

    @Benchmark
    public Vec3 fastNonHomogeneous(Context context)
    {
        return CubicSampler.precomputingSample(context.constVector, (x, y, z) -> context.manyValues[(x ^ y ^ z) & 15]);
    }
}
