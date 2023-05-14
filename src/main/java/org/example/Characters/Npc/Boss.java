package org.example.Characters.Npc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.example.Characters.Character;
import org.example.Console.TextColor;
import org.example.Items.weapon.Weapon;

import java.util.Random;

public class Boss extends AbstractEnemy{

    public Boss (String name, Weapon weapon,int life,int goldValue,int defense){
        this.name = name;
        this.successAttackRate = 85;
        this.weapon = weapon;
        this.life = life;
        this.red = new TextColor();
        this.red.setRed();
        this.white = new TextColor();
        this.white.setWhite();
        this.green = new TextColor();
        this.green.setGreen();
        this.goldValue = goldValue;
        this.defense = defense;
    }

    public Boss (String name, Weapon weapon, int life, int goldValue, int defense, Image spriteSheet){
        this.name = name;
        this.successAttackRate = 85;
        this.weapon = weapon;
        this.life = life;
        this.red = new TextColor();
        this.red.setRed();
        this.white = new TextColor();
        this.white.setWhite();
        this.green = new TextColor();
        this.green.setGreen();
        this.goldValue = goldValue;
        this.defense = defense;
        this.spritesheet = spriteSheet;
        this.spriteImage = new WritableImage(spriteSheet.getPixelReader(), spriteX, spriteY, spriteWidth, spriteHeight);
        this.spriteView = new ImageView(spriteImage);
    }
    public int getSuccessAttackRate(){
        return this.successAttackRate;
    }
    @Override
    public void take_dmg(int damage,int defense){
        this.life -=(damage - ((defense * damage))/100);
    }
    @Override
    public void attack(Character character,int damage,int defense,int successAttackRate) {
        Random r = new Random();
        if (r.nextInt(100) >= 100 - successAttackRate) {  //l'attaque échoue à (100 - succcessAttackRate)%
            //System.out.println(r.nextInt());
            character.take_dmg(damage,defense);
            System.out.println(this.red.getColorCode() + "L'ennemie " + this.name + " vous a infligé " + damage + " points de dégâts" + this.white.getColorCode());
        } else {
            System.out.println("\u001B[33m" + "L'attaque de l'ennemie a échoué!" + "\u001B[0m");
        }
    }

    public Boolean itemDrop(int dropRate){
        Random r = new Random();
        if(r.nextInt(100) >= 100-dropRate){
            return true;
        }
        else{
            return false;
        }
    }

}
