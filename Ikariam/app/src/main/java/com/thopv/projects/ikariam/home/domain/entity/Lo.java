package com.thopv.projects.ikariam.home.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.data.schema.houses.House;


/**
 * Created by jlaotsezu on 25/11/2017.
 */
@Entity(primaryKeys = {"party"})
public class Lo {
    private int party;
    private int level;
    private long startTime;
    private long endTime;
    private long nextTimeAvailable;
    private float extraDamePercent;
    private int extraArmour;
    @Ignore
    private Lo( int party, int townHallLevel){
        level = calculateLevel(townHallLevel);
        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        nextTimeAvailable = calculateNextTimeAvailable();
        this.party = party;
        extraDamePercent = calculateExtraDamePercent();
        extraArmour = calculateExtraArmour();
    }
    @Ignore
    public static Lo create(@NonNull House house){
        return new Lo(house.getParty(), house.getTownHall().getLevel());
    }
    private static int calculateLevel(int townHallLevel){
        if(townHallLevel <= 7){
            return 1;
        }
        else if(townHallLevel <= 14){
            return 2;
        }
        else if(townHallLevel <= 21){
            return 3;
        }
        else if(townHallLevel <= 28){
            return 4;
        }
        else{
            return 5;
        }
    }
    public long calculateEndTime() {
        return startTime + 24 * 60 * 60 * 1000;
    }
    public long calculateNextTimeAvailable() {
        if(level == 1){
            return startTime + 7 * 24 * 60 * 60 * 1000;
        }
        else if(level == 2){
            return (long) (startTime + 3.5 * 24 * 60 * 60 * 1000);
        }
        else if(level == 3){
            return  (startTime + 56 * 60 * 60 * 1000);
        }
        else if(level == 4){
            return startTime + 30 * 60 * 60 * 1000;
        }
        else if(level == 5){
            return startTime + 16 * 60 * 60 * 1000;
        }

        return startTime;
    }
    private float calculateExtraDamePercent(){
        if(level == 1){
            return 0.1F;
        }
        else if(level == 2){
            return 0.1F;
        }
        else if(level == 3){
            return 0.15F;
        }
        else if(level == 4){
            return 0.15F;
        }
        else if(level == 5){
            return 0.2F;
        }
        return 0;
    }
    private int calculateExtraArmour(){
        if(level == 1){
            return 0;
        }
        else if(level == 2){
            return 1;
        }
        else if(level == 3){
            return 1;
        }
        else if(level == 4){
            return 2;
        }
        else if(level == 5){
            return 2;
        }
        return 0;
    }

    public Lo(int party, int level, long startTime, long endTime, long nextTimeAvailable, float extraDamePercent, int extraArmour) {
        this.party = party;
        this.level = level;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nextTimeAvailable = nextTimeAvailable;
        this.extraDamePercent = extraDamePercent;
        this.extraArmour = extraArmour;
    }

    public int getParty() {
        return party;
    }

    public void setParty( int party) {
        this.party = party;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public boolean isAffect(){
        return endTime > System.currentTimeMillis();
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getNextTimeAvailable() {
        return nextTimeAvailable;
    }

    public void setNextTimeAvailable(long nextTimeAvailable) {
        this.nextTimeAvailable = nextTimeAvailable;
    }

    public float getExtraDamePercent() {
        return extraDamePercent;
    }

    public void setExtraDamePercent(float extraDamePercent) {
        this.extraDamePercent = extraDamePercent;
    }

    public int getExtraArmour() {
        return extraArmour;
    }

    public void setExtraArmour(int extraArmour) {
        this.extraArmour = extraArmour;
    }
}
