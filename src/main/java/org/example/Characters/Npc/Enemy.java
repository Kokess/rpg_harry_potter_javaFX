package org.example.Characters.Npc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.example.Characters.Character;
import org.example.Characters.Player.Wizard;
import org.example.Console.TextColor;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Weapon;
import org.example.Spells.*;

import java.util.HashMap;
import java.util.Random;

public class Enemy extends AbstractEnemy {
    public Enemy(String name, Weapon weapon,int life,int goldValue,int defense){
        this.name = name;
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
        this.isWizard = false;
    }
    public Enemy(String name, Wand wand, int life, int goldValue, int defense){
        this.name = name;
        this.wand = wand;
        this.life = life;
        this.goldValue = goldValue;
        this.defense = defense;
        this.spellList = spellList;
        this.isWizard = true;
        this.spellList = new HashMap<String, AbstractSpell>();
        BasicAttack basicAttack = new BasicAttack("Attaque basique",true);
        Petrificus petrificus = new Petrificus();
        Sectumsempra sectumsempra = new Sectumsempra();
        Incendio incendio = new Incendio();
        this.spellList.put("Basic attack",basicAttack);
        this.spellList.put("Petrificus",petrificus);
        this.spellList.put("Sectumsempra",sectumsempra);
        this.spellList.put("Incendio",incendio);
        this.canPlay = true;
    }
    public Enemy(String name, Wand wand, int life, int goldValue, int defense,Image spriteSheet){
        this.name = name;
        this.wand = wand;
        this.life = life;
        this.goldValue = goldValue;
        this.defense = defense;
        this.spellList = spellList;
        this.isWizard = true;
        this.spellList = new HashMap<String, AbstractSpell>();
        BasicAttack basicAttack = new BasicAttack("Attaque basique",true);
        Petrificus petrificus = new Petrificus();
        Sectumsempra sectumsempra = new Sectumsempra();
        Incendio incendio = new Incendio();
        this.spellList.put("Basic attack",basicAttack);
        this.spellList.put("Petrificus",petrificus);
        this.spellList.put("Sectumsempra",sectumsempra);
        this.spellList.put("Incendio",incendio);
        this.canPlay = true;
        this.spritesheet = spriteSheet;
        this.spriteImage = new WritableImage(spriteSheet.getPixelReader(), spriteX, spriteY, spriteWidth, spriteHeight);
        this.spriteView = new ImageView(spriteImage);
    }

    public Enemy(String name, Weapon weapon, int life, int goldValue, int defense, Image spriteSheet){
        this.name = name;
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
        this.isWizard = false;
        this.spritesheet = spriteSheet;
        this.spriteImage = new WritableImage(spriteSheet.getPixelReader(), spriteX, spriteY, spriteWidth, spriteHeight);
        this.spriteView = new ImageView(spriteImage);
    }
    @Override
    public void take_dmg(int damage,int defense){
        this.life -=(damage - ((defense * damage))/100);
    }
    @Override
    public void attack(Character character,int damage,int defense,int successAttackRate){
        Random r = new Random();
        if(this.canPlay){
            if(r.nextInt(100 ) >= 100 - successAttackRate){  //l'attaque échoue à (100 - succcessAttackRate)%
                System.out.println(r.nextInt());
                character.take_dmg(damage,defense);
                System.out.println(this.red.getColorCode() + "⚔️ L'ennemie " + this.name+ " vous a infligé " +  damage + " points de dégâts ⚔️" + this.white.getColorCode());
            }
            else{
                System.out.println("\u001B[33m" + "❌ L'attaque de l'ennemie a échoué!" + "\u001B[0m");
            }
        }
        else{
            System.out.println("L'ennemie "+this.name + " ne peut pas attaquer!");
        }

    }

    public AbstractSpell randomEnemyWizardSpell(){
        Random rSpell = new Random();
        int numberSpell = rSpell.nextInt(this.spellList.size());
        AbstractSpell currentSpell = this.spellList.get(spellList.keySet().toArray()[numberSpell].toString());
        return currentSpell;
    }
    public void enemyWizardAttack(Wizard wizard,AbstractSpell spell){
        Random r = new Random();
        Random rSpell = new Random();
        AbstractSpell currentSpell = spell;
        if(this.canPlay){
            if(r.nextInt(100) >=100 - currentSpell.getSuccessRate()){
                if(currentSpell.getName() == "Attaque basique"){
                    wizard.take_dmg(20,wizard.getDefense());
                    System.out.println(this.name + " Vous a infligé 20 points de dégâts");
                }
                else{
                    currentSpell.actionOnCharacter(wizard);
                }
            }
            else{
                System.out.println("Le sort de " + this.name + " a echoué!");
            }
        }
        else{
            System.out.println("Le sorcier ennemie " + this.name +" ne peut pas attaquer!");
        }
    }

    public HashMap<String, AbstractSpell> getSpellList(){
        return this.spellList;
    }

    public void addSpell(AbstractSpell spell){
        this.spellList.put(spell.getName(),spell);
    }
    public boolean getIsWizard(){
        return this.isWizard;
    }
}
