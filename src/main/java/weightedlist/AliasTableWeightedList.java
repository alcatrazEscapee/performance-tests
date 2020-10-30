/*
 * Copyright 2015 Olivier Grégoire.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This class has been modified from code from the original author under permission from the above license.
 * The license is included in this repository as per the license restrictions.
 */

package weightedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AliasTableWeightedList<E> implements IWeightedList<E>
{
    private final List<Entry<E>> entries;

    private double[] probabilities;
    private int[] alias;

    public AliasTableWeightedList()
    {
        this.entries = new ArrayList<>();
    }

    @Override
    public void add(double weight, E element)
    {
        entries.add(new Entry<>(element, weight));
    }

    @Override
    public E get(Random random)
    {
        int column = random.nextInt(probabilities.length);
        int index = random.nextDouble() < probabilities[column] ? column : alias[column];
        return entries.get(index).value;
    }

    /**
     * For the purposes of this benchmark, we are not interested in setup time as it's assume to be inconsequential to the query time.
     * Thus, this is called externally, during static initialization of the list
     */
    public void init()
    {
        int size = entries.size();
        double totalWeight = 0d;
        double[] probabilities = new double[size];
        for (int i = 0; i < size; i++)
        {
            double weight = entries.get(i).weight;
            probabilities[i] = weight;
            totalWeight += weight;
        }
        for (int i = 0; i < size; i++)
        {
            probabilities[i] /= totalWeight;
        }

        double average = 1d / size;
        int[] small = new int[size];
        int smallSize = 0;
        int[] large = new int[size];
        int largeSize = 0;

        for (int i = 0; i < size; i++)
        {
            if (probabilities[i] < average)
            {
                small[smallSize++] = i;
            }
            else
            {
                large[largeSize++] = i;
            }
        }

        double[] pr = new double[size];
        int[] al = new int[size];
        this.probabilities = pr;
        this.alias = al;

        while (largeSize != 0 && smallSize != 0)
        {
            int less = small[--smallSize];
            int more = large[--largeSize];
            pr[less] = probabilities[less] * size;
            al[less] = more;
            probabilities[more] += probabilities[less] - average;
            if (probabilities[more] < average)
            {
                small[smallSize++] = more;
            }
            else
            {
                large[largeSize++] = more;
            }
        }
        while (smallSize != 0)
        {
            pr[small[--smallSize]] = 1d;
        }
        while (largeSize != 0)
        {
            pr[large[--largeSize]] = 1d;
        }
    }

    private static class Entry<E>
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
