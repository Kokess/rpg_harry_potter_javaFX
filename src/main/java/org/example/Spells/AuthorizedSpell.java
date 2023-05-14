package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Items.Item;

public abstract class AuthorizedSpell extends AbstractSpell{
    protected Boolean interactWithItem;
    protected int successPercentage = 80;
    public int getSuccessPercentage(){return this.successPercentage;}

    public boolean canInteractWithItem(){
        return this.interactWithItem;
    }
    public void textFailedSpell(){
        this.userInteraction.print("\u001B[33m" + "❌ Votre sort a échoué" + "\u001B[0m");
    }

    public void addSuccessPercentage(int value){
        this.successPercentage +=5;
    }
    public void setSuccessPercentage(int value){
        this.successPercentage = value;
    }
}