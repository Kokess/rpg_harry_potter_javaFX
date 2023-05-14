package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public class Accio extends AuthorizedSpell {

    public Accio(String name, boolean makeDamage){
        this.name = name;
        this.makeDamage = makeDamage; //savoir si c'est un sort qui fait des dégats.
        this.interactWithItem = true;
    }
    @Override
    public void actionOnCharacter(Character character) {

    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {
        if(isAttackSuccessful(this.successPercentage)){
            System.out.println("\u001B[36m" + "Vous attirez l'objet " + item.getName() + " et le mettez dans votre inventaire" + "\u001B[0m");
            inventory.getItemsList().add(item);
        }
        else{
            textFailedSpell();
        }

    }
}
