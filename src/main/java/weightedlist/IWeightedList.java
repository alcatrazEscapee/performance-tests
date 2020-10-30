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
public interface IWeightedList<E>
{
    IWeightedList<Object> EMPTY = new IWeightedList<>()
    {
        @Override
        public void add(double weight, Object element)
        {
            throw new UnsupportedOperationException("Cannot add to EMPTY weighted list instance");
        }

        @Override
        public Object get(Random random)
        {
            return null;
        }
    };

    @SuppressWarnings("unchecked")
    static <E> IWeightedList<E> empty()
    {
        return (IWeightedList<E>) EMPTY;
    }

    static <E> IWeightedList<E> singleton(E element)
    {
        return new IWeightedList<>()
        {
            @Override
            public void add(double weight, E element)
            {
                throw new UnsupportedOperationException("Cannot add to singleton weighted list");
            }

            @Override
            public E get(Random random)
            {
                return element;
            }
        };
    }

    void add(double weight, E element);

    E get(Random random);
}
