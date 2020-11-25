/*
 * Copyright 2020 Alex O'Neill (AlcatrazEscapee)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package weightedlist;

import java.util.*;

public class TreeMapWeightedList<E> implements IWeightedList<E>
{
    private final NavigableMap<Double, E> map;
    private double totalWeight;

    public TreeMapWeightedList()
    {
        this.totalWeight = 0;
        this.map = new TreeMap<>();
    }

    public void add(double weight, E element)
    {
        if (weight > 0)
        {
            totalWeight += weight;
            map.put(totalWeight, element);
        }
    }

    public E get(Random random)
    {
        double value = random.nextDouble() * totalWeight;
        return map.higherEntry(value).getValue();
    }
}
