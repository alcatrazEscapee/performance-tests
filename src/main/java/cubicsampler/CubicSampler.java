package cubicsampler;

import org.jetbrains.annotations.Nullable;

public final class CubicSampler
{
    private static final int SAMPLE_RADIUS = 2;
    private static final int SAMPLE_BREADTH = 6;
    private static final double[] KERNEL = {0, 1, 4, 6, 4, 1, 0};

    public static Vec3 defaultSample(Vec3 pos, Vec3Sampler maker)
    {
        int xf = floor(pos.x());
        int yf = floor(pos.y());
        int zf = floor(pos.z());
        double xd = pos.x() - (double) xf;
        double yd = pos.y() - (double) yf;
        double zd = pos.z() - (double) zf;
        double totalScale = 0.0D;
        Vec3 sum = Vec3.ZERO;

        for (int x = 0; x < SAMPLE_BREADTH; ++x)
        {
            double xDensity = lerp(xd, KERNEL[x + 1], KERNEL[x]);
            int xBlock = xf - SAMPLE_RADIUS + x;

            for (int y = 0; y < SAMPLE_BREADTH; ++y)
            {
                double yDensity = lerp(yd, KERNEL[y + 1], KERNEL[y]);
                int yBlock = yf - SAMPLE_RADIUS + y;

                for (int z = 0; z < SAMPLE_BREADTH; ++z)
                {
                    double zDensity = lerp(zd, KERNEL[z + 1], KERNEL[z]);
                    int zBlock = zf - SAMPLE_RADIUS + z;
                    double totalDensity = xDensity * yDensity * zDensity;
                    totalScale += totalDensity;
                    sum = sum.add(maker.sample(xBlock, yBlock, zBlock).scale(totalDensity));
                }
            }
        }

        return sum.scale(1.0D / totalScale);
    }

    public static Vec3 precomputingSample(Vec3 pos, ColorSampler sampler)
    {
        int xf = floor(pos.x());
        int yf = floor(pos.y());
        int zf = floor(pos.z());
        double xd = pos.x() - (double) xf;
        double yd = pos.y() - (double) yf;
        double zd = pos.z() - (double) zf;
        double totalScale = 0;
        Vec3 sum = Vec3.ZERO;

        // Array where we fill with all the required color samples
        final int[] colorSamples = new int[SAMPLE_BREADTH * SAMPLE_BREADTH * SAMPLE_BREADTH];
        // If the array is homogeneous, then we just do the final calculation and return that value, we don't need to go through the entire loop.
        if (fillBlockArray(colorSamples, sampler, xf, yf, zf))
        {
            return Vec3.fromRGB24(colorSamples[0]);
        }

        // If the array is not homogeneous, then we can just finish the calculation, with the values we computed
        for (int x = 0; x < SAMPLE_BREADTH; ++x)
        {
            double xDensity = lerp(xd, KERNEL[x + 1], KERNEL[x]);
            for (int y = 0; y < SAMPLE_BREADTH; ++y)
            {
                double yDensity = lerp(yd, KERNEL[y + 1], KERNEL[y]);
                for (int z = 0; z < SAMPLE_BREADTH; ++z)
                {
                    double zDensity = lerp(zd, KERNEL[z + 1], KERNEL[z]);
                    double totalDensity = xDensity * yDensity * zDensity;
                    totalScale += totalDensity;

                    Vec3 color = Vec3.fromRGB24(colorSamples[arrayIndex(x, y, z)]);
                    sum = sum.add(color.scale(totalDensity));
                }
            }
        }

        return sum.scale(1.0D / totalScale);
    }

    private static boolean fillBlockArray(int[] colorSamples, ColorSampler sampler, int xf, int yf, int zf)
    {
        boolean homogeneous = true;
        int color = -100;
        for (int x = 0; x < SAMPLE_BREADTH; x++)
        {
            final int xBlock = xf - SAMPLE_RADIUS + x;
            for (int y = 0; y < SAMPLE_BREADTH; y++)
            {
                final int yBlock = yf - SAMPLE_RADIUS + y;
                for (int z = 0; z < SAMPLE_BREADTH; z++)
                {
                    final int zBlock = zf - SAMPLE_RADIUS + z;
                    final int sample = sampler.sample(xBlock, yBlock, zBlock);

                    colorSamples[arrayIndex(x, y, z)] = sample;

                    if (color == -100)
                    {
                        color = sample;
                    }
                    else if (color != sample)
                    {
                        homogeneous = false;
                    }
                }
            }
        }
        return homogeneous;
    }

    private static int arrayIndex(int x, int y, int z)
    {
        return x + (SAMPLE_BREADTH * z) + (SAMPLE_BREADTH * SAMPLE_RADIUS * y);
    }

    private static int floor(double value)
    {
        int casted = (int) value;
        return value < (double) casted ? casted - 1 : casted;
    }

    private static double lerp(double scale, double min, double max)
    {
        return min + scale * (max - min);
    }

    @FunctionalInterface
    public interface Vec3Sampler
    {
        Vec3 sample(int x, int y, int z);
    }

    @FunctionalInterface
    public interface ColorSampler
    {
        int sample(int x, int y, int z);
    }
}
