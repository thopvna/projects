package com.thopv.projects.ikariam.data.schema.armies;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;


/**
 * Created by thopv on 11/13/2017.
 */

/**
 * Home units
 */
@Entity(primaryKeys = {"party", "unitName"})
public class HomeTroop {
    private int party;
    @NonNull
    private String unitName;
    private int amount;

    public HomeTroop(int party, @NonNull String unitName, int amount) {
        this.party = party;
        this.unitName = unitName;
        this.amount = amount;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getAmount() {
        return amount;
    }
    public int subtract(int amount){
        if(amount >= this.amount) {
            int result = this.amount;
            this.amount = 0;
            return result;
        }
        else {
            this.amount -= amount;
            return amount;
        }
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void merge(HomeTroop homeTroop){
        if(homeTroop == null)
            return;
        if(homeTroop.getUnitName().equalsIgnoreCase(unitName))
            amount += homeTroop.getAmount();
    }
    public void merge(int amount){
        this.amount += amount;
    }
    public void merge(BaseTroop troop){
        if(troop.getUnitName().equalsIgnoreCase(unitName))
            amount += troop.getAmount();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HomeTroop)
            return getUnitName().equalsIgnoreCase(((HomeTroop) obj).getUnitName())
                    && getParty() == ((HomeTroop) obj).getParty();
        return super.equals(obj);
    }
}
