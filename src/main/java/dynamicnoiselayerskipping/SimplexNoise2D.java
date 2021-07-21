/*
 * This was originally part of FastNoise (https://github.com/Auburn/FastNoise) and has been included as per the MIT license:
 *
 * MIT License
 *
 * Copyright(c) 2020 Jordan Peck (jordan.me2@gmail.com)
 * Copyright(c) 2020 Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files(the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions :
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dynamicnoiselayerskipping;

public class SimplexNoise2D
{
    private static final int PRIME_X = 501125321;
    private static final int PRIME_Y = 1136930381;
    private static final float SQRT3 = (float) 1.7320508075688772935274463415059;
    private static final float F2 = 0.5f * (SQRT3 - 1);
    private static final float G2 = (3 - SQRT3) / 6;
    private static final float[] GRADIENTS_2D = {
        0.130526192220052f, 0.99144486137381f, 0.38268343236509f, 0.923879532511287f, 0.608761429008721f, 0.793353340291235f, 0.793353340291235f, 0.608761429008721f,
        0.923879532511287f, 0.38268343236509f, 0.99144486137381f, 0.130526192220051f, 0.99144486137381f, -0.130526192220051f, 0.923879532511287f, -0.38268343236509f,
        0.793353340291235f, -0.60876142900872f, 0.608761429008721f, -0.793353340291235f, 0.38268343236509f, -0.923879532511287f, 0.130526192220052f, -0.99144486137381f,
        -0.130526192220052f, -0.99144486137381f, -0.38268343236509f, -0.923879532511287f, -0.608761429008721f, -0.793353340291235f, -0.793353340291235f, -0.608761429008721f,
        -0.923879532511287f, -0.38268343236509f, -0.99144486137381f, -0.130526192220052f, -0.99144486137381f, 0.130526192220051f, -0.923879532511287f, 0.38268343236509f,
        -0.793353340291235f, 0.608761429008721f, -0.608761429008721f, 0.793353340291235f, -0.38268343236509f, 0.923879532511287f, -0.130526192220052f, 0.99144486137381f,
        0.130526192220052f, 0.99144486137381f, 0.38268343236509f, 0.923879532511287f, 0.608761429008721f, 0.793353340291235f, 0.793353340291235f, 0.608761429008721f,
        0.923879532511287f, 0.38268343236509f, 0.99144486137381f, 0.130526192220051f, 0.99144486137381f, -0.130526192220051f, 0.923879532511287f, -0.38268343236509f,
        0.793353340291235f, -0.60876142900872f, 0.608761429008721f, -0.793353340291235f, 0.38268343236509f, -0.923879532511287f, 0.130526192220052f, -0.99144486137381f,
        -0.130526192220052f, -0.99144486137381f, -0.38268343236509f, -0.923879532511287f, -0.608761429008721f, -0.793353340291235f, -0.793353340291235f, -0.608761429008721f,
        -0.923879532511287f, -0.38268343236509f, -0.99144486137381f, -0.130526192220052f, -0.99144486137381f, 0.130526192220051f, -0.923879532511287f, 0.38268343236509f,
        -0.793353340291235f, 0.608761429008721f, -0.608761429008721f, 0.793353340291235f, -0.38268343236509f, 0.923879532511287f, -0.130526192220052f, 0.99144486137381f,
        0.130526192220052f, 0.99144486137381f, 0.38268343236509f, 0.923879532511287f, 0.608761429008721f, 0.793353340291235f, 0.793353340291235f, 0.608761429008721f,
        0.923879532511287f, 0.38268343236509f, 0.99144486137381f, 0.130526192220051f, 0.99144486137381f, -0.130526192220051f, 0.923879532511287f, -0.38268343236509f,
        0.793353340291235f, -0.60876142900872f, 0.608761429008721f, -0.793353340291235f, 0.38268343236509f, -0.923879532511287f, 0.130526192220052f, -0.99144486137381f,
        -0.130526192220052f, -0.99144486137381f, -0.38268343236509f, -0.923879532511287f, -0.608761429008721f, -0.793353340291235f, -0.793353340291235f, -0.608761429008721f,
        -0.923879532511287f, -0.38268343236509f, -0.99144486137381f, -0.130526192220052f, -0.99144486137381f, 0.130526192220051f, -0.923879532511287f, 0.38268343236509f,
        -0.793353340291235f, 0.608761429008721f, -0.608761429008721f, 0.793353340291235f, -0.38268343236509f, 0.923879532511287f, -0.130526192220052f, 0.99144486137381f,
        0.130526192220052f, 0.99144486137381f, 0.38268343236509f, 0.923879532511287f, 0.608761429008721f, 0.793353340291235f, 0.793353340291235f, 0.608761429008721f,
        0.923879532511287f, 0.38268343236509f, 0.99144486137381f, 0.130526192220051f, 0.99144486137381f, -0.130526192220051f, 0.923879532511287f, -0.38268343236509f,
        0.793353340291235f, -0.60876142900872f, 0.608761429008721f, -0.793353340291235f, 0.38268343236509f, -0.923879532511287f, 0.130526192220052f, -0.99144486137381f,
        -0.130526192220052f, -0.99144486137381f, -0.38268343236509f, -0.923879532511287f, -0.608761429008721f, -0.793353340291235f, -0.793353340291235f, -0.608761429008721f,
        -0.923879532511287f, -0.38268343236509f, -0.99144486137381f, -0.130526192220052f, -0.99144486137381f, 0.130526192220051f, -0.923879532511287f, 0.38268343236509f,
        -0.793353340291235f, 0.608761429008721f, -0.608761429008721f, 0.793353340291235f, -0.38268343236509f, 0.923879532511287f, -0.130526192220052f, 0.99144486137381f,
        0.130526192220052f, 0.99144486137381f, 0.38268343236509f, 0.923879532511287f, 0.608761429008721f, 0.793353340291235f, 0.793353340291235f, 0.608761429008721f,
        0.923879532511287f, 0.38268343236509f, 0.99144486137381f, 0.130526192220051f, 0.99144486137381f, -0.130526192220051f, 0.923879532511287f, -0.38268343236509f,
        0.793353340291235f, -0.60876142900872f, 0.608761429008721f, -0.793353340291235f, 0.38268343236509f, -0.923879532511287f, 0.130526192220052f, -0.99144486137381f,
        -0.130526192220052f, -0.99144486137381f, -0.38268343236509f, -0.923879532511287f, -0.608761429008721f, -0.793353340291235f, -0.793353340291235f, -0.608761429008721f,
        -0.923879532511287f, -0.38268343236509f, -0.99144486137381f, -0.130526192220052f, -0.99144486137381f, 0.130526192220051f, -0.923879532511287f, 0.38268343236509f,
        -0.793353340291235f, 0.608761429008721f, -0.608761429008721f, 0.793353340291235f, -0.38268343236509f, 0.923879532511287f, -0.130526192220052f, 0.99144486137381f,
        0.38268343236509f, 0.923879532511287f, 0.923879532511287f, 0.38268343236509f, 0.923879532511287f, -0.38268343236509f, 0.38268343236509f, -0.923879532511287f,
        -0.38268343236509f, -0.923879532511287f, -0.923879532511287f, -0.38268343236509f, -0.923879532511287f, 0.38268343236509f, -0.38268343236509f, 0.923879532511287f,
    };

    public static Noise2D defaultSimplex(int seed)
    {
        return (x, y) -> SimplexNoise2D.calculateNoise(x, y, seed);
    }

    public static Noise2D dynamicSimplex(int seed)
    {
        return new DynamicNoise2D(-1, 1) {
            @Override
            public float noise(float x, float y)
            {
                return SimplexNoise2D.calculateNoise(x, y, seed);
            }
        };
    }

    @SuppressWarnings("NumericOverflow")
    public static float calculateNoise(float x, float y, int seed)
    {
        float skew = (x + y) * F2;
        x += skew;
        y += skew;

        int i = fastFloor(x);
        int j = fastFloor(y);
        float xi = x - i;
        float yi = y - j;

        i *= PRIME_X;
        j *= PRIME_Y;
        int i1 = i + PRIME_X;
        int j1 = j + PRIME_Y;

        float t = (xi + yi) * G2;
        float x0 = xi - t;
        float y0 = yi - t;

        float a0 = (2.0f / 3.0f) - x0 * x0 - y0 * y0;
        float value = (a0 * a0) * (a0 * a0) * gradientCoord(seed, i, j, x0, y0);

        float a1 = 2 * (1 - 2 * G2) * (1 / G2 - 2) * t + ((-2 * (1 - 2 * G2) * (1 - 2 * G2)) + a0);
        float x1 = x0 - (1 - 2 * G2);
        float y1 = y0 - (1 - 2 * G2);
        value += (a1 * a1) * (a1 * a1) * gradientCoord(seed, i1, j1, x1, y1);

        // Nested conditionals were faster than compact bit logic/arithmetic.
        float xmyi = xi - yi;
        if (t > G2)
        {
            if (xi + xmyi > 1)
            {
                float x2 = x0 + (3 * G2 - 2);
                float y2 = y0 + (3 * G2 - 1);
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i + (PRIME_X << 1), j + PRIME_Y, x2, y2);
                }
            }
            else
            {
                float x2 = x0 + G2;
                float y2 = y0 + (G2 - 1);
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i, j + PRIME_Y, x2, y2);
                }
            }

            if (yi - xmyi > 1)
            {
                float x3 = x0 + (3 * G2 - 1);
                float y3 = y0 + (3 * G2 - 2);
                float a3 = (2.0f / 3.0f) - x3 * x3 - y3 * y3;
                if (a3 > 0)
                {
                    value += (a3 * a3) * (a3 * a3) * gradientCoord(seed, i + PRIME_X, j + (PRIME_Y << 1), x3, y3);
                }
            }
            else
            {
                float x3 = x0 + (G2 - 1);
                float y3 = y0 + G2;
                float a3 = (2.0f / 3.0f) - x3 * x3 - y3 * y3;
                if (a3 > 0)
                {
                    value += (a3 * a3) * (a3 * a3) * gradientCoord(seed, i + PRIME_X, j, x3, y3);
                }
            }
        }
        else
        {
            if (xi + xmyi < 0)
            {
                float x2 = x0 + (1 - G2);
                float y2 = y0 - G2;
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i - PRIME_X, j, x2, y2);
                }
            }
            else
            {
                float x2 = x0 + (G2 - 1);
                float y2 = y0 + G2;
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i + PRIME_X, j, x2, y2);
                }
            }

            if (yi < xmyi)
            {
                float x2 = x0 - G2;
                float y2 = y0 - (G2 - 1);
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i, j - PRIME_Y, x2, y2);
                }
            }
            else
            {
                float x2 = x0 + G2;
                float y2 = y0 + (G2 - 1);
                float a2 = (2.0f / 3.0f) - x2 * x2 - y2 * y2;
                if (a2 > 0)
                {
                    value += (a2 * a2) * (a2 * a2) * gradientCoord(seed, i, j + PRIME_Y, x2, y2);
                }
            }
        }

        return value * 18.24196194486065f;
    }

    public static int fastFloor(float f)
    {
        return f < 0 ? (int) f - 1 : (int) f;
    }

    public static float gradientCoord(int seed, int xPrimed, int yPrimed, float xd, float yd)
    {
        int hash = hashPrimed(seed, xPrimed, yPrimed);
        hash ^= hash >> 15;
        hash &= 127 << 1;

        float xg = GRADIENTS_2D[hash];
        float yg = GRADIENTS_2D[hash | 1];

        return xd * xg + yd * yg;
    }

    public static int hashPrimed(int seed, int xPrimed, int yPrimed)
    {
        long hash = seed ^ xPrimed ^ yPrimed;
        hash *= 0x27d4eb2d;
        return (int) hash;
    }
}

