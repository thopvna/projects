package com.thopv.projects.ikariam.home.presentation.models;

import android.arch.persistence.room.Ignore;

import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

/**
 * Created by thopv on 11/20/2017.
 */
public class ModelFieldTroop {
    private int party;
    private String unitName;
    private int amount;
    private int maxAmount;
    private int viewId;

    private int currentHitPoints;
    private int currentMunitions;
    @Ignore
    private int maxHitpoints;
    @Ignore
    private int maxMunitions;

    public ModelFieldTroop(int party, String unitName, int amount, int maxAmount, int viewId, int currentHitPoints, int currentMunitions) {
        this.party = party;
        this.unitName = unitName;
        this.amount = amount;
        this.maxAmount = maxAmount;
        this.viewId = viewId;
        this.currentHitPoints = currentHitPoints;
        this.currentMunitions = currentMunitions;

        Unit unit = UnitFactory.getUnit(unitName);

        maxHitpoints = unit.getUnitAttributes().getHitpoints() * amount;
        maxMunitions = unit.getUnitAttributes().getWeapons().get(0).getMaxMunition();
    }

    public boolean isAlive(){
        return currentHitPoints > 0 && amount > 0;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }

    public int getCurrentMunitions() {
        return currentMunitions;
    }

    public void setCurrentMunitions(int currentMunitions) {
        this.currentMunitions = currentMunitions;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public int getMaxMunitions() {
        return maxMunitions;
    }

    public void setMaxMunitions(int maxMunitions) {
        this.maxMunitions = maxMunitions;
    }
}
