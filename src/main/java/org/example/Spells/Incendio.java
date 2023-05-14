package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class Incendio extends AuthorizedSpell{
    private int numberRoundEffect;
    public Incendio(){
        this.name = "Incendio";
        this.makeDamage = true;
        this.damage = 10;
        this.interactWithItem = false;
        this.successPercentage = 80;
        this.numberRoundEffect = 3;
    }
    @Override
    public void actionOnCharacter(Character character) {
        //userInteraction.print("Le sort incendio brûle la cible pendant 3 tours et inflige 10 points de dégâts");
        character.take_dmg(this.damage,character.getDefense());
    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
    public int getNumberRoundEffect(){
        return this.numberRoundEffect;
    }
}