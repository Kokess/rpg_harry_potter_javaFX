package org.example.Characters.Player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.example.Characters.Character;
import org.example.Clans.House;
import org.example.Items.Item;
import org.example.Items.consumable.Potion;
import org.example.Items.weapon.Wand;
import org.example.Companion.Pet;
import org.example.Spells.AuthorizedSpell;

import javax.net.ssl.SSLProtocolException;
import java.util.*;



public class Wizard extends Character {
    private Wand wand;
    private ArrayList<Potion> potions;
    private Pet pet;
    private House house;
    private int mana;
    private ArrayList<Item> itemList;
    private Inventory inventory;
    private boolean isDeathEater;

    private Image spriteSheet;
    private Image spriteImage;
    private ImageView spriteView;
    private  int spriteX = 0;
    private int spriteY = 9 * 64;

    public Wizard() {
    }

    private HashMap<String, AuthorizedSpell> spellList;
    private int gold;

    public Wizard(String name, Wand wand, ArrayList<Potion> potions, Pet pet, int life, House house) {
        this.pet = pet;
        this.potions = potions;
        this.wand = wand;
        this.name = name;
        this.life = life;
        this.house = house;
        this.mana = 100;
        this.spellList = new HashMap<String, AuthorizedSpell>();
        this.gold = 0;
        this.defense = 3;
        this.itemList = new ArrayList<Item>();
        this.inventory = new Inventory(this.potions, this.itemList);//pourcentage de défense
        this.spriteSheet =new Image("C:\\Users\\33625\\Desktop\\rpg-main\\src\\images\\playerSheet.png");
        int spriteWidth = 7 * 64;
        int spriteHeight = 196;
        spriteImage = new WritableImage(spriteSheet.getPixelReader(), spriteX, spriteY, spriteWidth, spriteHeight);
        spriteView = new ImageView(spriteImage);
    }

    //get methods
    public Wand getWand() {
        return this.wand;
    }

    public House getHouse() {
        return this.house;
    }

    public int getGold() {
        return this.gold;
    }

    public int getMana() {
        return this.mana;
    }

    public HashMap<String, AuthorizedSpell> getSpellList() {
        return this.spellList;
    }

