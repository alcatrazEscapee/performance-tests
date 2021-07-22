package dynamicnoiselayerskipping;

@FunctionalInterface
public interface Noise2D
{
    float noise(float x, float y);

    default Noise2D octaves(int octaves, float persistence)
    {
        final float[] frequency = new float[octaves];
        final float[] amplitude = new float[octaves];
        for (int i = 0; i < octaves; i++)
        {
            float amp = (float) Math.pow(persistence, i + 1);

            frequency[i] = 1f / (1 << (octaves - i - 1));
            amplitude[i] = amp;
        }
        return (x, y) -> {
            float value = 0;
            for (int i = 0; i < octaves; i++)
            {
                value += Noise2D.this.noise(x * frequency[i], y * frequency[i]) * amplitude[i];
            }
            return value;
        };
    }

    default boolean above(float x, float y, float t)
    {
        return noise(x, y) > t;
    }

    default boolean below(float x, float y, float t)
    {
        return !above(x, y, t);
    }
}
