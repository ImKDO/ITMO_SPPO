package org.example.Creature;

public abstract class Creature {
    public String name;
    int count = 0;

    public Creature(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        return this.name.equals(o);
    }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public int hashCode(){return count += 1;}
}
