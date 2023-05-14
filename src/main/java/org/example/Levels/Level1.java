package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Boss;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Dialogues.JsonRead;
import org.example.Items.weapon.Weapon;
import org.example.Spells.*;


import java.util.ArrayList;

public class Level1 extends Level{
    //private final Spell spell;
    // private final Spell basicAttack;
    private BasicAttack attackBasic;
    private WingardiumLeviosa wingardiumLeviosa;
    private final Weapon bossWeapon;private final Weapon ennemyWeapon;

    private Level3 level3;

    private final String path;
    public Level1(String name, Wizard wizard, Stage stage){
        this.enemyList = new ArrayList<Enemy>();
        this.name = name;
        this.wizard = wizard;
        this.isFinish = false;
        this.userInteraction = new UserInteraction();
        this.bossWeapon = new Weapon("Grosse massue",10,15);
        this.ennemyWeapon = new Weapon("Petite massue",5,5);
        Image bossSPrite = new Image("images/enemySprite/TrollSheet.png");
        this.boss = new Boss("Troll",this.bossWeapon,150,30,5,bossSPrite);
        Image riverTrollSheet = new Image("images/enemySprite/miniTrollSheet.png");
        Enemy riverTroll_1 = new Enemy("River Troll",this.ennemyWeapon,50,10,3,riverTrollSheet);
        Enemy riverTroll_2 = new Enemy("River Troll", this.ennemyWeapon,50,10,3,riverTrollSheet);
        this.enemyList.add(riverTroll_1);this.enemyList.add(riverTroll_2);
        this.json = new JsonRead();
        this.path = "src/main/java/org/example/Dialogues/script_level1.json";
        this.attackBasic = new BasicAttack("Basic Attack",true);
        this.attackBasic.setDamage(20);
        this.wingardiumLeviosa = new WingardiumLeviosa("Wingardium Leviosa",true);
        this.wizard.addSpell(wingardiumLeviosa);
        this.wizard.addSpell(attackBasic);
        this.stage = stage;
        isBoss = true;


    }
    @Override
    public void introduction(){
        this.json.read(path,"intro");
    }
    @Override
    public void attackEnemySystem(){
        String currentSpellName;
        AuthorizedSpell currentAuthorizedSpell;
        wizardStats(this.wizard);
        currentSpellName = userInteraction.ScannerSpellChoice(this.wizard.getSpellList());
        currentAuthorizedSpell = this.wizard.getSpellList().get(currentSpellName);
        if(currentAuthorizedSpell.getMakeDamage()){
            int player_attack_choice = userInteraction.ScannerAttackChoice(this.enemyList);
            this.wizard.attack(this.enemyList.get(player_attack_choice - 1), currentAuthorizedSpell.getDamage(),this.enemyList.get(player_attack_choice - 1).getDefense(), currentAuthorizedSpell.getSuccessPercentage());
            if(!this.enemyList.get(player_attack_choice - 1).isAlive()){
                this.userInteraction.print(" ☠️ L'ennemie " + this.enemyList.get(player_attack_choice - 1).getName() + " est mort! ☠️");
                this.wizard.addGold(this.enemyList.get(player_attack_choice - 1).getGoldValue());
                this.enemyList.remove(player_attack_choice - 1);
            }
            else {
                this.enemyList.get(0).attack(this.wizard,this.enemyList.get(0).getWeapon().getDamage(),this.wizard.getDefense(),80);
            }
        }
        else{
            int player_attack_choice = userInteraction.ScannerAttackChoice(this.enemyList);
            executeDamageSpellAction(currentAuthorizedSpell,this.enemyList.get(player_attack_choice - 1));
            if(!this.enemyList.get(player_attack_choice - 1).isAlive()){
                this.userInteraction.print("☠️ L'ennemie " + this.enemyList.get(player_attack_choice - 1).getName() + " est mort! ☠️");
                this.wizard.addGold(this.enemyList.get(player_attack_choice - 1).getGoldValue());
                this.enemyList.remove(player_attack_choice - 1);
            }
            else{
                this.enemyList.get(0).attack(this.wizard,this.enemyList.get(0).getWeapon().getDamage(),this.wizard.getDefense(),80);
            }
        }
    }
    @Override
    public void attackBossSystem(){
        String currentSpellName;
        AuthorizedSpell currentAuthorizedSpell;
        wizardStats(this.wizard);
        bossStats(this.boss);
        currentSpellName = userInteraction.ScannerSpellChoice(this.wizard.getSpellList());
        currentAuthorizedSpell = this.wizard.getSpellList().get(currentSpellName);
        if(currentAuthorizedSpell.getMakeDamage()){
            this.wizard.attack(this.boss, currentAuthorizedSpell.getDamage(),this.boss.getDefense(), currentAuthorizedSpell.getSuccessPercentage());
            if(this.boss.isAlive()){
                this.boss.attack(this.wizard,this.bossWeapon.getDamage(),this.wizard.getDefense(),this.boss.getSuccessAttackRate());
            }
        }
        else{
            currentAuthorizedSpell.actionOnCharacter(this.boss);
            if(this.boss.isAlive()){
                this.boss.attack(this.wizard,this.bossWeapon.getDamage(),this.wizard.getDefense(),this.boss.getSuccessAttackRate());
            }
        }
    }
    @Override
    public void play(){
        this.level3 = new Level3("The Prisonner of Azkaban",wizard,stage);
        createEnemyScene(stage,level3);
    }
}
