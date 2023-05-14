package org.example.Core;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.Characters.Player.Wizard;
import org.example.Clans.House;
import org.example.Clans.SortingHat;
import org.example.Companion.Pet;
import org.example.Console.UserInteraction;
import org.example.Items.consumable.Potion;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Core;
import org.example.Levels.*;

import java.net.URL;
import java.util.ArrayList;;

public class Game {
    public Game(Stage stage){this.stage = stage;}
    private Stage stage;
    private Boolean creation = true;
    private String name;
    private String nameHouse;
    private String nameWandCore;
    private String petName;
    private int wandSize;
    private Wizard player;
    private House Gryffindor; private House Hufflepuff; private House Ravenclaw; private House Slytherin;private House player_house;
    private UserInteraction user_interaction;
    private House[] houses;
    private ArrayList<Potion> potions;
    private Pet phoenix;private Pet goat;private Pet cat;private Pet owl;private Pet toad;private Pet player_pet;
    private Wand wand;
    private String core;
    private int life_player;
    private Level1 level1;private Level2 level2;private Level3 level3;private Level4 level4;private Level5 level5;private Level6 level6;private Level7 level7;
    private Market market;
    private int width = 1300;
    private int height = 720;
    public void initialization(){
        user_interaction = new UserInteraction();
        Gryffindor = new House("Gryffindor");
        Hufflepuff = new House("Hufflepuff");
        Ravenclaw = new House("Ravenclaw");
        Slytherin = new House("Slytherin");
        //houses = {Gryffindor,Hufflepuff,Ravenclaw,Slytherin};
        life_player = 100;
        potions = new ArrayList<Potion>();
    }

    public void playerCreation(){

        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        Scene playerCreationScene = new Scene(stackPane,width,height);


        Label label = new Label("Player Creation");
        label.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        label.setTextFill(Color.WHITE);
        Label nomLabel = new Label("Name");
        nomLabel.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        nomLabel.setTextFill(Color.WHITE);
        TextField nomTextField = new TextField();
        Label maisonLabel = new Label("House");
        maisonLabel.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        maisonLabel.setTextFill(Color.WHITE);
        ComboBox<String> maisonComboBox = new ComboBox<>();
        maisonComboBox.getItems().addAll("Gryffindor", "Slytherin", "Hufflepuff", "Ravenclaw");
        Label petLabel = new Label("Pet");
        petLabel.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        petLabel.setTextFill(Color.WHITE);
        ComboBox<String> petComboBox = new ComboBox<>();
        petComboBox.getItems().addAll("phoenix", "goat", "cat","owl","toad");
        Label baguetteLabel = new Label("Wand");
        baguetteLabel.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        baguetteLabel.setTextFill(Color.WHITE);
        ComboBox<String> baguetteComboBox = new ComboBox<>();
        baguetteComboBox.getItems().addAll(Core.ONE.name(), Core.TWO.name(), Core.THREE.name());
        Label tailleLabel = new Label("Wand's Size");
        tailleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        tailleLabel.setTextFill(Color.WHITE);
        TextField tailleTextField = new TextField();
        Button validerButton = new Button("Validation");

        validerButton.setOnAction(event -> {
            name = nomTextField.getText();
            nameHouse = maisonComboBox.getValue();
            nameWandCore = baguetteComboBox.getValue();
            petName = petComboBox.getValue();
            try {
                wandSize = Integer.parseInt(tailleTextField.getText());
                if (wandSize < 22 || wandSize > 35) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("La taille de la baguette doit être comprise entre 22 et 35 cm.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La taille de la baguette doit être un nombre entier.");
                alert.showAndWait();
            }
            house_choice(nameHouse);
            pet_choice(petName);
            core = nameWandCore;
            wand = new Wand(wandSize,core);
            this.player = new Wizard(name,wand,potions,player_pet,life_player,player_house);
            setBuff(this.player);
            levelInitialization();
            runLevel();
        });
        Image backgroundImage = new Image("images/menuBackground.jpg");

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(gridPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(gridPane.heightProperty());
        stackPane.getChildren().addAll(backgroundImageView, gridPane);
        int colmunMenu =18;
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(label, 24, 0, 2, 1);
        gridPane.add(nomLabel, colmunMenu, 1);
        gridPane.add(nomTextField, colmunMenu, 2);
        gridPane.add(maisonLabel, colmunMenu, 3);
        gridPane.add(maisonComboBox, colmunMenu, 4);
        gridPane.add(baguetteLabel, colmunMenu, 5);
        gridPane.add(baguetteComboBox, colmunMenu, 6);
        gridPane.add(tailleLabel, colmunMenu, 7);
        gridPane.add(tailleTextField, colmunMenu ,8);
        gridPane.add(petLabel,colmunMenu,9);
        gridPane.add(petComboBox,colmunMenu,10);
        gridPane.add(validerButton, colmunMenu, 11);
        gridPane.setValignment(nomTextField, VPos.BOTTOM);
        stage.setScene(playerCreationScene);

    }

    public void levelInitialization(){
        level1 = new Level1("The Philosopher’s Stone", this.player,stage);
    }

    public void runLevel(){
        StackPane levelStackPane = new StackPane();
        GridPane levelGridPane = new GridPane();
        levelGridPane.setPadding(new Insets(25, 25, 25, 25));
        Scene levelScene = new Scene(levelStackPane,width,height);
        stage.setScene(levelScene);
        Label title = new Label("Welcome into the world of Harry Potter At Home!");
        title.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        title.setTextFill(Color.WHITE);
        Button start = new Button("start the adventure");
        Image backgroundImage = new Image("images/1309409.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(levelGridPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(levelGridPane.heightProperty());
        levelStackPane.getChildren().addAll(backgroundImageView,levelGridPane);
        levelGridPane.add(title,20,3);
        levelGridPane.add(start,20,5);

        start.setOnAction(actionEvent -> {
            level1.play();
        });

    }

    public void house_choice(String name){
        switch(name){
            case "Gryffindor":
                player_house = Gryffindor;
                break;

            case "Slytherin":
                player_house = Slytherin;
                break;

            case "Ravenclaw":
                player_house = Ravenclaw;
                break;

            case "Hufflepuff":
                player_house = Hufflepuff;
                break;
        }
    }

    public void pet_choice(String name){
        switch(name){
            case "phoenix":
                player_pet = phoenix;
                break;

            case "goat":
                player_pet = goat;
                break;

            case "cat":
                player_pet = cat;
                break;

            case "owl":
                player_pet = owl;
                break;

            case "toad":
                player_pet = toad;
                break;
        }
    }

    public void core_choice(String name){
        switch(name){
            case " a":
                core = Core.ONE.name();
                break;
            case " z":
                core = Core.TWO.name();
                break;
            case " e":
                core = Core.THREE.name();
                break;
        }
    }
    public void setBuff(Wizard wizard){
        switch (wizard.getHouse().getName()){
            case "Griffindor":
                wizard.setDefense(10);
                break;
            case "Slytherin":
                //TODO faire buff attaque
                break;
            case "Ravenclaw":
                wizard.setSuccessAttackRate(85);
                break;
            case "Hufflepuff":
                //TODO faire buff potion
                break;

        }
    }
    public void run(){
        initialization();
        playerCreation();
    }
}
