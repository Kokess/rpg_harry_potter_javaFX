package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;
import org.example.Items.weapon.Weapon;
import org.example.Spells.Accio;
import org.example.Spells.ExpectoPatronum;

import java.util.ArrayList;

public class Level3 extends Level{

    private String name;
    private String jsonPath;
    private ExpectoPatronum expectoPatronum;
    private Level4 level4;

    public Level3(String name, Wizard wizard, Stage stage){
        this.name = name;
        this.wizard = wizard;
        this.userInteraction = new UserInteraction();
        this.json = new JsonRead();
        this.jsonPath = "src/main/java/org/example/Dialogues/scriptLevel3.json";
        this.enemyList = new ArrayList<Enemy>();
        this.availableWorldItem = new ArrayList<Item>();
        Weapon dementorWeapon = new Weapon("Dementor's kiss",10,5);
        Image dementorSheet = new Image("images/enemySprite/dementorSheet.png");
        Enemy dementor_1 = new Enemy("Dementor",dementorWeapon,75,20,3,dementorSheet);
        Enemy dementor_2 = new Enemy("Dementor",dementorWeapon,75,20,3,dementorSheet);
        Enemy dementor_3 = new Enemy("Dementor",dementorWeapon,75,20,3,dementorSheet);
        this.enemyList.add(dementor_1);this.enemyList.add(dementor_2);this.enemyList.add(dementor_3);
        this.isFinish = false;
        this.round =0;
        runSystemAttack = true;
        this.stage = stage;
        isBoss=false;

    }

    @Override
    public void play() {
        //while(!isFinish){
            //introduction();
            //attackEnemySystem();
            //setEnd();
        //}
        level4= new Level4("The Goblet of Fire", wizard,stage);
        introduction();
        createEnemyScene(stage,level4);
    }

    @Override
    public void introduction() {
        this.json.read(this.jsonPath,"intro");
        Accio accio = new Accio("Accio",false);
        this.wizard.addSpell(accio);
        this.expectoPatronum = new ExpectoPatronum();
        this.wizard.addSpell(expectoPatronum);
    }

    @Override
    public void attackEnemySystem() {
        this.json.read(this.jsonPath,"fightBegin");
        this.json.read(this.jsonPath,"enemyDescription");
        while(runSystemAttack){
            wizardStats(this.wizard);
            enemyStats(this.enemyList);
            this.userInteraction.actionChoiceEnemy(this.wizard.getInventory(),this.wizard.getSpellList(),this.wizard,this.enemyList,this.availableWorldItem,this.round);
            this.round +=1;
            checkWizardLife(this.wizard);
            checkEnemyList(this.enemyList);
        }
        this.json.read(this.jsonPath,"endFight");

        //createenemyscene
        //butonevent..
        //enlever les boucles whiles
        //faire des if
    }

    @Override
    public void attackBossSystem() {
        //No boss here
    }
}

