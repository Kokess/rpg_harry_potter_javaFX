package org.example.Items.weapon;


import org.example.Items.Item;

public class Weapon extends Item {
    private int damage;
    private int critic;


    public Weapon(String name, int damage, int critic){
        this.name = name;
        this.damage = damage;
        this.critic = critic;
    }
    //get methods
    public String getName(){
        return this.name;
    }
    public int getDamage(){
        return this.damage;
    }
    public int getCritic(){
        return this.critic;
    }
    public void action(){}

}
