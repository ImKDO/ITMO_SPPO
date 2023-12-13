package org.example.Creatures;

public abstract class Creatures {
    public String name;
    int count = 0;

    public Creatures(String name) {
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
