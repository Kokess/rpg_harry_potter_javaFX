package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Player.Inventory;
import org.example.Console.UserInteraction;
import org.example.Items.Item;

import javax.swing.text.StyledEditorKit;
import java.util.Random;

public abstract class AbstractSpell {
    protected String name;
    protected Boolean makeDamage;
    protected int damage;
    protected UserInteraction userInteraction = new UserInteraction();
    protected int successRate = 80;
    public boolean getMakeDamage(){return this.makeDamage;}
    public void setDamage(int damage){this.damage = damage;}

    public void addDamage(int damage){
        this.damage +=5;
    }
    public int getDamage(){return this.damage;}

    public String getName(){return this.name;}
    public abstract void actionOnCharacter(Character character);
    public abstract void actionOnItem(Item item, Inventory inventory);

    public Boolean isAttackSuccessful(int successAttackRate){
        Random r = new Random();
        int randomNumber = r.nextInt(100);
        if(randomNumber >= (100 - successAttackRate)){
            return true;
        }
        else{
            return false;
        }
    }

    public int getSuccessRate(){
        return this.successRate;
    }


}
