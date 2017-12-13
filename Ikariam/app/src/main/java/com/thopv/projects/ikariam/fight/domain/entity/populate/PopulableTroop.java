package com.thopv.projects.ikariam.fight.domain.entity.populate;

/**
 * Created by thopv on 10/31/2017.
 */

public class PopulableTroop {
    private String unitName;
    private int amount;
    private int party;

    public PopulableTroop(String unitName, int amount, int party) {
        this.unitName = unitName;
        this.amount = amount;
        this.party = party;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAttack() {
        return party == 0;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int _amount){
        amount += _amount;
    }
    public void merge(PopulableTroop populableTroop){
        if(this.equals(populableTroop)){
            addAmount(populableTroop.getAmount());
        }
    }
    public int subtractAmount(int subtractAmount){
        if (subtractAmount < 0)
           return 0;
        int subtracted = 0;
        if(subtractAmount >= amount){
            subtracted = amount;
            amount = 0;
        }
        else{
            subtracted = subtractAmount;
            amount -= subtractAmount;
        }
        return subtracted;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PopulableTroop){
            return getUnitName().equalsIgnoreCase(((PopulableTroop) obj).getUnitName())
                    && getParty() == ((PopulableTroop) obj).getParty();
        }
        return super.equals(obj);
    }
}
