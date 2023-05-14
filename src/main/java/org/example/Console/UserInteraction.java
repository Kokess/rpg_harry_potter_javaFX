package org.example.Console;

import org.example.Characters.Character;
import org.example.Characters.Npc.AbstractEnemy;
import org.example.Characters.Npc.Boss;
import org.example.Characters.Npc.Enemy;
import org.example.Characters.Player.Inventory;
import org.example.Characters.Player.Wizard;
import org.example.Items.Item;
import org.example.Spells.AbstractSpell;
import org.example.Spells.AuthorizedSpell;

import java.util.*;

public class UserInteraction {

    private Scanner scanner;
    private boolean incendioCast = false; private boolean isIncendioCurrentEffect = false;
    private boolean enemyIncendioCast = false; private boolean enemyIsIncendioCurrentEffect = false;
    private boolean petrificusCast = false;private boolean isPetrificusCurrentEffect = false;
    private boolean enemyPetrificusCast = false;private boolean enemyIsPetrificusCurrentEffect = false;
    private int incendioRound =0; private int petrificusRound = 0;
    private int enemyIncendioRound = 0; private int enemyPetrificusRound = 0;
    private Character incendioTarget; private Character petrificusTarget;
    private Character enemyIncendioTarget;private Character enemyPetrificusTarget;
    public UserInteraction(){scanner = new Scanner(System.in);}
    public String Scanner_String(String message){
        //Scanner scanner = new Scanner(System.in);
        boolean inputValid = false;
        while(!inputValid){
            try{
                System.out.println(message);
                String answer = scanner.nextLine();
                inputValid = true;
                //scanner.close();  //fait bugger les scanners, une fois cette ligne executé, on ne peut plus faire de scanner dans d'autres fonctions
                return answer;      //(ferme également le system.in (flux de stream))

            }
            catch(InputMismatchException e){
                System.out.println("Le format de votre réponse n'est pas valide");
                scanner.next();
            }
        }
        return "";
    }
    public int Scanner_Int(String message){
        //Scanner scanner = new Scanner(System.in);
        boolean inputValid = false;
        while(!inputValid){
            try{
                System.out.println(message);
                int answer = scanner.nextInt();
                //scanner.close();
                inputValid = true;
                return answer;
            }
            catch(InputMismatchException e){
                System.out.println("Le format de votre réponse n'est pas valide");
                scanner.next();
            }
        }
        return 0;
    }
    public int ScannerAttackChoice(List<Enemy> enemyList){     //choix de la cible à attaquer
        int choice = 0;
        System.out.println("Choisissez quel ennemie vous voulez attaquer");
        for(int i=0; i < enemyList.size();i++){
            System.out.print("\u001B[31m" + enemyList.get(i).getName() +"("+enemyList.get(i).getLife()+"❤️ | " + enemyList.get(i).getDefense() + "🛡️) " + ", " + "\u001B[0m" );
        }
        do{
            System.out.println("Tapez un nombre entre 1 et " + enemyList.size());
            choice = scanner.nextInt();
        }
        while(choice > enemyList.size());
        return choice;
    }
    public String ScannerSpellChoice(HashMap<String, AuthorizedSpell> spellList){
        int count = 1;
        String result = "";
        int answer = 0;

        do{
            print("Choisissez le sort que vous voulez utiliser 🪄");
            for(String i : spellList.keySet()){
                System.out.print(count + ": "+ i +" ");
                count++;
            }
            answer = scanner.nextInt();
            result = spellList.keySet().toArray()[answer -1].toString();
        }
        while(answer ==0 || answer >spellList.size());

        return result;
    }
    public void actionChoiceBoss(Inventory inventory, HashMap<String, AuthorizedSpell> spellList, Wizard wizard, Boss boss, ArrayList<Item> worldItemAvailable){
        System.out.println("Que voulez vous faire?");
        int numberChoice = Scanner_Int("1. Utiliser un sort | 2. Utiliser un objet");
        switch (numberChoice){
            case 1:
                String currentSpellName = ScannerSpellChoice(spellList);
                AuthorizedSpell currentSpell = wizard.getSpellList().get(currentSpellName);
                if(currentSpell.getMakeDamage()){
                    wizard.attack(boss, currentSpell.getDamage(),boss.getDefense(), currentSpell.getSuccessPercentage());
                }
                else{
                    currentSpell.actionOnCharacter(boss);
                    if(!worldItemAvailable.isEmpty() && currentSpell.canInteractWithItem()){
                        for (int i=0;i< worldItemAvailable.size();i++){
                            System.out.println(i + ". " + worldItemAvailable.get(i).getName());
                        }
                        int indexItem = Scanner_Int("Avec quel objet voulez vous intéragir?");
                        wizard.getSpellList().get(currentSpellName).actionOnItem(worldItemAvailable.get(indexItem),wizard.getInventory());
                        worldItemAvailable.remove(indexItem);
                    }
                    else if(worldItemAvailable.isEmpty() && currentSpell.canInteractWithItem()){
                        System.out.println("Il n'y a aucun objet disponible avec lequel intéragir");
                        actionChoiceBoss(inventory,spellList,wizard,boss,worldItemAvailable);
                    }
                }
                break;
            case 2:
                int numberItemChoice = Scanner_Int("1. Utiliser une potion | 2. Utiliser un objet");
                switch (numberItemChoice){
                    case 1:
                        inventory.displayPotion();
                        int numberPotionChoice = Scanner_Int("Quelle potion voulez-vous utiliser? 1. Heal | 2. Mana | 3.Strength | 4. Luck | 5. Shield");
                        wizard.usePotion(numberPotionChoice,inventory.getPotionList());
                        break;
                    case 2:
                        if(!inventory.getItemsList().isEmpty())
                        {
                            inventory.displayItem();
                            int itemPosition = Scanner_Int("Quelle objet voulez vous utiliser?");
                            System.out.println("\u001B[32m" + "Vous utilisez le" + inventory.getItemsList().get(itemPosition).getName() +" et le plantez dans le Basilic!" + "\u001B[0m");
                            inventory.useItem(itemPosition);
                        }
                        else{
                            System.out.println("Vous n'avez aucun objet à utiliser");
                            actionChoiceBoss(inventory,spellList,wizard,boss,worldItemAvailable);
                        }
                        break;
                }
                break;
        }
    }
    public void scanner_Close(){
        scanner.close();
    }
    public void print(String text){
        System.out.println(text);
    }
    public void actionChoiceEnemy(Inventory inventory, HashMap<String, AuthorizedSpell> spellList, Wizard wizard, ArrayList<Enemy>enemyList, ArrayList<Item> worldItemAvailable,int currentRound){
        AbstractSpell enemyWizardCurrentSpell;
        print("Tour " + currentRound);
        print("Que voulez vous faire?");
        int numberChoice = Scanner_Int("1. Utiliser un sort | 2. Utiliser un objet");
        switch (numberChoice){
            case 1:
                String currentSpellName = ScannerSpellChoice(spellList);
                AuthorizedSpell currentSpell = wizard.getSpellList().get(currentSpellName);
                if(currentSpell.getMakeDamage()){
                    int player_attack_choice = ScannerAttackChoice(enemyList);
                    if(currentSpell.getName() == "Basic Attack"){
                        wizard.attack(enemyList.get(player_attack_choice - 1), currentSpell.getDamage(),enemyList.get(player_attack_choice - 1).getDefense(), currentSpell.getSuccessPercentage());
                        multipleRoundSpellAction(wizard,enemyList.get(player_attack_choice-1));
                        resetSpellRoundCounter();
                    }
                    else{
                        spellAction(wizard,currentSpell,enemyList.get(player_attack_choice-1));
                        multipleRoundSpellCheck(currentSpell);
                        multipleRoundSpellAction(wizard,enemyList.get(player_attack_choice-1));
                    }
                    checkEnemyLife(enemyList,player_attack_choice,wizard);
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
                }
                else{
                    if(!worldItemAvailable.isEmpty() && currentSpell.canInteractWithItem()){
                        for (int i=0;i< worldItemAvailable.size();i++){
                            System.out.println(i + ". " + worldItemAvailable.get(i).getName());
                        }
                        int indexItem = Scanner_Int("Avec quel objet voulez vous intéragir?");
                        wizard.getSpellList().get(currentSpellName).actionOnItem(worldItemAvailable.get(indexItem),wizard.getInventory());
                        if(wizard.getInventory().getItemsList().get(0).getName() == worldItemAvailable.get(indexItem).getName()){
                            worldItemAvailable.remove(indexItem);
                        }

                    }
                    else if(worldItemAvailable.isEmpty() && currentSpell.canInteractWithItem()){
                        System.out.println("Il n'y a aucun objet disponible avec lequel intéragir");
                        actionChoiceEnemy(inventory,spellList,wizard,enemyList,worldItemAvailable,currentRound);
                    }
                    int player_attack_choice = ScannerAttackChoice(enemyList);
                    currentSpell.actionOnCharacter(enemyList.get(player_attack_choice - 1));
                    checkEnemyLife(enemyList,player_attack_choice,wizard);
                    multipleRoundSpellCheck(currentSpell);
                    multipleRoundSpellAction(wizard,enemyList.get(player_attack_choice-1));
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
                }
                resetSpellRoundCounter();
                enemyResetSpellRoundCounter();
                break;
            case 2:
                useItem(inventory,wizard,spellList,enemyList,worldItemAvailable,currentRound);
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
                break;


        }
        //print("" + isIncendioCurrentEffect);
    }

