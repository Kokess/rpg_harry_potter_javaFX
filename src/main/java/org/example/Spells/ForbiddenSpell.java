package org.example.Spells;

import org.example.Characters.Character;
import org.example.Characters.Npc.AbstractEnemy;

public abstract class ForbiddenSpell extends AbstractSpell {
    protected int damage;
    public abstract void action(Character character);


}