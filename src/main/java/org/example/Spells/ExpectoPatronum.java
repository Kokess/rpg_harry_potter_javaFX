package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Console.UserInteraction;
import org.example.Items.Item;

public class ExpectoPatronum extends AuthorizedSpell{

    public ExpectoPatronum(){
        this.name = "Expecto Patronum";
        this.makeDamage = false;
        this.interactWithItem = false;
        this.userInteraction = new UserInteraction();
        this.successPercentage = 60;
    }


    @Override
    public void actionOnCharacter(Character character) {
        if(character.getName() == "Dementor"){
            this.userInteraction.print("Vous faites fuir le détraqueur grâce à votre patronus!");
            character.take_dmg(character.getLife(),0);
        }
        else{
            this.userInteraction.print("La cible n'est pas un Détraqueur,ce sort n'a aucun effet!");
        }
    }

    @Override
    public void actionOnItem(Item item, Inventory inventory) {

    }
}
