# Performance Tests

This is a collection of JMH performance tests I have built over time. These are inspired by cpw's performance testing stream and own performance test repository. As a result, this includes mostly cases from my own mods or vanilla code which I have tried to (or wondered about) if they could be optimized.

## Weighted Lists

This compares multiple different weighted list implementations, using various different size lists.

- `ParallelListWeightedList` is the simplest possible implementation. It has an O(n) `get` complexity. This is very similar to a Minecraft's `WeightedRandom` (mcp) or `WeightedPicker` (yarn).
- `ParallelListBinarySearchWeightedList` is a similar implementation to the previous, but instead of using a simple linear search, it uses a binary search, and relies on a sorted property of the weighted elements, giving it a O(log(n)) `get` complexity. This style of list is used in Fabric API's [WeightedBiomePicker](https://github.com/FabricMC/fabric/blob/a89534abff11127996c0067b1628a6ab02d50524/fabric-biome-api-v1/src/main/java/net/fabricmc/fabric/impl/biome/WeightedBiomePicker.java)
- `TreeMapWeightedList` is an implementation adapted from [this Stack Overflow answer](https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java). It also has an O(log(n)) `get` complexity. This style of list is implemented and used in [Realistic Ore Veins](https://github.com/alcatrazEscapee/ore-veins/blob/faa22c4d1b1117068b4346262a5a517731ee89f8/src/main/java/com/alcatrazescapee/oreveins/util/collections/WeightedList.java)
- `AliasTableWeightedList` is based on the [Alias Method](https://en.wikipedia.org/wiki/Alias_method), and is borrowed from [RandomSelector](https://github.com/ogregoire/fror-common/blob/master/src/main/java/be/fror/common/collection/RandomSelector.java), which has been modified to fit this test and used under the Apache License 2.0. This has one slight difference in that the `init` method is required before the list can be used. It has an O(1) `get` complexity, however a quite complicated setup process and is easily the most complex algorithm in this test.
- Finally, only for testing the case of a list with one element, `IWeighted#singleton` is used. This is a custom implementation just for single element lists, and it could be used in conjunction with any of the above other lists.

The results mainly confirm the expected theoretical complexity outlined above: `AliasTableWeightedList` is the fastest under larger sizes, `TreeMapWeightedList` and `ParallelListBinarySearchWeightedList` are both comparable, and `ParallelListWeightedList` is the slowest. However, there are some interesting takeaways from these results:

- Firstly, `IWeightedList#singleton` is a very worthwhile investment if single element weighted lists are a common occurrence. This is especially true in a situation where an `IWeighted` may be empty, a single element, a uniform list, or a weighted list. In this case, having dedicated or specialized implementations for specific constraints upon the list may improve the overall performance.
- Secondly, the performance thresholds between the different theoretical complexities are interesting to note, and should be taken into account before choosing an implementation. `AliasTableWeightedList` only starts to perform better than the two O(log(n)) implementations around the 100 element scale.
- Finally, `ParallelListWeightedList` is - predictably - outperformed by every other implementation here (except for `IWeightedList#singleton`), and should probably never be used.


## Predicate Map

This is an example structure which is both used very often, and used with large collection sizes. Some examples include crafting recipes, ore generation (block state -> block state), or custom machine recipes.

The standard predicate map represents the vanilla crafting recipe approach. In reality the keys are much more complex, and the need to avoid querying multiple keys is more important. However, for most cases the map represents a single predicate -> value. The index map represents the simplest way of using a hashable key (for example items or blocks) to narrow down the possible values. The fast index map represents the case where the recipe list can be simplified so no complex predicates are present. This is not always the case (e.g. in crafting recipes), but can sometimes be used.

- Standard loops vastly outperform streams in this instance and should be used in performance-critical situations.
- Using an index based map also vastly improves performance. Actual gains will vary based on how effective the index is.
- The fast index map does not offer much benefit over the index map, although this may be mostly due to the effectiveness of the initial index map. Actual effects may vary based on the effectiveness of the index.
