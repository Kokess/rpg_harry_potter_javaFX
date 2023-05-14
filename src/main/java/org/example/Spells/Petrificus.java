package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class Petrificus extends AuthorizedSpell{
    private int numberRoundEffect;
    public Petrificus(){
        this.name = "Petrificus";
        this.makeDamage = true;
        this.numberRoundEffect =2;

    }

    @Override
    public void actionOnCharacter(Character character) {
        //userInteraction.print("Le sort pétrifie la cible pendant "+this.numberRoundEffect +" tours!");
        character.setCanPlay(false);
    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}
