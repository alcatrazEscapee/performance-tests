package weightedlist;

import java.util.*;

public class ParallelListWeightedList<E> implements IWeightedList<E>
{
    private final List<E> elements;
    private final List<Weighted<E>> weightedElements;
    private double totalWeight;

    public ParallelListWeightedList()
    {
        this.elements = new ArrayList<>();
        this.weightedElements = new ArrayList<>();
        this.totalWeight = 0;
    }

    @Override
    public void add(double weight, E element)
    {
        this.elements.add(element);
        this.weightedElements.add(new Weighted<>(element, weight));
        this.totalWeight += weight;
    }

    @Override
    public E get(Random random)
    {
        if (isEmpty())
        {
            return null;
        }
        double target = random.nextDouble() * totalWeight;
        double weight = 0;
        for (int i = 0; i < weightedElements.size(); i++)
        {
            weight += weightedElements.get(i).weight;
            if (weight > target)
            {
                return elements.get(i);
            }
        }
        return elements.get(elements.size() - 1);
    }

    @Override
    public Collection<E> values()
    {
        return elements;
    }

    @Override
    public boolean isEmpty()
    {
        return elements.isEmpty();
    }

    @Override
    public Iterator<E> iterator()
    {
        return elements.iterator();
    }

    private static final class Weighted<E>
    {
        final E element;
        final double weight;

        Weighted(E element, double weight)
        {
            this.element = element;
            this.weight = weight;
        }
    }
}
