package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class BasicAttack extends AuthorizedSpell {

    public BasicAttack(String name, boolean makeDamage){
        this.name = name;
        this.makeDamage = makeDamage; //savoir si c'est un sort qui fait des dégats.
        this.interactWithItem = false;
    }

    @Override
    public void actionOnCharacter(Character character) {

    }
    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}

