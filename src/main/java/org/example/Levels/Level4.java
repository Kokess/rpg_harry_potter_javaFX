package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;
import org.example.Items.objects.Portkey;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Core;
import org.example.Spells.Incendio;
import org.example.Spells.Petrificus;

import java.util.ArrayList;

public class Level4 extends Level{
    private String name;
    private String jsonPath = "src/main/java/org/example/Dialogues/scriptLevel4.json";

    private Level5 level5;
    public Level4(String name, Wizard wizard, Stage stage){
        this.name = name;
        this.wizard = wizard;
        this.userInteraction = new UserInteraction();
        this.json = new JsonRead();
        this.isFinish = false;
        Wand voldemortWand = new Wand(25,Core.ONE.name());
        Image voldemortSprite = new Image("images/enemySprite/voldemort.png");
        Image peterSprite = new Image("images/enemySprite/peter.png");
        Enemy voldemort = new Enemy("Voldemort",voldemortWand,1000,500,5,voldemortSprite);
        Wand peterPettigrewWand = new Wand(22,Core.TWO.name());
        Enemy peterPettigrew = new Enemy("Peter Pettigrew", peterPettigrewWand,1000,200,5,peterSprite);
        this.enemyList = new ArrayList<Enemy>();
        this.enemyList.add(voldemort);
        this.enemyList.add(peterPettigrew);
        this.round = 0;
        this.availableWorldItem = new ArrayList<Item>();
        Portkey portkey = new Portkey(this.enemyList);
        this.availableWorldItem.add(portkey);
        runSystemAttack = true;
        this.stage = stage;
        isBoss=false;
    }

    @Override
    public void play() {
       // while(!isFinish){
         //   introduction();
           // attackEnemySystem();
            //if(this.wizard.isAlive()){
              //  setEnd();
            //}
            //else{
              //  isFinish =true;
            //}

        //}
        level5 = new Level5("",wizard,stage);
        introduction();
        createEnemyScene(stage,level5);

    }

    @Override
    public void introduction() {
        this.json.read(this.jsonPath,"intro");
        Incendio incendio = new Incendio();
        this.wizard.addSpell(incendio);
        Petrificus petrificus = new Petrificus();
        this.wizard.addSpell(petrificus);
    }
    @Override
    public void attackEnemySystem() {
        this.json.read(this.jsonPath,"fightBegin");
        while(runSystemAttack){
            wizardStats(this.wizard);
            enemyStats(this.enemyList);
            this.userInteraction.actionChoiceEnemy(this.wizard.getInventory(),this.wizard.getSpellList(),this.wizard,this.enemyList,this.availableWorldItem,this.round);
            this.round +=1;
        }
        checkWizardLife(this.wizard);
        checkEnemyList(this.enemyList);
    }
    @Override
    public void attackBossSystem() {
    }
}
