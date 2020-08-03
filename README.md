# Performance Tests

This is a collection of JMH performance tests I have built over time. These are inspired by cpw's performance testing stream and own performance test repository. As a result, this includes mostly cases from my own mods or vanilla code which I have tried to (or wondered about) if they could be optimized.

### Weighted List

This compares two example weighted list implementations, using various different size lists. Vanilla uses a comparable implementation to `ParallelListWeightedList`. Realistic Ore Veins and TerraFirmaCraft both use the `TreeMapWeightedList`.

Results show that both implementations are comparable for smaller list sizes, but the tree map outperforms the parallel list for larger sizes.

These tests also show it is very worth it to keep and use the custom `IWeightedList#singleton` implementation as it is many times faster than either list with a single item list.

### Predicate Map

This is an example structure which is both used very often, and used with large collection sizes. Some examples include crafting recipes, ore generation (block state -> block state), or custom machine recipes.

The standard predicate map represents the vanilla crafting recipe approach. In reality the keys are much more complex, and the need to avoid querying multiple keys is more important. However, for most cases the map represents a single predicate -> value. The index map represents the simplest way of using a hashable key (for example items or blocks) to narrow down the possible values. The fast index map represents the case where the recipe list can be simplified so no complex predicates are present. This is not always the case (e.g. in crafting recipes), but can sometimes be used.

- Standard loops vastly outperform streams in this instance and should be used in performance-critical situations.
- Using an index based map also vastly improves performance. Actual gains will vary based on how effective the index is.
- The fast index map does not offer much benefit over the index map, although this may be mostly due to the effectiveness of the initial index map. Actual effects may vary based on the effectiveness of the index.
