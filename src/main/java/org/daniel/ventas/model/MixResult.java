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

<<<<<<< Updated upstream
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        MixResult guest = (MixResult) obj;
        return character == guest.getCharacter();
    }


=======
>>>>>>> Stashed changes
    public Character getCharacter() {
        return character;
    }

<<<<<<< Updated upstream
    public void setCharacter(Character character) {
        this.character = character;
    }

=======
>>>>>>> Stashed changes
    public Integer getCount() {
        return count;
    }

<<<<<<< Updated upstream
    public void setCount(Integer count) {
        this.count = count;
    }

=======
>>>>>>> Stashed changes
    public List<Integer> getIndexes() {
        return indexes;
    }

<<<<<<< Updated upstream
    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }
=======
>>>>>>> Stashed changes
}
