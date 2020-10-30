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
