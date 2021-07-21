package dynamicnoiselayerskipping;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DynamicNoiseLayerSkippingTests
{
    private static final Random RANDOM = new Random();

    @Test
    public void testSimplexWithinBounds()
    {
        int seed = (int) System.currentTimeMillis();
        Noise2D noise = SimplexNoise2D.defaultSimplex(seed);

        for (int i = 0; i < 10000; i++)
        {
            float x = RANDOM.nextFloat() * 1000, y = RANDOM.nextFloat() * 1000, v = noise.noise(x, y);
            Assertions.assertTrue(v <= 1);
            Assertions.assertTrue(v >= -1);
        }
    }

    @Test
    public void testDynamicEqualsBase()
    {
        int seed = (int) System.currentTimeMillis();
        Noise2D base = SimplexNoise2D.defaultSimplex(seed);
        Noise2D dynamic = SimplexNoise2D.dynamicSimplex(seed);

        for (int i = 0; i < 10000; i++)
        {
            float x = RANDOM.nextFloat() * 1000, y = RANDOM.nextFloat() * 1000;
            Assertions.assertEquals(base.noise(x, y), dynamic.noise(x, y));
        }
    }

    @Test
    public void testDynamicAboveEqualsBase()
    {
        int seed = (int) System.currentTimeMillis();
        Noise2D base = SimplexNoise2D.defaultSimplex(seed);
        Noise2D dynamic = SimplexNoise2D.dynamicSimplex(seed);

        for (int i = 0; i < 10000; i++)
        {
            float x = RANDOM.nextFloat() * 1000, y = RANDOM.nextFloat() * 1000, t = RANDOM.nextFloat();
            Assertions.assertEquals(base.above(x, y, t), dynamic.above(x, y, t));
        }
    }

    @Test
    public void testDynamicOctavesEqualsBase()
    {
        int seed = (int) System.currentTimeMillis();
        Noise2D base = SimplexNoise2D.defaultSimplex(seed).octaves(4, 0.5f);
        Noise2D dynamic = SimplexNoise2D.dynamicSimplex(seed).octaves(4, 0.5f);

        for (int i = 0; i < 10000; i++)
        {
            float x = RANDOM.nextFloat() * 1000, y = RANDOM.nextFloat() * 1000;
            Assertions.assertEquals(base.noise(x, y), dynamic.noise(x, y));
        }
    }

    @Test
    public void testDynamicOctavesAboveEqualsBase()
    {
        int seed = (int) System.currentTimeMillis();
        Noise2D base = SimplexNoise2D.defaultSimplex(seed).octaves(4, 0.5f);
        Noise2D dynamic = SimplexNoise2D.dynamicSimplex(seed).octaves(4, 0.5f);

        for (int i = 0; i < 10000; i++)
        {
            float x = RANDOM.nextFloat() * 1000, y = RANDOM.nextFloat() * 1000, t = RANDOM.nextFloat();
            Assertions.assertEquals(base.above(x, y, t), dynamic.above(x, y, t));
        }
    }
}
