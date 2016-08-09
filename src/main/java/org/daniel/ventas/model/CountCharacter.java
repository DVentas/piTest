package org.daniel.ventas.model;


import java.util.List;

public class CountCharacter {

    private Integer count;
    private List<Integer> indexes;

    public CountCharacter (final Integer count, final List<Integer> indexes) {
        this.count = count;
        this.indexes = indexes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }
}
