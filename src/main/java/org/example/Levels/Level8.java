package org.example.Levels;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Characters.Player.Wizard;

public class Level8 extends Level{

    private Scene endScene;
    public Level8(String name, Wizard wizard, Stage stage){
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        stackPane.getChildren().add(gridPane);
        Label label = new Label("you finished your schooling at Hogwarts " + wizard.getName());
        gridPane.add(label,0,0);
        endScene = new Scene(stackPane,600,600);
        this.stage = stage;
    }

    @Override
    public void play() {
        stage.setScene(endScene);
    }

    @Override
    public void introduction() {

    }

    @Override
    public void attackEnemySystem() {

    }

    @Override
    public void attackBossSystem() {

    }
}
