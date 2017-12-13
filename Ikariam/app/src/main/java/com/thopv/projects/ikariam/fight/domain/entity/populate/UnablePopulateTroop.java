package com.thopv.projects.ikariam.fight.domain.entity.populate;

/**
 * Created by thopv on 11/30/2017.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;

/**
 * Với những đơn vị đã hết đạn
 */
@Entity(primaryKeys = {"unitName", "party"})
public class UnablePopulateTroop {
    @NonNull
    private String unitName;
    private int amount;
    private int party;

    public UnablePopulateTroop(@NonNull String unitName, int amount, int party) {
        this.unitName = unitName;
        this.amount = amount;
        this.party = party;
    }
    public void merge(UnablePopulateTroop unablePopulateTroop){
        if(this.equals(unablePopulateTroop)){
            amount += unablePopulateTroop.getAmount();
        }
    }
    public void clear(){
        amount = 0;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UnablePopulateTroop){
            return getUnitName().equalsIgnoreCase(((UnablePopulateTroop) obj).getUnitName())
                    && getParty() == ((UnablePopulateTroop) obj).getParty();
        }
        return super.equals(obj);
    }
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
    public int subtract(int subAmount) {
        int result = 0;
        if(subAmount > amount){
            result = amount;
            amount = 0;
        }
        else{
            result = subAmount;
            amount -= subAmount;
        }
        return result;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }
}
