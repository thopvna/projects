package com.thopv.projects.ikariam.home.presentation.models;

/**
 * Created by thopv on 11/15/2017.
 */
public class BaseTroop {
    private String unitName;
    private int amount;

    public BaseTroop(String unitName, int amount) {
        this.unitName = unitName;
        this.amount = amount;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseTroop) {
            return getUnitName().equalsIgnoreCase(((BaseTroop) obj).getUnitName());
        }
        return super.equals(obj);
    }
    public void merge(BaseTroop baseTroop){
        if(this.equals(baseTroop))
            amount += baseTroop.getAmount();
    }
}
