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
    public boolean isAbsent(BloomFilterValue bloomFilterValue)
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
    public void add(BloomFilterValue bloomFilterValue)
    {
        if (bloomFilterValue.getFilterValue() >= 0 && bloomFilterValue.getFilterValue() < filterArray.length)
        {
            filterArray[bloomFilterValue.getFilterValue()] = true;
            return;
        }

        throw new RuntimeException("Invalid index value");
    }

    /**
     * Removes an element from the Bloom filter by clearing the bit
     * at the specified index.
     * <p>
     * Note that standard Bloom filters do not support removal of elements
     * without introducing false negatives. This method directly clears
     * a bit and should be used with caution.
     *
     * @param index the index of the bit to clear
     * @throws RuntimeException if the index is out of bounds
     */
    public void remove(int index)
    {
        if (index >= 0 && index < filterArray.length)
        {
            filterArray[index] = false;
            return;
        }

        throw new RuntimeException("Invalid index value");
    }

    /**
     * Returns the total number of bits currently set in the Bloom filter.
     * <p>
     * This provides a simple way to estimate how many elements have been
     * added (though due to possible hash collisions, this may slightly
     * overestimate the actual number of distinct elements).
     *
     * @return the count of bits set to {@code true} in the filter array
     */
    public int valuesCount()
    {
        int count = 0;
        for (boolean present: filterArray)
        {
            if (present)
            {
                count += 1;
            }
        }

        return count;
    }
}
