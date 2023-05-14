package org.example.Items.consumable;

import org.example.Items.Item;

public class Potion extends Item {
    private PotionType type;
    private final int heal = 30;
    private final int mana = 30;
    private final int shield = 3;
    private final int luck = 3;
    private final int strength = 10;
    private int price;

    public Potion(PotionType type,int price) {
        this.type = type;
        this.price = price;
    }

    public String getType(){return this.type.name();}
    public int getHealEffect(){return this.heal;} //retourne le nombre de point de vie rendu par une potion de soin
    public int getManaEffect(){return this.mana;} //retourne le nombre de point de mana rendu par une potion de mana
    public int getStrengthEffect(){return this.strength;}
    public int getShieldEffect(){return this.shield;}
    public int getLuckEffect(){return this.luck;}
    public int getPrice(){return this.price;}
    public void action(){}


}
