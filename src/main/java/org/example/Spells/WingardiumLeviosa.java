package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class WingardiumLeviosa extends AuthorizedSpell {

    public WingardiumLeviosa(String name, boolean makeDamage){
        this.name = name;
        this.makeDamage = makeDamage; //savoir si c'est un sort qui fait des dégats.
        this.interactWithItem = false;
        this.damage =40;
    }

    public void fly(Character character, int defense){
        System.out.println("Vous faites voler une pierre et la faîte tomber sur l'ennemie! Vous infliger 40 points de dégâts à l'ennemie!");
        character.take_dmg(this.damage,defense);
    }

    @Override
    public void actionOnCharacter(Character character) {
        if(isAttackSuccessful(this.successPercentage)){
            fly(character, character.getDefense());
        }
        else{
            textFailedSpell();
        }

    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}
