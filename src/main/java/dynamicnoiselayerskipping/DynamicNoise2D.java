package dynamicnoiselayerskipping;

public abstract class DynamicNoise2D implements Noise2D
{
    protected final float min, max;

    public DynamicNoise2D(float min, float max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public Noise2D octaves(int octaves, float persistence)
    {
        final float[] frequency = new float[octaves];
        final float[] amplitude = new float[octaves];
        float totalAmplitude = 0;
        for (int i = 0; i < octaves; i++)
        {
            float amp = (float) Math.pow(persistence, i + 1);

            frequency[i] = 1f / (1 << (octaves - i - 1));
            amplitude[i] = amp;
            totalAmplitude += amp;
        }

        final float[] upperThresholds = new float[octaves], lowerThresholds = new float[octaves];
        float remainingAmplitude = totalAmplitude;
        for (int i = 0; i < octaves; i++)
        {
            remainingAmplitude -= amplitude[i];

            upperThresholds[i] = -remainingAmplitude * min;
            lowerThresholds[i] = -remainingAmplitude * max;
        }

        final float totalMin = totalAmplitude * min, totalMax = totalAmplitude * max;

        return new DynamicNoise2D(totalMin, totalMax) {
            @Override
            public float noise(float x, float y)
            {
                float value = 0;
                for (int i = 0; i < octaves; i++)
                {
                    value += DynamicNoise2D.this.noise(x * frequency[i], y * frequency[i]) * amplitude[i];
                }
                return value;
            }

            @Override
            public boolean above(float x, float y, float t)
            {
                if (t > this.max) return false;
                if (t < this.min) return true;
                float value = -t;
                for (int i = 0; i < octaves; i++)
                {
                    value += DynamicNoise2D.this.noise(x * frequency[i], y * frequency[i]) * amplitude[i];
                    if (value > upperThresholds[i]) return true;
                    if (value < lowerThresholds[i]) return false;
                }
                return value > 0;
            }
        };
    }

    @Override
    public boolean above(float x, float y, float t)
    {
        return t <= this.max && (t < this.min || noise(x, y) > t);
    }
}
