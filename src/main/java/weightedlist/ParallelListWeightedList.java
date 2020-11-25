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

public class ParallelListWeightedList<E> implements IWeightedList<E>
{
    private final List<Entry<E>> weightedElements;
    private double totalWeight;

    public ParallelListWeightedList()
    {
        this.weightedElements = new ArrayList<>();
        this.totalWeight = 0;
    }

    @Override
    public void add(double weight, E element)
    {
        this.weightedElements.add(new Entry<>(element, weight));
        this.totalWeight += weight;
    }

    @Override
    public E get(Random random)
    {
        if (weightedElements.isEmpty())
        {
            return null;
        }
        double target = random.nextDouble() * totalWeight;
        double weight = 0;
        for (Entry<E> entry : weightedElements)
        {
            weight += entry.weight;
            if (weight > target)
            {
                return entry.value;
            }
        }
        return weightedElements.get(weightedElements.size() - 1).value;
    }

    private static final class Entry<E>
    {
        final E value;
        final double weight;

        Entry(E value, double weight)
        {
            this.value = value;
            this.weight = weight;
        }
    }
}
