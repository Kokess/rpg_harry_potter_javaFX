package org.example.Items;

public abstract class Item {
    protected String name;
    public String getName(){
        return this.name;
    }

    public abstract void action();}