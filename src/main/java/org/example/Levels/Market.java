package org.example.Levels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.Characters.Character;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Items.consumable.Potion;
import org.example.Items.consumable.PotionType;

import static javafx.scene.text.Font.font;

public class Market {
    private Character merchant;
    private Wizard wizard;
    private UserInteraction userInteraction;

    private Scene scene;
    private GridPane gridPane;
    private StackPane stackPane;
    private Stage stage;

    private int width = 1300;

    private int height = 700;
    private boolean isFinish;

    private Level level;
    public Market(Wizard wizard, Stage stage,Level level){
        this.isFinish = false;
        this.userInteraction = new UserInteraction();
        this.wizard = wizard;
        this.stackPane = new StackPane();
        this.stage = stage;
        this.gridPane = new GridPane();
        gridPane.setPadding(new Insets(400, 25, 25, 500));
        this.scene = new Scene(stackPane,width,height);
        this.level = level;
        Image backgroundImage = new Image("images/wp3641435-diagon-alley-wallpapers.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(width);
        backgroundImageView.setFitHeight(height);
        stackPane.getChildren().addAll(backgroundImageView,gridPane);
    }

    public void checkPrice(Potion potion){
        if(this.wizard.getGold() >= potion.getPrice()){
            this.wizard.spendGold(potion.getPrice());
            this.wizard.addPotion(potion);
        }
        else{
            System.out.println("Vous n'avez pas assez d'argent!");
        }
    }
    public void buyPotion(){
        boolean isFinish = false;
        while(!isFinish){
            System.out.println("Vous avez " + this.wizard.getGold() + "🪙");
            int potionNumberChoice = userInteraction.Scanner_Int("Quelle potion voulez vous acheter? 1(Heal),2(Mana),3(Strenght),4(Luck), 5 (Shield), 6(partir)");
            int sizePotionList = this.wizard.getPotionsList().size();
            String sizePotionListToString = Integer.toString(sizePotionList);
            switch (potionNumberChoice){
                case 1:
                    Potion potionHeal = new Potion(PotionType.HEAL,20);
                    checkPrice(potionHeal);
                    break;
                case 2:
                    Potion potionMana = new Potion(PotionType.MANA,15);
                    checkPrice(potionMana);
                    break;
                case 3:
                    Potion potionStrenght = new Potion(PotionType.STRENGTH,25);
                    checkPrice(potionStrenght);
                    break;
                case 4:
                    Potion potionLuck = new Potion(PotionType.LUCK,15);
                    checkPrice(potionLuck);
                    break;
                case 5:
                    Potion potionShield = new Potion(PotionType.SHIELD,15);
                    checkPrice(potionShield);
                    break;
                case 6:
                    System.out.println("Au revoir");
                    isFinish = true;
                    break;
            }
        }

    }

    public void buyFood(){
        System.out.println("Vous avez " + this.wizard.getGold() + " 🪙");
    }
    public void play(){
        createMarketScene();
    }

    public void createMarketScene(){
        Button healBtn = new Button("Buy (20)");
        Button manaBtn = new Button("Buy (15)");
        Button strengthBtn = new Button("Buy (25)");
        Button luckBtn = new Button("Buy (15)");
        Button shieldBtn = new Button("Buy (15)");
        Button quitBtn = new Button("Quit");
        Label goldLabel = new Label(wizard.getGold()+ " 🪙");
        goldLabel.setFont(new Font(10));
        goldLabel.setTextFill(Color.WHITE);
        gridPane.add(goldLabel,0,0);
        gridPane.add(healBtn,1,2);
        gridPane.add(manaBtn,2,2);
        gridPane.add(strengthBtn,3,2);
        gridPane.add(luckBtn,4,2);
        gridPane.add(shieldBtn,5,2);
        gridPane.add(quitBtn,0,3);
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                level.play();
            }
        });
        healBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Potion potionHeal = new Potion(PotionType.HEAL,20);
                checkPrice(potionHeal);
                createMarketScene();
            }
        });
        manaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Potion potionMana = new Potion(PotionType.MANA,15);
                checkPrice(potionMana);
                createMarketScene();
            }
        });
        strengthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Potion potionStrenght = new Potion(PotionType.STRENGTH,25);
                checkPrice(potionStrenght);
                createMarketScene();
            }
        });
        luckBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Potion potionLuck = new Potion(PotionType.LUCK,15);
                checkPrice(potionLuck);
                createMarketScene();
            }
        });
        shieldBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Potion potionShield = new Potion(PotionType.SHIELD,15);
                checkPrice(potionShield);
                createMarketScene();
            }
        });
        stage.setScene(scene);

    }
}
