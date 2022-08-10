package cubicsampler;

public class Vec3
{
    public static Vec3 fromRGB24(int rgb24)
    {
        double x = (rgb24 >> 16 & 255) / 255D;
        double y = (rgb24 >> 8 & 255) / 255D;
        double z = (rgb24 & 255) / 255D;
        return new Vec3(x, y, z);
    }

    public static final Vec3 ZERO = new Vec3(0, 0, 0);

    private final double x;
    private final double y;
    private final double z;

    public Vec3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x()
    {
        return x;
    }

    public double y()
    {
        return y;
    }

    public double z()
    {
        return z;
    }

    public Vec3 add(Vec3 vec)
    {
        return add(vec.x, vec.y, vec.z);
    }

    public Vec3 add(double x, double y, double z)
    {
        return new Vec3(this.x + x, this.y + y, this.z + z);
    }

    public Vec3 scale(double amplitude)
    {
        return this.multiply(amplitude, amplitude, amplitude);
    }

    public Vec3 multiply(double x, double y, double z)
    {
        return new Vec3(this.x * x, this.y * y, this.z * z);
    }
}
