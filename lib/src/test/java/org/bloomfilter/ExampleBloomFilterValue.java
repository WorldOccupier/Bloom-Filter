package org.bloomfilter;

public class ExampleBloomFilterValue extends BloomFilterValue {
    private final int filterValue;

    ExampleBloomFilterValue(int filterValue)
    {
        this.filterValue = filterValue;
    }

    @Override
    public int getFilterValue()
    {
        return filterValue;
    }
}
