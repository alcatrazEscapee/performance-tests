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
