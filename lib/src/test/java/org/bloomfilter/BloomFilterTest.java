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
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(1);
        assertFalse(bloomFilter.isAbsent(new ExampleBloomFilterValue(20)));
    }

    @Test
    void throwRuntimeExceptionIfFilterValueIsGreaterThanSizeOfFilterArray()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(1);
        assertThrows(RuntimeException.class, () -> bloomFilter.add(new ExampleBloomFilterValue(20)));
    }

    @Test
    void testCountValuesReturnsCorrectCount()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(5);
        bloomFilter.add(new ExampleBloomFilterValue(1));
        bloomFilter.add(new ExampleBloomFilterValue(2));
        bloomFilter.add(new ExampleBloomFilterValue(3));
        bloomFilter.add(new ExampleBloomFilterValue(4));

        assertEquals(4,  bloomFilter.valuesCount());
    }

    @Test
    void testRemoveReturnsTrueForIsAbsentCheck()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(5);
        ExampleBloomFilterValue bloomFilterValue = new ExampleBloomFilterValue(0);
        bloomFilter.add(bloomFilterValue);
        assertFalse(bloomFilter.isAbsent(bloomFilterValue));
        bloomFilter.remove(bloomFilterValue.getFilterValue());
        assertTrue(bloomFilter.isAbsent(bloomFilterValue));
    }

    @Test
    void testRemovingDifferentValueReturnsFalseIsAbsentCheck()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(5);
        ExampleBloomFilterValue bloomFilterValue = new ExampleBloomFilterValue(0);
        bloomFilter.add(bloomFilterValue);
        assertFalse(bloomFilter.isAbsent(bloomFilterValue));
        bloomFilter.remove(1);
        assertFalse(bloomFilter.isAbsent(bloomFilterValue));
    }

    @Test
    void throwRuntimeExceptionForRemoveIfFilterValueIsGreaterThanSizeOfFilterArray()
    {
        BloomFilter<ExampleBloomFilterValue> bloomFilter = new BloomFilter<>(1);
        assertThrows(RuntimeException.class, () -> bloomFilter.remove(20));
    }
}
