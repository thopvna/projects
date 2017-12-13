package com.thopv.projects.ikariam.data.schema.units.properties;

import java.util.ArrayList;
import java.util.List;

public class UnitAttributes {
    private List<Weapon> weapons;
    private int armour;
    private int hitpoints;
    private int speed;
    private int size;
    private String type;
    public UnitAttributes(){

    }
    public UnitAttributes(Weapon weapon, int armour, int hitpoints, int speed, int size, String type){
       this.weapons = new ArrayList<>();
       this.weapons.add(weapon);
       this.armour = armour;
       this.hitpoints = hitpoints;
       this.speed = speed;
       this.size = size;
       this.type = type;
    }
    public void addWeapon(Weapon weapon){
        this.weapons.add(weapon);
    }
    public void setArmour(int armour) {
        this.armour = armour;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getArmour() {
        return armour;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }
}