    public void checkEnemyLife(ArrayList<Enemy>enemyList,int player_attack_choice,Wizard wizard){
        if(!enemyList.get(player_attack_choice - 1).isAlive()){
            print(" ☠️ L'ennemie " + enemyList.get(player_attack_choice - 1).getName() + " est mort! ☠️");
            wizard.addGold(enemyList.get(player_attack_choice - 1).getGoldValue());
            enemyList.remove(player_attack_choice - 1);
        }
    }
    public void useItem(Inventory inventory,Wizard wizard, HashMap<String,AuthorizedSpell> spellList, ArrayList<Enemy>enemyList,ArrayList<Item>worldItemAvailable,int currentRound){
        int numberItemChoice = Scanner_Int("1. Utiliser une potion | 2. Utiliser un objet");
        switch (numberItemChoice){
            case 1:
                inventory.displayPotion();
                int numberPotionChoice = Scanner_Int("Quelle potion voulez-vous utiliser? 1. Heal | 2. Mana | 3.Strength | 4. Luck | 5. Shield");
                wizard.usePotion(numberPotionChoice,inventory.getPotionList());
                break;
            case 2:
                if(!inventory.getItemsList().isEmpty())
                {
                    inventory.displayItem();
                    int itemPosition = Scanner_Int("Quelle objet voulez vous utiliser?");
                    System.out.println("\u001B[32m" + "Vous utilisez le" + inventory.getItemsList().get(itemPosition).getName() +" et le plantez dans le Basilic!" + "\u001B[0m");
                    inventory.useItem(itemPosition);
                }
                else{
                    System.out.println("Vous n'avez aucun objet à utiliser");
                    actionChoiceEnemy(inventory,spellList,wizard,enemyList,worldItemAvailable,currentRound);
                }
                break;
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
            print("Vous ne pouvez pas attaquer !");
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

    public void spellAction(Character strikerCharacter, AbstractSpell spell, Character targetCharacter){
        if(spell.getName() == "Wingardium Leviosa" || spell.getName() == "Sectumsempra"){
            if(strikerCharacter.getCanPlay()){
                spell.actionOnCharacter(targetCharacter);
            }
            else{
                print("Vous ne pouvez pas attaquer pendant ce tour!");
            }
        }
    }

    public void petrificusSystem(Wizard strikerCharacter){
        strikerCharacter.getSpellList().get("Petrificus").actionOnCharacter(this.petrificusTarget);
        print("Vous pétrifiez " + this.petrificusTarget.getName() + " pendant encore " + Integer.toString(2 - petrificusRound) + " tours");
    }

    public void incendioSystem(Wizard strikerCharacter){
        strikerCharacter.getSpellList().get("Incendio").actionOnCharacter(this.incendioTarget);
        print("Vous infliger 10 points de dégâts de brûlure à " + this.incendioTarget.getName() + " pendant encore " + Integer.toString(3 - incendioRound) + " tours");
    }

    public void enemyPetrificusSystem(Enemy strikerCharacter){
        strikerCharacter.getSpellList().get("Petrificus").actionOnCharacter(this.enemyPetrificusTarget);
        print(strikerCharacter.getName() + " Vous pétrifie " + "pendant encore " + Integer.toString( 2-enemyPetrificusRound) + " tours");
    }

    public void enemyIncendioSystem(Enemy strikerCharacter){
        strikerCharacter.getSpellList().get("Incendio").actionOnCharacter(this.enemyIncendioTarget);
        print(strikerCharacter.getName() + " Vous inflige 10 points de dégâts de brûlure " +  " pendant encore " + Integer.toString(3 - enemyIncendioRound) + " tours");
    }

}

