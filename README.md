# Java Performance Tests

This is a collection of JMH benchmarks which I have developed over time for various Minecraft mods.

### Weighted Lists

Two primary weighted list implementations were tested. The first based on a `TreeMap<Double, E>`, which has an `O(log(n))` get complexity. The second is a much simpler implementation using a `List<Weighted<E>>`.

The results show that the `TreeMap` based implementation is comparable with low sizes and much faster in larger sizes.

In addition, the use of a dedicated implementation for single element weighted lists is *much* faster than using either expandable implementation and justifies the existance of `IWeightedList.singleton()`