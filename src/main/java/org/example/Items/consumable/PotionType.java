package org.example.Items.consumable;

public enum PotionType {

    HEAL("heal"),
    STRENGTH("strenght"),
    MANA("mana"),
    SHIELD("shield"),
    LUCK("luck");

    private final String name;

    PotionType(String name){
        this.name = name;
    }
}

