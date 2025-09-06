package org.bloomfilter;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class BloomFilterValue {
    @Getter(AccessLevel.PUBLIC)
    private int filterValue = 0;
}
