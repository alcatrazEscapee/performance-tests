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
