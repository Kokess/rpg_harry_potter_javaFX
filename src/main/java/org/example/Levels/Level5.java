package org.example.Levels;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;
import org.example.Items.objects.FireWork;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Core;
import org.example.Spells.Petrificus;

import java.util.ArrayList;

public class Level5 extends Level{
    private String name;
    private String jsonPath;
    private Wand doloresWand;
    private Enemy dolores;

    private FireWork fireWork;

    private Level6 level6;
    public Level5(String name, Wizard wizard, Stage stage){
        this.name = name;
        this.wizard = wizard;
        this.json = new JsonRead();
        this.jsonPath = "src/main/java/org/example/Dialogues/scriptLevel5.json";
        this.enemyList = new ArrayList<Enemy>();
        this.doloresWand = new Wand(22,Core.ONE.name());
        Image doloresSprite = new Image("images/enemySprite/dolores.png");
        this.dolores = new Enemy("Dolores",doloresWand,500,100,5,doloresSprite);
        this.enemyList.add(dolores);
        this.isFinish = false;
        this.availableWorldItem = new ArrayList<Item>();
        this.userInteraction = new UserInteraction();
        this.round =0;
        this.stage = stage;
        isBoss=false;

    }
    @Override
    public void play() {
       level6 = new Level6("",wizard,stage);
       introduction();
       createEnemyScene(stage,level6);
    }

    @Override
    public void introduction() {
        this.json.read(this.jsonPath,"intro");
        Petrificus petrificus = new Petrificus();
        this.wizard.addSpell(petrificus);
        fireWork = new FireWork(this.enemyList);
        availableWorldItem.add(fireWork);
    }

    @Override
    public void attackEnemySystem() {
        json.read(jsonPath,"fightBegin");
        while(runSystemAttack){
            wizardStats(this.wizard);
            enemyStats(this.enemyList);
            userInteraction.actionChoiceEnemy(wizard.getInventory(),wizard.getSpellList(),wizard,enemyList,availableWorldItem,round);
            round +=1;
            if(this.round == 8){
                availableWorldItem.add(fireWork);
                userInteraction.print("Les feux d'artifices sont là! Utilisez Accio pour les attirer et les utiliser!");
            }
            checkWizardLife(this.wizard);
            checkEnemyList(this.enemyList);

        }

    }

    @Override
    public void attackBossSystem() {

    }
}