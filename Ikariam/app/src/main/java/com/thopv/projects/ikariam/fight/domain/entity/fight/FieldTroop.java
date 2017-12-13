package com.thopv.projects.ikariam.fight.domain.entity.fight;

import android.support.annotation.Nullable;
import android.util.Log;

import com.thopv.projects.ikariam.data.schema.units.properties.Weapon;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by thopv on 11/16/2017.
 */

public class FieldTroop {
    private int viewId;
    private String unitName;
    private int party;
    private int amount;
    private int maxAmount;

    
    private int currentTotalHitpoints;
    private int maxTotalHitpoints;
    
    private int totalArmours;
    private int extraArmour;
    
    private List<Weapon> weapons;
    private float extraDamePercent;

    public FieldTroop(int viewId, String unitName, int party, int amount, int maxAmount, int currentTotalHitpoints, List<Weapon> weapons) {
        this.viewId = viewId;
        this.unitName = unitName;
        this.party = party;
        this.amount = amount;
        this.maxAmount = maxAmount;
        this.currentTotalHitpoints = currentTotalHitpoints;
        this.weapons = weapons;

        Unit unit = UnitFactory.getUnit(unitName);

        maxTotalHitpoints = unit.getUnitAttributes().getHitpoints() * amount;
        totalArmours = unit.getUnitAttributes().getArmour() * amount;
    }
    public void clear(){
        amount = 0;
    }
    public FieldTroop(int viewId, String unitName, int party, int amount, int maxAmount) {
        this.viewId = viewId;
        this.unitName = unitName;
        this.party = party;
        this.maxAmount = maxAmount;
        this.amount = amount;
        
        Unit unit = UnitFactory.getUnit(unitName);

        currentTotalHitpoints += unit.getUnitAttributes().getHitpoints() * amount;
        maxTotalHitpoints += unit.getUnitAttributes().getHitpoints() * amount;
        totalArmours += unit.getUnitAttributes().getArmour() * amount;
        weapons = unit.getUnitAttributes().getWeapons();
    }
    public boolean hasMunitions(){
        return getCurrentWeapon() != null;
    }
    public void addAmount(int additionAmount){
        Assert.assertFalse("Total amount unable > max amount.", amount + additionAmount > maxAmount);
        Unit unit = UnitFactory.getUnit(unitName);
        if (additionAmount > 0) {
            amount += additionAmount;

            currentTotalHitpoints += unit.getUnitAttributes().getHitpoints() * additionAmount;
            maxTotalHitpoints += unit.getUnitAttributes().getHitpoints() * additionAmount;
            totalArmours += unit.getUnitAttributes().getArmour() * additionAmount;
            weapons = unit.getUnitAttributes().getWeapons();
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAdditionableAmount(){
        return maxAmount - amount;
    }
    public int getFirstClassMunitions(){
        return weapons.get(0).getMunition();
    }
    public void merge(FieldTroop fieldTroop){
        if(this.equals(fieldTroop))
            addAmount(fieldTroop.getAmount());
    }
    public int getFirstClassMaxMunitions(){
        return weapons.get(0).getMaxMunition();
    }
    private
    Weapon getCurrentWeapon(){
        if(weapons != null) {
            for (int i = 0; i < weapons.size(); i++){
                Weapon weapon = weapons.get(i);
                if (weapon.hasMunition()) {
                    return weapon;
                }
            }
        }
        return null;
    }
    public RealDame calculateTotalDames() {
        for(Weapon weapon : weapons){
            if(weapon.hasMunition()) {
                weapon.decreaseMunition();
                int dame = weapon.getDame()* amount;
                dame += dame * extraDamePercent;
                Log.e(getClass().getSimpleName(),getUnitName() + ": start fight with dame: " + dame);
                return new RealDame(dame, weapon.getAccuracy(), getPercentHitPoint());
            }
            else{
                Log.e(getClass().getSimpleName(),getUnitName() + ": don't have munitions " + weapon.getName() + ", currentMunitions = " + weapon.getMunition() + "/" + weapon.getMaxMunition());
            }
        }
        return null;
    }

    /**
     *
     * @param realDame
     * @return -1 if don't have rest dame
     */
    public int isFighted(@Nullable RealDame realDame){
        if(realDame != null) {
            Log.e(getClass().getSimpleName(), unitName + " defense with armour:  " + getTotalArmours());
            int attackHitpoints = realDame.calculateDameHitpoints(getTotalArmours());
            Log.e(getClass().getSimpleName(), unitName + " is fighted " + attackHitpoints + " hit points");
            if (attackHitpoints > 0) {
                return decreaseTotalHitPoints(attackHitpoints);
            }
        }
        return -1;
    }
    public void setExtraArmour(int extraArmour) {
        this.extraArmour = extraArmour;
    }

    public void setExtraDamePercent(float extraDamePercent) {
        this.extraDamePercent = extraDamePercent;
    }

    public int isFighted(FieldTroop fieldTroop){
        return isFighted(fieldTroop.calculateTotalDames());
    }
    public int isFighted(int restDame){
        int attackHitpoints = (restDame - getTotalArmours());
        Log.e("FightingTroop", unitName + " is fighted " + attackHitpoints + " hit points");
        if (attackHitpoints > 0) {
            return decreaseTotalHitPoints(attackHitpoints);
        }
        return 0;
    }

    /**
     * return rest dame if have.
     * @param attackHitpoints
     * @return
     */
    private int decreaseTotalHitPoints(int attackHitpoints) {
        if(attackHitpoints > 0) {
            Log.e("LOL", unitName + "is decrease: " + attackHitpoints + ", Origin Hitpoints : " + currentTotalHitpoints + ", Before Hitpoints : " + (currentTotalHitpoints - attackHitpoints));
            currentTotalHitpoints -= attackHitpoints;
            return currentTotalHitpoints < 0 ? Math.abs(currentTotalHitpoints) : 0;
        }
        return 0;
    }
    public boolean isAlive() {
        return unitName != null && amount > 0 && currentTotalHitpoints > 0;
    }
    public int getPercentHitPoint(){
        if(maxTotalHitpoints == 0)
            return 100;
        return currentTotalHitpoints * 100 / maxTotalHitpoints;
    }
    public int subtractUnits(int subAmount){
        if(amount == 0){
            currentTotalHitpoints = 0;
            maxTotalHitpoints = 0;
            totalArmours = 0;
            return 0;
        }

        int willSubtract;
        int origin = amount;

        if(origin <= subAmount){
            willSubtract = origin;
        }
        else{
            willSubtract = subAmount;
        }

        Unit unit = UnitFactory.getUnit(unitName);

        currentTotalHitpoints = currentTotalHitpoints * (origin - willSubtract) / origin;
        maxTotalHitpoints -= unit.getUnitAttributes().getHitpoints() * willSubtract;
        totalArmours -= unit.getUnitAttributes().getArmour() * willSubtract;

        return willSubtract;
    }

    public int getAmount(){
        //TODO: Cực kỳ nguy hiểm vì current total hit points <=0 nhưng amount vẫn cứ nguyên giá trị cũ.
        if(currentTotalHitpoints <= 0)
            return 0;
        return amount;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public static class RealDame{
        private int baseDame;
        private float accuracy;
        private int percentHitpoints;
        public RealDame(int baseDame, int accuracy, int percentHitpoints) {
            this.baseDame = baseDame;
            this.accuracy = 100 ;
            this.percentHitpoints = percentHitpoints;
        }
        public int getBaseDame() {
            return baseDame;
        }

        public float getAccuracy() {
            return accuracy;
        }
        public int calculateDameHitpoints(int totalAmours){
            int expected = (int) ((getBaseDame() - totalAmours) * getAccuracy() / 100 /** percentHitpoints / 100*/);
            return  expected > 0 ? expected : 0;
        }
    }
    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getCurrentTotalHitpoints() {
        return currentTotalHitpoints;
    }

    public void setCurrentTotalHitpoints(int currentTotalHitpoints) {
        this.currentTotalHitpoints = currentTotalHitpoints;
    }

    public int getTotalArmours() {
        return totalArmours + extraArmour * amount;
    }

    public void setTotalArmours(int totalArmours) {
        this.totalArmours = totalArmours;
    }

    public int getMaxTotalHitpoints() {
        return maxTotalHitpoints;
    }

    public void setMaxTotalHitpoints(int maxTotalHitpoints) {
        this.maxTotalHitpoints = maxTotalHitpoints;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FieldTroop){
            return getUnitName().equalsIgnoreCase(((FieldTroop) obj).getUnitName())
                    && getParty() == ((FieldTroop) obj).getParty()
                    && getViewId() == ((FieldTroop) obj).getViewId();
        }
        return super.equals(obj);
    }

    public static class Builder {
        private int viewId;
        private String unitName;
        private int party;
        private int maxAmount;
        private int amount;
        private int currentTotalHitpoints;
        private List<Weapon> weapons;

        public Builder() {
        }

        public Builder setViewId(int viewId) {
            this.viewId = viewId;
            return this;
        }

        public Builder setUnitName(String unitName) {
            this.unitName = unitName;
            return this;
        }

        public Builder setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCurrentTotalHitpoints(int currentTotalHitpoints) {
            this.currentTotalHitpoints = currentTotalHitpoints;
            return this;
        }

        public Builder setWeapons(List<Weapon> weapons) {
            this.weapons = weapons;
            return this;
        }

        public FieldTroop build() {
            return new FieldTroop(viewId, unitName, party, amount, maxAmount, currentTotalHitpoints, weapons);
        }

        public Builder setParty(int party) {
            this.party = party;
            return this;
        }
    }
}
