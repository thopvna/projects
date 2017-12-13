package com.thopv.projects.ikariam.data.schema.armies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.util.Log;

import junit.framework.Assert;

/**
 * Created by thopv on 11/14/2017.
 */
@Entity(primaryKeys = {"unitName", "startTime"})
public class MovingTroop {
    @NonNull
    private String unitName;
    private int amount;
    private long startTime;
    private long endTime;

    private boolean isConfirm;

    private boolean isReturn;

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public MovingTroop(@NonNull String unitName, int amount, long startTime, long endTime, boolean isConfirm, boolean isReturn) {
        this.unitName = unitName;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isConfirm = isConfirm;
        this.isReturn = isReturn;
    }

    public void merge(MovingTroop movingTroop){
        Assert.assertTrue(movingTroop.getUnitName().equalsIgnoreCase(getUnitName()));
        if(movingTroop.getAmount() == 0)
            return;
        amount += movingTroop.getAmount();
    }

    public void setToHouseId( int party) {
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Ignore
    public void info(){
        Log.e("MovingTroop: ", "HomeTroop Unit name: " + getUnitName() + ", Amount = " + getAmount()
                +", is confirm: " + isConfirm()
        );
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MovingTroop)
            return getUnitName().equalsIgnoreCase(((MovingTroop) obj).getUnitName())
                    && getStartTime() == ((MovingTroop) obj).getStartTime()
                    ;
        return super.equals(obj);
    }

    public static class Builder {
        private String unitName;
        private int amount;
        private long startTime;
        private long endTime;
        private boolean isReturn;

        public Builder setReturn(boolean aReturn) {
            isReturn = aReturn;
            return this;
        }

        public Builder setUnitName(String unitName) {
            this.unitName = unitName;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public MovingTroop build() {
            return new MovingTroop(unitName, amount, startTime, endTime, false, isReturn);
        }
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
}
