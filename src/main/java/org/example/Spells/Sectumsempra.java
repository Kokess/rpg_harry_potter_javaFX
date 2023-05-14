package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class Sectumsempra extends AuthorizedSpell{

    public Sectumsempra(){
        this.name = "Sectumsempra";
        this.makeDamage = true;

    }

    @Override
    public void actionOnCharacter(Character character) {
        System.out.println("Sectumsempra inglige 50 points de dégâts!");
        character.take_dmg(50, character.getDefense());

    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}
