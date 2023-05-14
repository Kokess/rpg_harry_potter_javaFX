package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Items.Item;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Core;

import java.util.ArrayList;

public class Level6 extends Level{
    private String name;
    private String jsonPath = "src/main/java/org/example/Dialogues/scriptLevel6.json";

    private Level8 level8;

    public Level6(String name, Wizard wizard, Stage stage){
        this.name=name;
        this.wizard = wizard;
        this.enemyList = new ArrayList<Enemy>();
        Wand enemyWand = new Wand(25,Core.TWO.name());
        Image deathEaterSpriteSheet = new Image("images/enemySprite/deatheater.png");
        Enemy deathEater1 = new Enemy("Death eater",enemyWand,80,40,5,deathEaterSpriteSheet);
        Enemy deathEater2 = new Enemy("Death eater",enemyWand,80,40,5,deathEaterSpriteSheet);
        Enemy deathEater3 = new Enemy("Death eater",enemyWand,80,40,5,deathEaterSpriteSheet);
        Enemy deathEater4 = new Enemy("Death eater",enemyWand,80,40,5,deathEaterSpriteSheet);
        enemyList.add(deathEater1);enemyList.add(deathEater2);//enemyList.add(deathEater3);enemyList.add(deathEater4);
        isFinish = false;
        round = 0;
        this.availableWorldItem = new ArrayList<Item>();
        runSystemAttack = true;
        this.stage = stage;
        isBoss = false;

    }

    @Override
    public void play() {
        level8 = new Level8("End Game",wizard,stage);
        introduction();
        createEnemyScene(stage,level8);
    }

    @Override
    public void introduction() {
        //json.read(jsonPath,"intro");
        //json.read(jsonPath,"spell");

    }

    @Override
    public void attackEnemySystem() {
        this.json.read(this.jsonPath,"fightBegin");
        while(runSystemAttack){
            wizardStats(this.wizard);
            enemyStats(this.enemyList);
            this.userInteraction.actionChoiceEnemy(this.wizard.getInventory(),this.wizard.getSpellList(),this.wizard,this.enemyList,this.availableWorldItem,this.round);
            this.round +=1;
            checkWizardLife(this.wizard);
            checkEnemyList(this.enemyList);
        }
    }

    @Override
    public void attackBossSystem() {

    }
}