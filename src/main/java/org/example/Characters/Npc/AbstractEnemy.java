package org.example.Characters.Npc;



import org.example.Characters.Character;
import org.example.Items.weapon.Wand;
import org.example.Items.weapon.Weapon;
import org.example.Spells.AbstractSpell;
import org.example.Spells.AuthorizedSpell;
import org.example.Characters.Character;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractEnemy extends Character {
    protected Weapon weapon;
    protected int goldValue;
    protected HashMap<String, AbstractSpell> spellList;
    protected boolean isWizard;
    protected Wand wand;
    public Weapon getWeapon(){
        return this.weapon;
    }
    public int getGoldValue(){return this.goldValue;}
    public void take_dmg(int damage,int defense){
        this.life -=(damage - ((defense * damage))/100);
    }



}

