package org.bloomfilter;

/**
 * A Bloom filter is a space-efficient probabilistic data structure used to test 
 * whether an element is a member of a set. False positive matches are possible, 
 * but false negatives are not — meaning a query will return either 
 * "possibly in the set" or "definitely not in the set".
 * <p>
 * Bloom filters achieve memory efficiency by using a fixed-size bit array 
 * along with multiple independent hash functions. When an element is added, 
 * several bits in the array are set according to the hash functions. 
 * Membership checks then verify the corresponding bits to determine potential presence.
 * <p>
 * Bloom filters are commonly used in scenarios where:
 * <ul>
 *   <li>Memory is limited and exact set representation is too costly.</li>
 *   <li>Fast approximate membership tests are needed, such as in caching layers 
 *       or database query optimization.</li>
 *   <li>Large-scale systems require probabilistic filtering to reduce unnecessary 
 *       computations or network calls.</li>
 * </ul>
 * <p>
 * This class provides methods to add elements and to check for potential membership, 
 * while maintaining the space-efficient, probabilistic characteristics of a Bloom filter.
 */
public class BloomFilter<T extends BloomFilterValue> {
    private static final int DEFAULT_SIZE = 10000;
    private final boolean[] filterArray;

    public BloomFilter(int size)
    {
        filterArray = new boolean[size];
    }

    public BloomFilter()
    {
        filterArray = new boolean[DEFAULT_SIZE];
    }

    /**
     * Checks whether the specified element is absent in the Bloom filter.
     * <p>
     * Returns {@code true} if the bit corresponding to the element's {@link BloomFilterValue.filterValue}
     * is not set, indicating that the element is definitely not in the set.
     * Returns {@code false} if the bit is set, which means the element might
     * be present (Bloom filters can produce false positives but not false negatives).
     *
     * @param bloomFilterValue the element to check for absence
     * @return {@code true} if the element is definitely not in the filter;
     *         {@code false} if it may be present
     */
    public boolean isAbsent(T bloomFilterValue)
    {
        if (bloomFilterValue.getFilterValue() >= 0 && bloomFilterValue.getFilterValue() < filterArray.length)
        {
            return !filterArray[bloomFilterValue.getFilterValue()];
        }

        return false;
    }

    /**
     * Adds the specified element to the Bloom filter by setting the bit
     * corresponding to the element's {@link BloomFilterValue.filterValue}.
     * <p>
     * Once a bit is set, it may cause future membership checks for this
     * or other elements to return {@code false} in {@link #isAbsent},
     * reflecting the probabilistic nature of Bloom filters.
     *
     * @param bloomFilterValue the element to add to the filter
     */
    public void add(T bloomFilterValue)
    {
        if (bloomFilterValue.getFilterValue() >= 0 && bloomFilterValue.getFilterValue() < filterArray.length)
        {
            filterArray[bloomFilterValue.getFilterValue()] = true;
            return;
        }

        throw new RuntimeException("Invalid index value");
    }
}
