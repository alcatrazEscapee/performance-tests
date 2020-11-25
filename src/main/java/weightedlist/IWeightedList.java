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

/**
 * A weighted list implementation.
 * Supports custom singleton {@link IWeightedList#singleton(Object)} and empty {@link IWeightedList#empty()} versions
 *
 * @see ParallelListWeightedList
 * @see TreeMapWeightedList
 */
public interface IWeightedList<E>
{
    IWeightedList<Object> EMPTY = new IWeightedList<Object>()
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
        return new IWeightedList<E>()
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
