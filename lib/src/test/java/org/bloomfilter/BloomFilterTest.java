package org.bloomfilter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    @Test
    void testReturnsTrueIfNotPresent() {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>();
        bloomFilter.add(new ExampleBloomFilterValue(10));
        assertTrue(bloomFilter.isAbsent(new ExampleBloomFilterValue(20)));
    }

    @Test
    void testReturnsFalseIfPresent() {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>();
        bloomFilter.add(new ExampleBloomFilterValue(10));
        assertFalse(bloomFilter.isAbsent(new ExampleBloomFilterValue(10)));
    }

    @Test
    void testReturnsFalseIfFilterValueGreaterThanSizeOfFilterArray() {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(10);
        assertFalse(bloomFilter.isAbsent(new ExampleBloomFilterValue(20)));
    }

    @Test
    void throwRuntimeExceptionIfFilterValueIsGreaterThanSizeOfFilterArray()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(10);
        assertThrows(RuntimeException.class, () -> bloomFilter.add(new ExampleBloomFilterValue(20)));
    }
}
