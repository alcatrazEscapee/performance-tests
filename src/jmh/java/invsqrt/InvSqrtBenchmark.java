/*
 * Copyright 2020 Alex O'Neill (AlcatrazEscapee)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package invsqrt;

import org.openjdk.jmh.annotations.Benchmark;

public class InvSqrtBenchmark
{
    @Benchmark
    public double fastInvSqrt()
    {
        double sum = 0;
        for (double i = 0; i < 10_000; i++)
        {
            sum += fastInvSqrt(i);
        }
        return sum;
    }

    @Benchmark
    public double javaSqrt()
    {
        double sum = 0;
        for (double i = 0; i < 10_000; i++)
        {
            sum += (1 / Math.sqrt(i));
        }
        return sum;
    }

    /**
     * From https://en.wikipedia.org/wiki/Fast_inverse_square_root
     */
    public static double fastInvSqrt(double x)
    {
        double d0 = 0.5D * x;
        long i = Double.doubleToRawLongBits(x);
        i = 0x5fe6eb50c7b537aaL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x = x * (1.5D - d0 * x * x);
        return x;
    }
}
