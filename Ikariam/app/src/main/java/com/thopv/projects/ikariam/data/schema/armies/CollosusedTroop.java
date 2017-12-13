package com.thopv.projects.ikariam.data.schema.armies;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by thopv on 11/27/2017.
 */
@Entity(primaryKeys = {"startTime", "unitName"})
public class CollosusedTroop {

    @NonNull
    private String unitName;
    private int amount;
    private long startTime;
    private long endTime;
    private boolean isConfirm;

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public @NonNull String getUnitName() {
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

    public CollosusedTroop(@NonNull String unitName, int amount, long startTime, long endTime, boolean isConfirm) {
        this.unitName = unitName;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isConfirm = isConfirm;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CollosusedTroop){
            return startTime == ((CollosusedTroop) obj).getStartTime() && unitName.equalsIgnoreCase(((CollosusedTroop) obj).getUnitName());
        }
        return super.equals(obj);
    }

    public void merge(CollosusedTroop _collosusedTroop){
        if(equals(_collosusedTroop)){
            amount += _collosusedTroop.getAmount();
        }
    }
}
