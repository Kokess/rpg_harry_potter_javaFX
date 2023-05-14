package org.example.Items.objects;

import org.example.Characters.Npc.Boss;
import org.example.Items.Item;

public class BasilicFang extends Item{
    private Boss boss;
    public BasilicFang(String name,Boss basilic){
        this.name = name;
        this.boss = basilic;
    }
    @Override
    public void action() {
        this.boss.take_dmg(this.boss.getLife(),0);
    }
}

