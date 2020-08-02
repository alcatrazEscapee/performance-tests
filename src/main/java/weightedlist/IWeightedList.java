package weightedlist;

import java.util.*;
import java.util.function.ToDoubleFunction;

/**
 * A weighted list implementation.
 * Supports custom singleton {@link IWeightedList#singleton(Object)} and empty {@link IWeightedList#empty()} versions
 *
 * @see ParallelListWeightedList
 * @see TreeMapWeightedList
 */
public interface IWeightedList<E> extends Iterable<E>
{
    IWeightedList<Object> EMPTY = new IWeightedList<>()
    {
        @Override
        public void add(double weight, Object element) {}

        @Override
        public Object get(Random random)
        {
            return null;
        }

        @Override
        public Collection<Object> values()
        {
            return Collections.emptyList();
        }

        @Override
        public boolean isEmpty()
        {
            return true;
        }

        @Override
        public Iterator<Object> iterator()
        {
            return Collections.emptyIterator();
        }
    };

    @SuppressWarnings("unchecked")
    static <E> IWeightedList<E> empty()
    {
        return (IWeightedList<E>) EMPTY;
    }

    static <E> IWeightedList<E> singleton(E element)
    {
        return new IWeightedList<E>()
        {
            private final Collection<E> elementSet = Collections.singleton(element);

            @Override
            public void add(double weight, E element) {}

            @Override
            public E get(Random random)
            {
                return element;
            }

            @Override
            public Collection<E> values()
            {
                return elementSet;
            }

            @Override
            public boolean isEmpty()
            {
                return false;
            }

            @Override
            public Iterator<E> iterator()
            {
                return elementSet.iterator();
            }

            @Override
            public String toString()
            {
                return "[" + element + "]";
            }
        };
    }

    void add(double weight, E element);

    default void add(Map<? extends E, ? extends Double> values)
    {
        for (Map.Entry<? extends E, ? extends Double> entry : values.entrySet())
        {
            add(entry.getValue(), entry.getKey());
        }
    }

    default void add(Collection<? extends E> values, ToDoubleFunction<E> weightFunction)
    {
        for (E entry : values)
        {
            add(weightFunction.applyAsDouble(entry), entry);
        }
    }

    E get(Random random);

    Collection<E> values();

    boolean isEmpty();
}
