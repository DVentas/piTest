package org.daniel.ventas.model;


import java.util.List;

public class MixResult implements Comparable<MixResult>{

    private Character character;
    private Integer count;
    private List<Integer> indexes;

    public MixResult(final Character character, final Integer count, final List<Integer> indexes) {
        this.character = character;
        this.count = count;
        this.indexes = indexes;
    }

    @Override
    public int compareTo(final MixResult other){
        int compareMix = count.compareTo(other.getCount());

        if (compareMix == 0)
            compareMix = other.getCharacter().compareTo(character);

        return compareMix;
    }
    
    public Character getCharacter() {
        return character;
    }

    public Integer getCount() {
        return count;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

}
