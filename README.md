# Bloom Filter (Java)

A **Bloom filter** is a space-efficient probabilistic data structure used to test whether an element is a member of a set.  
It can return **false positives** but never **false negatives**, meaning a query will return either:

- **Possibly in the set**, or
- **Definitely not in the set**

This implementation uses an array of `AtomicBoolean` for **thread-safe operations**.

---

## Features
- Add elements to the filter.
- Check membership (absence = definitely not in set).
- Remove elements (non-standard; may cause false negatives).
- Count the number of bits currently set.
- Thread-safe using `AtomicBoolean[]`.

---

## How It Works

1. When an element is added, the bit corresponding to its `filterValue` is set to `true`.
2. Membership check reads the bit at the same index:
    - `0` → definitely not present
    - `1` → possibly present (false positives possible)
3. `remove()` clears the bit, but this can introduce false negatives and is non-standard.

---

## Usage Example

```java
import org.bloomfilter.BloomFilter;
import org.bloomfilter.BloomFilterValue;

class MyValue implements BloomFilterValue {
    private final int value;

    MyValue(int value) {
        this.value = value;
    }

    @Override
    public int getFilterValue() {
        return value % 100; // simple example
    }
}

public class Main {
    public static void main(String[] args) {
        BloomFilter<MyValue> filter = new BloomFilter<>(100);
        MyValue item = new MyValue(42);

        filter.add(item);
        System.out.println(filter.isAbsent(item)); // false
        System.out.println("Values set: " + filter.valuesCount());

        filter.remove(42);
        System.out.println(filter.isAbsent(item)); // true
    }
}
