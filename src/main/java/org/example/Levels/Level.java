package org.example.Levels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.Characters.Npc.Boss;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Inventory;
import org.example.Characters.Player.Wizard;
import org.example.Console.UserInteraction;
import org.example.Dialogues.JsonRead;
import org.example.Fx.Animation;
import org.example.Items.Item;
import org.example.Spells.AbstractSpell;
import org.example.Spells.AuthorizedSpell;
import org.example.Characters.Character;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Level {
    protected String name;
    protected ArrayList<Enemy> enemyList;
    protected ArrayList<Item> availableWorldItem;
    protected Boss boss;
    protected Boolean isFinish;
    protected Wizard wizard;
    protected UserInteraction userInteraction;
    protected int round;

    protected boolean runSystemAttack;

    protected JsonRead json;
    protected Scene bossScene;
    protected Scene enemyScene;
    protected GridPane enemyGridpane;
    protected GridPane bossGridPane;
    protected Stage stage;
    protected Button spellButton;
    protected Button potionButton;
    protected Button objectButton;
    protected boolean incendioCast = false; protected boolean isIncendioCurrentEffect = false;
    protected boolean enemyIncendioCast = false; protected boolean enemyIsIncendioCurrentEffect = false;
    protected boolean petrificusCast = false;protected boolean isPetrificusCurrentEffect = false;
    protected boolean enemyPetrificusCast = false;protected boolean enemyIsPetrificusCurrentEffect = false;
    protected int incendioRound =0; protected int petrificusRound = 0;
    protected int enemyIncendioRound = 0; protected int enemyPetrificusRound = 0;
    protected Character incendioTarget; protected Character petrificusTarget;
    protected Character enemyIncendioTarget;protected Character enemyPetrificusTarget;
    protected Animation animation= new Animation();
    protected Boolean isBoss;
    protected Scene deathScene;

    Level(){}

    //get methods
    public boolean isOver(){return this.isFinish;}
    public void setEnd(){
        System.out.println("Félicitation! Vous avez réussi le niveau!");
        this.isFinish = true;}
    public abstract void play();
    public abstract void introduction();
    public abstract void attackEnemySystem();
    public abstract void attackBossSystem();
    public void bossStats(Boss boss){
        System.out.println("\u001B[31m" + "Le boss a " + boss.getLife() + "❤️" + "\u001B[0m");
    }
    public void wizardStats(Wizard wizard){
        System.out.println("\u001B[32m" + "Vos statistiques " +"❤️" + wizard.getLife() +" | " + "🛡️" + wizard.getDefense() + " | " + "💧" + wizard.getMana()+ " | " + "🪙" + wizard.getGold() + "\u001B[0m");
    }

    public void executeDamageSpellAction(AuthorizedSpell authorizedSpell, Character character){
        switch (authorizedSpell.getName()){
            case "Wingardium Leviosa":
                authorizedSpell.actionOnCharacter(character);
        }
    }

    public void enemyStats(ArrayList<Enemy>enemyList){
        for(int i=0; i < enemyList.size();i++){
            System.out.print("\u001B[31m" + enemyList.get(i).getName() +"("+enemyList.get(i).getLife()+"❤️ | " + enemyList.get(i).getDefense() + "🛡️) " + ", " + "\u001B[0m" );
        }
    }
public void checkWizardLife(Wizard wizard){
    if(!wizard.isAlive()){
        runSystemAttack = false;
    }
}
public void checkEnemyList(ArrayList<Enemy> enemyList){
    if(enemyList.isEmpty()){
        runSystemAttack = false;
    }
}

public void createEnemyScene(Stage stage,Level level){
        round +=1;
    enemyGridpane = new GridPane();
    StackPane stackPane = new StackPane();
    StackPane.setAlignment(enemyGridpane, Pos.CENTER);
    enemyScene = new Scene(stackPane,600,600);
    wizard.getSpriteView().setViewport(new Rectangle2D(0, 64, 64, 64));
    stackPane.getChildren().addAll(enemyGridpane);
    spellButton = new Button("Spell");
    potionButton = new Button("Potions");
    objectButton = new Button("Objects");
    Label stats = new Label("❤️" + wizard.getLife() +" | " + "🛡️" + wizard.getDefense() + " | " + "💧" + wizard.getMana()+ " | " + "🪙" + wizard.getGold());
    Label playerName = new Label(wizard.getName());
    for(int i=0;i<enemyList.size();i++){
          enemyList.get(i).getSpriteView().setViewport(new Rectangle2D(0,5,64,64));
          Label enemyName = new Label(i+1+ " " + enemyList.get(i).getName() + " " +  enemyList.get(i).getLife() + "❤️");
          enemyGridpane.add(enemyList.get(i).getSpriteView(),5,i);
          enemyGridpane.setValignment(enemyList.get(i).getSpriteView(), VPos.BOTTOM);
          enemyGridpane.add(enemyName,5,i);
          enemyGridpane.setValignment(enemyName, VPos.TOP);
          enemyGridpane.setHalignment(enemyName, HPos.CENTER);
    }
    enemyGridpane.add(playerName,1,0);
    enemyGridpane.setValignment(playerName, VPos.BOTTOM);
    enemyGridpane.setHalignment(playerName, HPos.CENTER);
    enemyGridpane.add(wizard.getSpriteView(),1,1);
    enemyGridpane.add(spellButton,1,6);
    enemyGridpane.add(potionButton,2,6);
    enemyGridpane.add(objectButton,3,6);
    enemyGridpane.add(stats,0,0);
    eventSpellButton(level);
    eventPotionButton(level);
    eventObjectButton(level);
    stage.setScene(enemyScene);
    if(enemyList.isEmpty() && isBoss){
        System.out.println("COMBAT ENENMI FINI");
        round = 0;
        createBossScene(stage,level);
    }
    else if(enemyList.isEmpty() && !isBoss){
        Market market = new Market(wizard,stage,level);
        market.play();
    }

    if(!wizard.isAlive()){
        createDeathScene();
    }
}
public void eventSpellButton(Level level){
        Label numberSpell = new Label("Spell");
        Label enemyChoiceText = new Label("Choose a ennemy");
        ComboBox<Integer> spellChoice = new ComboBox<>();
        Button validateButton = new Button("Validate");
        ComboBox<Integer> enemyComboBox = new ComboBox<>();
        spellButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                enemyGridpane.getChildren().remove(spellButton);
                enemyGridpane.getChildren().remove(potionButton);
                enemyGridpane.getChildren().remove(objectButton);
                for(int i=0; i<wizard.getSpellList().size();i++){
                    Label spellName = new Label(i+1 + ". " + wizard.getSpellList().keySet().toArray()[i].toString());
                    spellChoice.getItems().add(i,i+1);
                    enemyGridpane.add(spellName,i,4);
                }
                for(int i=0; i<enemyList.size();i++){
                    Label enemyName = new Label(i +". " + enemyList.get(i).getName() + " " + enemyList.get(i).getLife() + " HP");
                    //enemyGridpane.add(enemyName,i,3);
                    enemyComboBox.getItems().add(i,i+1);
                }
                enemyGridpane.add(numberSpell,0,5);
                enemyGridpane.add(spellChoice,1,5);
                enemyGridpane.add(enemyChoiceText,0,6);
                enemyGridpane.add(enemyComboBox,1,6);
                enemyGridpane.add(validateButton,0,7);
                validateButton.setOnAction(actionEvent1 -> {
                    int spellNumber = spellChoice.getValue();
                    int enemyNumber = enemyComboBox.getValue();
                    String currentSpellName = wizard.getSpellList().keySet().toArray()[spellNumber - 1].toString();
                    AuthorizedSpell currentSpell = wizard.getSpellList().get(currentSpellName);
                    spellSystem(currentSpell,enemyNumber,round,level); //TODO changer le currentRound
                    enemyGridpane.getChildren().removeAll();
                    if(stage.getScene() == enemyScene){
                        //createEnemyScene(stage,level);
                    }
                    else{
                        //createBossScene(stage,level);
                    }
                });
            }

        });}
    public void spellSystem(AuthorizedSpell currentSpell,int enemyNumber,int currentRound,Level level){
        AbstractSpell enemyWizardCurrentSpell;
        Label objectChoice = new Label("Avec quel objet voulez vous intéragir?");
        ComboBox<Integer> objectComboBox = new ComboBox<>();
        Button validateObject = new Button("Interact with this item");
        if(currentSpell.getMakeDamage()){
            if(currentSpell.getName() == "Basic Attack"){
                wizard.attack(enemyList.get(enemyNumber - 1), currentSpell.getDamage(),enemyList.get(enemyNumber - 1).getDefense(), currentSpell.getSuccessPercentage());
                multipleRoundSpellAction(wizard,enemyList.get(enemyNumber-1));
                resetSpellRoundCounter();
            }
            else{
                spellAction(wizard,currentSpell,enemyList.get(enemyNumber-1));
                multipleRoundSpellCheck(currentSpell);
                multipleRoundSpellAction(wizard,enemyList.get(enemyNumber-1));
            }
            checkEnemyLife(enemyList,enemyNumber,wizard);
            if(!enemyList.isEmpty()){
                if(!enemyList.get(0).getIsWizard()){
                    enemyList.get(0).attack(wizard,enemyList.get(0).getWeapon().getDamage(),wizard.getDefense(),80);
                }
                else{
                    enemyWizardCurrentSpell = enemyList.get(0).randomEnemyWizardSpell();
                    enemyList.get(0).enemyWizardAttack(wizard,enemyWizardCurrentSpell);
                    enemyMultipleRoundSpellCheck(enemyWizardCurrentSpell);
                    enemyMultipleRoundSpellAction(enemyList.get(0),wizard);


                }
            }
            resetSpellRoundCounter();
            enemyResetSpellRoundCounter();
            createEnemyScene(stage,level);
        }
        else{
            if(!availableWorldItem.isEmpty() && currentSpell.canInteractWithItem()){
                for (int i=0;i< availableWorldItem.size();i++){
                    System.out.println(i + ". " + availableWorldItem.get(i).getName());
                    Label objectLabel = new Label(i + ". " + availableWorldItem.get(i).getName());
                    enemyGridpane.add(objectLabel,i,9);
                    objectComboBox.getItems().add(i,i);
                }
                enemyGridpane.add(objectChoice,10,8);
                enemyGridpane.add(objectComboBox,10,10);
                enemyGridpane.add(validateObject,10,11);
                if(!enemyList.isEmpty()){
                    if(!enemyList.get(0).getIsWizard()){
                        enemyList.get(0).attack(wizard,enemyList.get(0).getWeapon().getDamage(),wizard.getDefense(),80);
                    }
                    else{
                        enemyWizardCurrentSpell = enemyList.get(0).randomEnemyWizardSpell();
                        enemyList.get(0).enemyWizardAttack(wizard,enemyWizardCurrentSpell);
                        enemyMultipleRoundSpellCheck(enemyWizardCurrentSpell);
                        enemyMultipleRoundSpellAction(enemyList.get(0),wizard);

                    }                    }
                validateObject.setOnAction(actionEvent -> {
                    int indexItem = objectComboBox.getValue();
                    wizard.getSpellList().get(currentSpell.getName()).actionOnItem(availableWorldItem.get(indexItem),wizard.getInventory());
                    if(wizard.getInventory().getItemsList().get(0).getName() == availableWorldItem.get(indexItem).getName()){
                        availableWorldItem.remove(indexItem);

                    }
                    createEnemyScene(stage,level);
                });
            }
            else if(availableWorldItem.isEmpty() && currentSpell.canInteractWithItem()){
                System.out.println("Il n'y a aucun objet disponible avec lequel intéragir");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Il n'y a aucun objet avec lequel intéragir");
                alert.showAndWait();

            }
            else{
                currentSpell.actionOnCharacter(enemyList.get(enemyNumber - 1));
                checkEnemyLife(enemyList,enemyNumber,wizard);
                multipleRoundSpellCheck(currentSpell);
                multipleRoundSpellAction(wizard,enemyList.get(enemyNumber-1));
                if(!enemyList.isEmpty()){
                    if(!enemyList.get(0).getIsWizard()){
                        enemyList.get(0).attack(wizard,enemyList.get(0).getWeapon().getDamage(),wizard.getDefense(),80);
                    }
                    else{
                        enemyWizardCurrentSpell = enemyList.get(0).randomEnemyWizardSpell();
                        enemyList.get(0).enemyWizardAttack(wizard,enemyWizardCurrentSpell);
                        enemyMultipleRoundSpellCheck(enemyWizardCurrentSpell);
                        enemyMultipleRoundSpellAction(enemyList.get(0),wizard);

                    }                    }
                resetSpellRoundCounter();
                enemyResetSpellRoundCounter();
                createEnemyScene(stage,level);
            }


            }

    }

    public void resetCombatTarget(){
        incendioCast = false;
        isIncendioCurrentEffect = false;
        enemyIncendioCast = false;
        enemyIsIncendioCurrentEffect = false;
        petrificusCast = false;
        isPetrificusCurrentEffect = false;
        enemyPetrificusCast = false;
        enemyIsPetrificusCurrentEffect = false;
        incendioRound =0;
        petrificusRound = 0;
        enemyIncendioRound = 0;
        enemyPetrificusRound = 0;
    }

    public void eventPotionButton(Level level){
        Button takePotionBtn = new Button("Take the potion");
        Button backButton = new Button("back");
        ComboBox<Integer> potionComboBox = new ComboBox<>();
        potionButton.setOnAction(actionEvent -> {
            enemyGridpane.getChildren().remove(spellButton);
            enemyGridpane.getChildren().remove(potionButton);
            enemyGridpane.getChildren().remove(objectButton);
            if(!wizard.getPotionsList().isEmpty()){
                Label potionLabel = new Label("Which potion do you want to use?");
                enemyGridpane.add(potionLabel,0,5);
                for(int i=0; i<wizard.getPotionsList().size();i++){
                    Label potionListLabel = new Label( i + 1 +". " + wizard.getPotionsList().get(i).getName());
                    enemyGridpane.add(potionListLabel,i,6);
                    potionComboBox.getItems().add(i,i+1);
                }
                enemyGridpane.add(potionComboBox,0,7);
                enemyGridpane.add(takePotionBtn,1,7);
                enemyGridpane.add(backButton,2,7);

                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

                takePotionBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int potionNumber = potionComboBox.getValue();
                        potionSystem(potionNumber);
                        createEnemyScene(stage,level);
                    }
                });
            }
            else{
                Label noPotionLabel = new Label("You don't have any potion");
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }

                    }
                });
                enemyGridpane.add(noPotionLabel,0,6);
                enemyGridpane.add(backButton,0,7);
            }

        });
    }

    public void eventObjectButton(Level level){
        Label objectLabel = new Label("Wich item do you want to use?");
        Button useObjectBtn = new Button("Use this object");
        Button backBtn = new Button("Back");
        ComboBox<Integer> objectComboBox= new ComboBox<>();
        objectButton.setOnAction(actionEvent -> {
            enemyGridpane.getChildren().remove(spellButton);
            enemyGridpane.getChildren().remove(potionButton);
            enemyGridpane.getChildren().remove(objectButton);
            if(!wizard.getInventory().getItemsList().isEmpty()){
                enemyGridpane.add(objectLabel,0,6);
                for(int i= 0; i<wizard.getInventory().getItemsList().size();i++){
                    Label objectListLabel = new Label(i+1 + ". " + wizard.getInventory().getItemsList().get(i).getName());
                    objectComboBox.getItems().add(i,i+1);
                    enemyGridpane.add(objectListLabel,i,7);
                }
                enemyGridpane.add(useObjectBtn,0,8);
                enemyGridpane.add(objectComboBox,1,8);
                enemyGridpane.add(backBtn,2,8);
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

                useObjectBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int objectNumber = objectComboBox.getValue();
                        objectSystem(objectNumber-1,level);
                    }
                });
            }
            else{
                Label noObjectLabel = new Label("You don't have any object in your inventory");
                enemyGridpane.add(noObjectLabel,0,6);
                enemyGridpane.add(backBtn,0,7);
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

            }

        });
    }

    public void multipleRoundSpellAction(Wizard strikerCharacter,Character character) {
        //print("on passe");
        if ((strikerCharacter.getCanPlay() && (!this.isPetrificusCurrentEffect || !this.isIncendioCurrentEffect)) || (strikerCharacter.getCanPlay() && (this.isPetrificusCurrentEffect || this.isIncendioCurrentEffect))) {
            //print("condition basique");
            if ((this.petrificusCast && this.petrificusRound <= 2)) {
                this.petrificusRound =0;
                this.petrificusTarget = character;
                petrificusSystem(strikerCharacter);
                this.isPetrificusCurrentEffect = true;
                this.petrificusCast = false;
                //print("condition 1");
            }else if((!this.petrificusCast && this.petrificusRound <=2 && this.isPetrificusCurrentEffect)){
                this.petrificusRound += 1;
                petrificusSystem(strikerCharacter);
                //print("condition 2");
            }
            if ((this.incendioCast && this.incendioRound <= 3) ) {
                this.incendioRound =0;
                this.incendioTarget = character;
                incendioSystem(strikerCharacter);
                this.isIncendioCurrentEffect = true;
                this.incendioCast = false;
            }
            else if (!this.incendioCast && this.incendioRound <=3 && this.isIncendioCurrentEffect){
                this.incendioRound +=1;
                incendioSystem(strikerCharacter);
            }
        }
        else if (!strikerCharacter.getCanPlay() && (this.isPetrificusCurrentEffect || this.isIncendioCurrentEffect)) {
            if (!this.petrificusCast && this.petrificusRound <= 2 && this.isPetrificusCurrentEffect) {
                this.petrificusRound += 1;
                petrificusSystem(strikerCharacter);
            }if (!this.incendioCast && this.incendioRound <= 3 && this.isIncendioCurrentEffect) {
                this.incendioRound += 1;
                incendioSystem(strikerCharacter);
            }
        }
    }

    public void resetSpellRoundCounter(){
        if(this.petrificusRound ==2){
            this.isPetrificusCurrentEffect = false;
            this.petrificusRound = 0;
            this.petrificusTarget.setCanPlay(true);
        }
        else if(this.incendioRound == 3){
            this.isIncendioCurrentEffect = false;
            this.incendioRound =0;

        }
    }

    public void spellAction(Character strikerCharacter, AbstractSpell spell, Character targetCharacter){
        if(spell.getName() == "Wingardium Leviosa" || spell.getName() == "Sectumsempra"){
            if(strikerCharacter.getCanPlay()){
                spell.actionOnCharacter(targetCharacter);
            }
            else{
                //print("Vous ne pouvez pas attaquer pendant ce tour!");
            }
        }
    }

    public void petrificusSystem(Wizard strikerCharacter){
        strikerCharacter.getSpellList().get("Petrificus").actionOnCharacter(this.petrificusTarget);
        //print("Vous pétrifiez " + this.petrificusTarget.getName() + " pendant encore " + Integer.toString(2 - petrificusRound) + " tours");
    }

    public void incendioSystem(Wizard strikerCharacter){
        strikerCharacter.getSpellList().get("Incendio").actionOnCharacter(this.incendioTarget);
        //print("Vous infliger 10 points de dégâts de brûlure à " + this.incendioTarget.getName() + " pendant encore " + Integer.toString(3 - incendioRound) + " tours");
    }

    public void enemyPetrificusSystem(Enemy strikerCharacter){
        strikerCharacter.getSpellList().get("Petrificus").actionOnCharacter(this.enemyPetrificusTarget);
        //print(strikerCharacter.getName() + " Vous pétrifie " + "pendant encore " + Integer.toString( 2-enemyPetrificusRound) + " tours");
    }

    public void enemyIncendioSystem(Enemy strikerCharacter){
        strikerCharacter.getSpellList().get("Incendio").actionOnCharacter(this.enemyIncendioTarget);
        //print(strikerCharacter.getName() + " Vous inflige 10 points de dégâts de brûlure " +  " pendant encore " + Integer.toString(3 - enemyIncendioRound) + " tours");
    }

    public void checkEnemyLife(ArrayList<Enemy>enemyList,int player_attack_choice,Wizard wizard){
        if(!enemyList.get(player_attack_choice - 1).isAlive()){
            //print(" ☠️ L'ennemie " + enemyList.get(player_attack_choice - 1).getName() + " est mort! ☠️");
            wizard.addGold(enemyList.get(player_attack_choice - 1).getGoldValue());
            enemyList.remove(player_attack_choice - 1);
        }
    }

    public void multipleRoundSpellCheck(AbstractSpell spell){
        if(spell.getName() == "Petrificus"){
            this.petrificusCast = true;
        }
        else if(spell.getName() == "Incendio"){
            this.incendioCast = true;
        }
    }
    public void enemyMultipleRoundSpellCheck(AbstractSpell spell){
        if(spell.getName() == "Petrificus"){
            this.enemyPetrificusCast = true;
        }
        else if(spell.getName() == "Incendio"){
            this.enemyIncendioCast = true;
        }
    }

    public void enemyMultipleRoundSpellAction(Enemy strikerCharacter,Character character){
        if ((strikerCharacter.getCanPlay() && (!this.enemyIsPetrificusCurrentEffect || !this.enemyIsIncendioCurrentEffect)) || (strikerCharacter.getCanPlay() && (this.enemyIsPetrificusCurrentEffect || this.enemyIsIncendioCurrentEffect))) {
            if ((this.enemyPetrificusCast && this.enemyPetrificusRound <= 2)) {
                this.enemyPetrificusRound =0;
                this.enemyPetrificusTarget = character;
                enemyPetrificusSystem(strikerCharacter);
                this.enemyIsPetrificusCurrentEffect = true;
                this.enemyPetrificusCast = false;
                //print("condition 1");
            }else if((!this.enemyPetrificusCast && this.enemyPetrificusRound <=2 && this.enemyIsPetrificusCurrentEffect)){
                this.enemyPetrificusRound += 1;
                enemyPetrificusSystem(strikerCharacter);
                //print("condition 2");
            }
            if ((this.enemyIncendioCast && this.enemyIncendioRound <= 3) ) {
                this.enemyIncendioRound =0;
                this.enemyIncendioTarget = character;
                enemyIncendioSystem(strikerCharacter);
                this.enemyIsIncendioCurrentEffect = true;
                this.enemyIncendioCast = false;
            }
            else if (!this.enemyIncendioCast && this.enemyIncendioRound <=3 && this.enemyIsIncendioCurrentEffect){
                this.enemyIncendioRound +=1;
                enemyIncendioSystem(strikerCharacter);
            }
        }
        else if (!strikerCharacter.getCanPlay() && (this.enemyIsPetrificusCurrentEffect || this.enemyIsIncendioCurrentEffect)) {
            if (!this.enemyPetrificusCast && this.enemyPetrificusRound <= 2) {
                this.enemyPetrificusRound += 1;
                enemyPetrificusSystem(strikerCharacter);
            }if (!this.enemyIncendioCast && this.enemyIncendioRound <= 3) {
                this.enemyIncendioRound += 1;
                enemyIncendioSystem(strikerCharacter);
            }
        }
        else{
            //print("Vous ne pouvez pas attaquer !");
        }

    }

    public void enemyResetSpellRoundCounter(){
        if(this.enemyPetrificusRound ==2){
            this.enemyIsPetrificusCurrentEffect = false;
            this.enemyPetrificusRound = 0;
            this.enemyPetrificusTarget.setCanPlay(true);
        }
        else if(this.enemyIncendioRound == 3){
            this.enemyIsIncendioCurrentEffect = false;
            this.enemyIncendioRound =0;
        }
    }

    public void potionSystem(int numberPotion){
        AbstractSpell enemyWizardCurrentSpell;
        wizard.usePotion(numberPotion,wizard.getInventory().getPotionList());
        if(!enemyList.isEmpty()){
            if(!enemyList.get(0).getIsWizard()){
                enemyList.get(0).attack(wizard,enemyList.get(0).getWeapon().getDamage(),wizard.getDefense(),80);
            }
            else{
                enemyWizardCurrentSpell = enemyList.get(0).randomEnemyWizardSpell();
                enemyList.get(0).enemyWizardAttack(wizard,enemyWizardCurrentSpell);
                enemyMultipleRoundSpellCheck(enemyWizardCurrentSpell);
                enemyMultipleRoundSpellAction(enemyList.get(0),wizard);
                enemyResetSpellRoundCounter();
            }
        }

    }

    public void createBossScene(Stage stage,Level level){
        round +=1;
        Market market = new Market(wizard,stage,level);
        bossGridPane = new GridPane();
        StackPane stackPane = new StackPane();
        bossScene = new Scene(stackPane,600,600);
        wizard.getSpriteView().setViewport(new Rectangle2D(0, 64, 64, 64));
        stackPane.getChildren().addAll(bossGridPane);
        spellButton = new Button("Spell");
        potionButton = new Button("Potions");
        objectButton = new Button("Objects");
        Label stats = new Label("❤️" + wizard.getLife() +" | " + "🛡️" + wizard.getDefense() + " | " + "💧" + wizard.getMana()+ " | " + "🪙" + wizard.getGold());
        Label playerName = new Label(wizard.getName());

        boss.getSpriteView().setViewport(new Rectangle2D(0,5,64,64));
        Label bossName = new Label(1+ " " + boss.getName() + " " +  boss.getLife() + "❤️");
        bossGridPane.add(boss.getSpriteView(),2,1);
        bossGridPane.setValignment(boss.getSpriteView(), VPos.BOTTOM);
        bossGridPane.add(bossName,2,1);
        bossGridPane.setValignment(bossName, VPos.TOP);
        bossGridPane.setHalignment(bossName, HPos.CENTER);

        bossGridPane.add(playerName,1,0);
        bossGridPane.setValignment(playerName, VPos.BOTTOM);
        bossGridPane.setHalignment(playerName, HPos.CENTER);
        bossGridPane.add(wizard.getSpriteView(),1,1);
        bossGridPane.add(spellButton,1,6);
        bossGridPane.add(potionButton,2,6);
        bossGridPane.add(objectButton,3,6);
        bossGridPane.add(stats,0,0);
        eventSpellButtonBoss(level);
        eventPotionButtonBoss(level);
        eventObjectButtonBoss(level);
        stage.setScene(bossScene);
        if(!boss.isAlive()){
            market.play();
        }
    }

    public void objectSystem(int objectNumber,Level level){
        AbstractSpell enemyWizardCurrentSpell;
        wizard.getInventory().useItem(objectNumber);
        if(!enemyList.isEmpty()){
            if(!enemyList.get(0).getIsWizard()){
                enemyList.get(0).attack(wizard,enemyList.get(0).getWeapon().getDamage(),wizard.getDefense(),80);
            }
            else{
                enemyWizardCurrentSpell = enemyList.get(0).randomEnemyWizardSpell();
                enemyList.get(0).enemyWizardAttack(wizard,enemyWizardCurrentSpell);
                enemyMultipleRoundSpellCheck(enemyWizardCurrentSpell);
                enemyMultipleRoundSpellAction(enemyList.get(0),wizard);
                enemyResetSpellRoundCounter();
            }
        }
        createEnemyScene(stage,level);
    }

    public void eventSpellButtonBoss(Level level){
        Label numberSpell = new Label("Spell");
        Label enemyChoiceText = new Label("Choose a ennemy");
        ComboBox<String> spellChoice = new ComboBox<>();
        Button validateButton = new Button("Validate");
        ComboBox<Integer> enemyComboBox = new ComboBox<>();
        spellButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                bossGridPane.getChildren().remove(spellButton);
                bossGridPane.getChildren().remove(potionButton);
                bossGridPane.getChildren().remove(objectButton);
                for(int i=0; i<wizard.getSpellList().size();i++){
                    Label spellName = new Label(i+1 + ". " + wizard.getSpellList().keySet().toArray()[i].toString());
                    spellChoice.getItems().add(i,wizard.getSpellList().keySet().toArray()[i].toString());
                    bossGridPane.add(spellName,i,4);
                }
                enemyComboBox.getItems().add(0,1);
                bossGridPane.add(numberSpell,0,5);
                bossGridPane.add(spellChoice,1,5);
                bossGridPane.add(enemyChoiceText,0,6);
                bossGridPane.add(enemyComboBox,1,6);
                bossGridPane.add(validateButton,0,7);
                validateButton.setOnAction(actionEvent1 -> {
                    String spellname = spellChoice.getValue();
                    spellBossSystem(spellname);
                    bossGridPane.getChildren().removeAll();
                    if(stage.getScene() == enemyScene){
                        createEnemyScene(stage,level);
                    }
                    else{
                        createBossScene(stage,level);
                    }
                });
            }

        });
    }
    public void eventPotionButtonBoss(Level level){
        Button takePotionBtn = new Button("Take the potion");
        Button backButton = new Button("back");
        ComboBox<Integer> potionComboBox = new ComboBox<>();
        potionButton.setOnAction(actionEvent -> {
            bossGridPane.getChildren().remove(spellButton);
            bossGridPane.getChildren().remove(potionButton);
            bossGridPane.getChildren().remove(objectButton);
            if(!wizard.getPotionsList().isEmpty()){
                Label potionLabel = new Label("Which potion do you want to use?");
                bossGridPane.add(potionLabel,0,5);
                for(int i=0; i<wizard.getPotionsList().size();i++){
                    Label potionListLabel = new Label( i + 1 +". " + wizard.getPotionsList().get(i).getName());
                    bossGridPane.add(potionListLabel,i,6);
                    potionComboBox.getItems().add(i,i+1);
                }
                bossGridPane.add(potionComboBox,0,7);
                bossGridPane.add(takePotionBtn,1,7);
                bossGridPane.add(backButton,2,7);
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

                takePotionBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int potionNumber = potionComboBox.getValue();
                        potionSystem(potionNumber);
                    }
                });
            }
            else{
                Label noPotionLabel = new Label("You don't have any potion");
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }

                    }
                });
                bossGridPane.add(noPotionLabel,0,6);
                bossGridPane.add(backButton,0,7);
            }

        });
    }
    public void eventObjectButtonBoss(Level level){
        Label objectLabel = new Label("Wich item do you want to use?");
        Button useObjectBtn = new Button("Use this object");
        Button backBtn = new Button("Back");
        ComboBox<Integer> objectComboBox= new ComboBox<>();
        objectButton.setOnAction(actionEvent -> {
            bossGridPane.getChildren().remove(spellButton);
            bossGridPane.getChildren().remove(potionButton);
            bossGridPane.getChildren().remove(objectButton);
            if(!wizard.getInventory().getItemsList().isEmpty()){
                bossGridPane.add(objectLabel,0,6);
                for(int i= 0; i<wizard.getInventory().getItemsList().size();i++){
                    Label objectListLabel = new Label(i+1 + ". " + wizard.getInventory().getItemsList().get(i).getName());
                    objectComboBox.getItems().add(i,i+1);
                    bossGridPane.add(objectListLabel,i,7);
                }
                bossGridPane.add(useObjectBtn,0,8);
                bossGridPane.add(useObjectBtn,1,8);
                bossGridPane.add(backBtn,2,8);
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

                useObjectBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int objectNumber = objectComboBox.getValue();
                        objectSystem(objectNumber,level);
                        //createEnemyScene(stage,level);
                    }
                });
            }
            else{
                Label noObjectLabel = new Label("You don't have any object in your inventory");
                bossGridPane.add(noObjectLabel,0,6);
                bossGridPane.add(backBtn,0,7);
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(stage.getScene() == enemyScene){
                            createEnemyScene(stage,level);
                        }
                        else{
                            createBossScene(stage,level);
                        }
                    }
                });

            }

        });
    }
    public void spellBossSystem(String currentSpellName){
        AuthorizedSpell currentAuthorizedSpell;
        currentAuthorizedSpell = this.wizard.getSpellList().get(currentSpellName);
        if(currentAuthorizedSpell.getMakeDamage()){
            this.wizard.attack(this.boss, currentAuthorizedSpell.getDamage(),this.boss.getDefense(), currentAuthorizedSpell.getSuccessPercentage());
            if(this.boss.isAlive()){
                this.boss.attack(this.wizard,this.boss.getWeapon().getDamage(),this.wizard.getDefense(),this.boss.getSuccessAttackRate());
            }
        }
        else{
            currentAuthorizedSpell.actionOnCharacter(this.boss);
            if(this.boss.isAlive()){
                this.boss.attack(this.wizard,this.boss.getWeapon().getDamage(),this.wizard.getDefense(),this.boss.getSuccessAttackRate());
            }
        }
    }

    public void createDeathScene(){
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        stackPane.getChildren().addAll(gridPane);
        deathScene = new Scene(stackPane,600,600);
        Label gameOver = new Label("You are dead! Game Over");
        gameOver.setFont(new Font(20));
        gridPane.add(gameOver,0,0);
        stage.setScene(deathScene);
    }

}