    public List<Potion> getPotionsList() {
        return this.potions;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void defend() {
    }

    @Override
    public void take_dmg(int damage, int defense) {
        this.life -= (damage - ((defense * damage)) / 100);
    }

    public void takePotion(Potion potion) {
        switch (potion.getType()) {
            case "HEAL":
                this.life += potion.getHealEffect();
                break;
            case "MANA":
                this.mana += potion.getManaEffect();
                break;
            case "STRENGTH":
                for (int i = 0; i < spellList.size(); i++) {
                    String name = spellList.keySet().toArray()[i].toString();
                    if (spellList.get(name).getMakeDamage()) {
                        spellList.get(name).addDamage(5);
                    }
                }
                break;
            case "LUCK":
                for (int i = 0; i < spellList.size(); i++) {
                    String name = spellList.keySet().toArray()[i].toString();
                    spellList.get(name).addSuccessPercentage(5);
                }
                break;
            case "SHIELD":
                this.defense += potion.getShieldEffect();
                break;
        }
    }

    public void deletePotion(int position) {
        this.potions.remove(position);
    }

    @Override
    public void attack(Character character, int damage, int defense, int successAttackRate) {
        Random r = new Random();
        if (this.canPlay) {
            int randomNumber = r.nextInt(100);
            if (randomNumber >= (100 - successAttackRate)) {  //l'attaque échoue à (100 - succcessAttackRate)%
                character.take_dmg(damage, defense);
                System.out.println("\u001B[32m" + "⚔️ Vous avez infligé " + damage + " points de dégâts à l'ennemie " + character.getName() + " ⚔️" + "\u001B[0m");
            } else {
                System.out.println("\u001B[33m" + "❌ Votre attaque a échoué!" + "\u001B[0m");
            }
        } else {
            System.out.println("Vous ne pouvez pas attaquer!");
        }


    }

    public void useSpell(AuthorizedSpell authorizedSpell) {

    }

    public void addSpell(AuthorizedSpell authorizedSpell) {
        this.spellList.put(authorizedSpell.getName(), authorizedSpell);
    }

    public void addPotion(Potion potion) {
        this.potions.add(potion);
    }

    public void addGold(int value) {
        this.gold += value;
    }

    public void spendGold(int value) {
        this.gold -= value;
    }

    public void usePotion(int number, ArrayList<Potion> potionList) {
        String typeName = "";
        String errorMessage = "\u001B[31m" + "Vous n'avez pas ce type de potion " + "\u001B[0m";
        int potionPosition = 0;
        switch (number) {
            case 1:
                typeName = "HEAL";
                if (isPotionTypeExist(typeName)) {
                    potionPosition = selectPotion(typeName);
                    takePotion(this.potions.get(potionPosition));
                    System.out.println("Vous regagnez " + this.potions.get(potionPosition).getHealEffect() + " Hp");
                    deletePotion(potionPosition);
                } else {
                    System.out.println(errorMessage);
                }
                break;
            case 2:
                typeName = "MANA";
                if (isPotionTypeExist(typeName)) {
                    potionPosition = selectPotion(typeName);
                    takePotion(this.potions.get(potionPosition));
                    System.out.println("Vous regagnez " + this.potions.get(potionPosition).getManaEffect() + " Mana");
                    deletePotion(potionPosition);
                } else {
                    System.out.println(errorMessage);
                }
                break;
            case 3:
                typeName = "STRENGTH";
                if (isPotionTypeExist(typeName)) {
                    potionPosition = selectPotion(typeName);
                    takePotion(this.potions.get(potionPosition));
                    System.out.println("Vous gagnez " + this.potions.get(potionPosition).getStrengthEffect() + " force");
                    deletePotion(potionPosition);
                } else {
                    System.out.println(errorMessage);
                }
                break;
            case 4:
                typeName = "LUCK";
                if (isPotionTypeExist(typeName)) {
                    potionPosition = selectPotion(typeName);
                    takePotion(this.potions.get(potionPosition));
                    System.out.println("Vous gagnez " + this.potions.get(potionPosition).getStrengthEffect() + " de précision");
                    deletePotion(potionPosition);
                } else {
                    System.out.println(errorMessage);
                }
                break;
            case 5:
                typeName = "SHIELD";
                if (isPotionTypeExist(typeName)) {
                    potionPosition = selectPotion(typeName);
                    takePotion(this.potions.get(potionPosition));
                    System.out.println("Vous gagnez " + this.potions.get(potionPosition).getShieldEffect() + " de protection");
                    deletePotion(potionPosition);
                } else {
                    System.out.println(errorMessage);
                }
                break;
        }

    }

    public int selectPotion(String typeName) {
        int potionPosition = 0;
        for (int i = 0; i < potions.size(); i++) {
            if (potions.get(i).getType() == typeName) {
                potionPosition = i;
            }
        }
        return potionPosition;
    }

    public Boolean isPotionTypeExist(String typeName) {
        Iterator iter = this.potions.iterator();
        while (iter.hasNext()) {
            Potion potion = (Potion) iter.next();
            if (potion.getType() == typeName) {
                return true;
            }
        }
        return false;
    }

    public boolean getIsDeathEater() {
        return this.isDeathEater;
    }

    public void setIsDeathEater(boolean value) {
        this.isDeathEater = value;
    }

    public void resetSpellsStats() {
        if (getHouse().getName() != "Slytherin") {
            Iterator it = spellList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                switch (m.getKey().toString()){
                    case "Basic attack":
                        //m.getValue().
                }
            }
        }
        if (getHouse().getName() != "Huflepuff") {
            for (int i = 0; i < spellList.size(); i++) {
                String name = spellList.keySet().toArray()[i].toString();
                spellList.get(name).setSuccessPercentage(80);
            }
        } else {
            for (int i = 0; i < spellList.size(); i++) {
                String name = spellList.keySet().toArray()[i].toString();
                spellList.get(name).setSuccessPercentage(90);
            }
        }
    }

    public Image getSpriteSheet(){
        return spriteSheet;
    }
    public Image getSpriteImage(){
        return spriteImage;
    }

    public ImageView getSpriteView(){
        return spriteView;
    }
    public void setSpriteImage(int spriteX,int spriteY,int spriteWidth,int spriteHeight){
        spriteImage = new WritableImage(spriteSheet.getPixelReader(),spriteX,spriteY,spriteWidth,spriteHeight);
    }
}
