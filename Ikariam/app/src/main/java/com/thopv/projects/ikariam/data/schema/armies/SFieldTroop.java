package com.thopv.projects.ikariam.data.schema.armies;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by thopv on 11/13/2017.
 */
@Entity(primaryKeys = {"party", "unitName", "viewId"})
public class SFieldTroop {
    private int party;
    @NonNull private String unitName;
    private int viewId;

    private int amount;
    private int maxAmount;
    private int currentHitPoints;
    private int currentMunitions;

    public SFieldTroop(int party, @NonNull String unitName, int viewId, int amount, int maxAmount, int currentHitPoints, int currentMunitions) {
        this.party = party;
        this.unitName = unitName;
        this.viewId = viewId;
        this.amount = amount;
        this.maxAmount = maxAmount;
        this.currentHitPoints = currentHitPoints;
        this.currentMunitions = currentMunitions;
    }

    @NonNull
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(@NonNull String unitName) {
        this.unitName = unitName;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SFieldTroop){
            return getUnitName().equalsIgnoreCase(((SFieldTroop) obj).getUnitName())
                    && getParty() == ((SFieldTroop) obj).getParty()
                    && getViewId() == ((SFieldTroop) obj).getViewId()
                    ;
        }
        return super.equals(obj);
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

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public static class Builder {
        private int party;
        private String unitName;
        private int viewId;
        private int amount;
        private int maxAmount;
        private int currentHitPoints;
        private int currentMunitions;

        public Builder setParty(int party) {
            this.party = party;
            return this;
        }

        public Builder setUnitName(String unitName) {
            this.unitName = unitName;
            return this;
        }

        public Builder setViewId(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public Builder setCurrentHitPoints(int currentHitPoints) {
            this.currentHitPoints = currentHitPoints;
            return this;
        }

        public Builder setCurrentMunitions(int currentMunitions) {
            this.currentMunitions = currentMunitions;
            return this;
        }

        public SFieldTroop build() {
            return new SFieldTroop(party, unitName, viewId, amount, maxAmount, currentHitPoints, currentMunitions);
        }
    }
}
