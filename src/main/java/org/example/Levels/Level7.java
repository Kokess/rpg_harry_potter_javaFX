package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;
import org.example.Items.weapon.Core;
import org.example.Items.weapon.Wand;
import org.example.Spells.AvadaKedavra;

import java.util.ArrayList;

public class Level7 extends Level{
    private String name;
    private String jsonPath = "src/main/java/org/example/Dialogues/scriptLevel7.json";

    private Level7 level7;

    public Level7(String name, Wizard wizard, Stage stage){
        this.name = name;
        this.wizard = wizard;
        this.json = new JsonRead();
        this.enemyList = new ArrayList<Enemy>();
        Wand voldemortWand = new Wand(25, Core.ONE.name());
        Wand beatrixWand = new Wand(25,Core.TWO.name());
        Image voldemortSprite = new Image("images/enemySprite/voldemort.png");
        Image beatrixSpriteSheet = new Image("images/enemySprite/beatrix.png");
        Enemy voldemort = new Enemy("Voldemort",voldemortWand,150,500,5,voldemortSprite);
        Enemy beatrix = new Enemy("Beatrix Lestrange",beatrixWand,150,500,5,beatrixSpriteSheet);
        this.enemyList.add(beatrix);
        this.enemyList.add(voldemort);
        this.availableWorldItem = new ArrayList<Item>();
        isFinish = false;
        runSystemAttack = true;
        AvadaKedavra avadaKedavra = new AvadaKedavra();
        voldemort.addSpell(avadaKedavra);
        this.stage =stage;
        isBoss = false;

    }

    @Override
    public void play() {
        introduction();
        createEnemyScene(stage,level7);
    }

    @Override
    public void introduction() {
        this.json.read(jsonPath,"intro");

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
