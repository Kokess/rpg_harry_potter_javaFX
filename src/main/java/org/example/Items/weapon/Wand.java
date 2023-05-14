package org.example.Items.weapon;

import org.example.Items.Item;

public class Wand extends Item{
    private int size;
    private String core;

    public Wand(int size, String core){
        this.size = size;
        this.core = core;
    }

    public String getCore(){return this.core;}
    public int getSize(){return this.size;}
    public void action(){}

}

