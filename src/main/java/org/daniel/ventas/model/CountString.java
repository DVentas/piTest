package org.daniel.ventas.model;

import java.util.List;
import java.util.Map;

public class CountString implements Comparable<CountString>{
    private Integer count;

    private Map<Character, List<Integer>> charsWithSameCountWithIndexStrings;

    public CountString (final Integer count, final Map<Character, List<Integer>> charsWithSameCountWithIndexStrings) {
        this.count = count;
        this.charsWithSameCountWithIndexStrings = charsWithSameCountWithIndexStrings;
    }

    public CountString (final Integer count) {
        this.count = count;
        this.charsWithSameCountWithIndexStrings = null;
    }

    @Override
    public int compareTo(final CountString other){
        return count.compareTo(other.getCount());
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public Map<Character, List<Integer>> getCharsWithSameCountWithIndexStrings() {
        return this.charsWithSameCountWithIndexStrings;
    }

    public void setCharsWithSameCountWithIndexStrings(final Map<Character, List<Integer>> charsWithSameCountWithIndexStrings) {
        this.charsWithSameCountWithIndexStrings = charsWithSameCountWithIndexStrings;
    }
}
