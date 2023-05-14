package org.example.Items.objects;

import org.example.Items.Item;
import org.example.Characters.Character;

public class GryffindorSword extends Item {
    private Character target;

    public GryffindorSword(Character character){
        this.name = "Gryffindor sword";
        this.target  = character;
    }

    @Override
    public void action() {
        this.target.take_dmg(this.target.getLife(),0);
    }
}

