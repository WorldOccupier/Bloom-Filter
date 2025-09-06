package org.bloomfilter;

/**
 * Represents a value that can be added to a Bloom filter.
 * <p>
 * Each subclass must implement {@link #getFilterValue()} to return an integer
 * that determines the index in the Bloom filter's bit array.
 * <p>
 * This abstraction allows the Bloom filter to work with any type of object
 * as long as it can provide a consistent integer value suitable for hashing.
 */
public abstract class BloomFilterValue {

    /**
     * Returns an integer that represents the position in the Bloom filter's bit array.
     * <p>
     * This value should be consistent for the same logical element across
     * multiple calls, and it should ideally be distributed uniformly to
     * minimize collisions.
     *
     * @return the integer index corresponding to this value in the Bloom filter
     */
    public abstract int getFilterValue();
}
