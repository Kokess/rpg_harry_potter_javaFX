package org.example.Items.objects;

import org.example.Characters.Npc.Enemy;
import org.example.Dialogues.JsonRead;
import org.example.Items.Item;

import java.util.ArrayList;

public class FireWork extends Item {

    private ArrayList<Enemy> enemyLevelList;
    private JsonRead json;
    private String jsonPath = "src/main/java/org/example/Dialogues/scriptLevel5.json";

    public FireWork(ArrayList<Enemy> enemyLevelList){
        this.name = "Feux d'artifices";
        this.enemyLevelList= enemyLevelList;
    }
    @Override
    public void action() {
        //json.read(jsonPath,"useFireWork");
        this.enemyLevelList.clear();
    }
}
