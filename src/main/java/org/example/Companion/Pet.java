package org.example.Companion;

public class Pet {
    private String name;
    private int life;
    public Pet(String name){
        this.name = name;
        life = 50;
    }
    //get methods
    public String getName(){
        return this.name;
    }
    public int getLife(){
        return this.life;
    }
}
