package com.thopv.projects.ikariam.data.schema.armies;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import junit.framework.Assert;

/**
 * Created by thopv on 11/14/2017.
 */
@Entity(primaryKeys = {"unitName"})
public class AttackTroop {
    @NonNull
    private String unitName;
    private int amount;

    @NonNull
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(@NonNull String unitName) {
        this.unitName = unitName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AttackTroop(@NonNull String unitName, int amount) {
        this.unitName = unitName;
        this.amount = amount;
    }
    public int subtractAmount(int subtractAmount){
        if(subtractAmount == 0 || amount == 0)
            return 0;
        if(subtractAmount > amount){
            amount = 0;
            return amount;
        }
        else{
            amount -= subtractAmount;
            return subtractAmount;
        }
    }
    public void merge(BaseTroop troop){
        if(troop == null)
            return;
        Assert.assertEquals(unitName, troop.getUnitName());
        amount += troop.getAmount();
    }
    public void merge(int amount){
        this.amount += amount;
    }
    public void merge(AttackTroop troop){
        if(troop == null)
            return;
        Assert.assertEquals(unitName, troop.getUnitName());
        amount += troop.getAmount();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AttackTroop)
            return getUnitName().equalsIgnoreCase(((AttackTroop) obj).getUnitName());
        return super.equals(obj);
    }

    public static class Builder {
        private String unitName;
        private int amount;

        public Builder setUnitName(String unitName) {
            this.unitName = unitName;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public AttackTroop build() {
            return new AttackTroop(unitName, amount);
        }
    }

}
