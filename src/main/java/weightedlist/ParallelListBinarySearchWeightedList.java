/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This weighted list implementation was modified from Fabric MC's WeightedBiomePicker (https://github.com/FabricMC/fabric)
 * It is used here under permission of the above license
 * It has been modified to use IWeightedList for the purposes of this benchmark
 */

package weightedlist;

import java.util.*;

public class ParallelListBinarySearchWeightedList<E> implements IWeightedList<E>
{
    private final List<Entry<E>> entries;
    private double totalWeight;

    public ParallelListBinarySearchWeightedList()
    {
        this.entries = new ArrayList<>();
        this.totalWeight = 0;
    }

    @Override
    public void add(double weight, E element)
    {
        totalWeight += weight;
        entries.add(new Entry<>(element, totalWeight));
    }

    @Override
    public E get(Random random)
    {
        if (entries.isEmpty())
        {
            return null;
        }
        double target = random.nextDouble() * totalWeight;
        int low = 0;
        int high = entries.size() - 1;
        while (low < high)
        {
            int mid = (high + low) >>> 1;
            if (target < entries.get(mid).maxWeight)
            {
                high = mid;
            }
            else
            {
                low = mid + 1;
            }
        }
        return entries.get(low).value;
    }

    private static class Entry<E>
    {
        private final E value;
        private final double maxWeight;

        private Entry(E value, double maxWeight)
        {
            this.value = value;
            this.maxWeight = maxWeight;
        }
    }
}
