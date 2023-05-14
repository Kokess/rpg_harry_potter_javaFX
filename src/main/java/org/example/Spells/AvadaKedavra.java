package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class AvadaKedavra extends ForbiddenSpell{

    public AvadaKedavra(){
        this.name = "Avada Kedavra";
        this.makeDamage = true;
        this.successRate =20;
    }
    @Override
    public void action(Character character) {
        this.userInteraction.print("AVADA KEDAVRA!");
        character.take_dmg(character.getLife(),0);
    }

    @Override
    public void actionOnCharacter(Character character) {

    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}