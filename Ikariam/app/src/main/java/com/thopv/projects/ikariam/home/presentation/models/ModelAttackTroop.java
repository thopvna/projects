package com.thopv.projects.ikariam.home.presentation.models;

/**
 * Created by thopv on 11/20/2017.
 */
public class ModelAttackTroop {
    private String unitName;
    private int amount;

    public ModelAttackTroop(String unitName, int amount) {
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
}
