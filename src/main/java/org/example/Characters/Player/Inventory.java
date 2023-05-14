package org.example.Characters.Player;

import org.example.Characters.Npc.AbstractEnemy;
import org.example.Items.Item;
import org.example.Items.consumable.Potion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

import static org.example.Items.consumable.PotionType.HEAL;

public class Inventory {

    private ArrayList<Potion> potionList;
    private ArrayList<Item> itemList;
    public Inventory(ArrayList<Potion> potionList, ArrayList<Item> itemsList){
        this.potionList = potionList;
        this.itemList = itemsList;
    }
    public ArrayList<Potion> getPotionList(){
        return this.potionList;
    }
    public ArrayList<Item> getItemsList(){
        return this.itemList;
    }
    public void addPotion(Potion potion){
        this.potionList.add(potion);
    }
    public void addItem(Item item){
        this.itemList.add(item);
    }
    public void displayItem(){
        for(int i=0;i<this.itemList.size();i++){
            System.out.println(i+". "+this.itemList.get(i).getName() + " | ");
        }
    }
    public void displayPotion(){
        Iterator iter = this.potionList.iterator();
        int healCounter =0;int manaCounter =0;int strengthCounter =0;int luckyCounter = 0;int shieldCounter=0;
        while(iter.hasNext()){
            Potion iterP = (Potion)iter.next();
            switch (iterP.getType()){
                case "HEAL":
                    healCounter +=1;
                    break;
                case "MANA":
                    manaCounter +=1;
                    break;
                case "STRENGTH":
                    strengthCounter+=1;
                    break;
                case "LUCK":
                    luckyCounter +=1;
                    break;
                case "SHIELD":
                    shieldCounter +=1;
                    break;
            }
        }
        System.out.println("Heal: " +healCounter + " | " + "Mana: " + manaCounter + " | " + "Strength: " + strengthCounter + " | " + "Luck: " + luckyCounter + " | " + "Shield: " + shieldCounter);

    }
    public void useItem(int positionItem){
        this.itemList.get(positionItem).action();
    }

}
