package org.example.Items.objects;

import org.example.Characters.Npc.Enemy;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;
import org.example.Levels.Level;
import org.example.Levels.Level4;

import java.util.ArrayList;

public class Portkey extends Item {

    private JsonRead json;
    private String jsonPath = "src/main/java/org/example/Dialogues/scriptLevel4.json";
    private ArrayList<Enemy> enemyLevelList;
    public Portkey(ArrayList<Enemy> enemyLevelList){
        this.name = "Portoloin";
        this.json = new JsonRead();
        this.enemyLevelList = enemyLevelList;

    }

    @Override
    public void action() {
        this.json.read(this.jsonPath,"endFight");
        this.enemyLevelList.clear();
    }
}
