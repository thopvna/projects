package com.thopv.projects.ikariam.home.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.data.schema.houses.House;

/**
 * Created by thopv on 11/27/2017.
 */
@Entity
public class Collosus {
    @PrimaryKey
    private int party;
    private int level;
    private long startTime;
    private long endTime;
    private long nextTimeAvailable;
    private float percent;
    @Ignore
    private Collosus( int party, int townHallLevel){
        level = calculateLevel(townHallLevel);
        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        nextTimeAvailable = calculateNextTimeAvailable();
        this.party = party;
        percent = calculatePercent();
    }
    @Ignore
    public static Collosus create(@NonNull House house){
        return new Collosus(house.getParty(), house.getTownHall().getLevel());
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
        if(level == 1){
            return (long) (startTime + 4.5F * 60 * 60 * 1000);
        }
        else if(level == 2){
            return (startTime + 5 * 60 * 60 * 1000);
        }
        else if(level == 3){
            return (long) (startTime + 5.5F * 60 * 1000);
        }
        else if(level == 4){
            return startTime + 6 * 60 * 60 * 1000;
        }
        else if(level == 5){
            return startTime + 7 * 60 * 60 * 1000;
        }

        return startTime;
    }
    public long calculateNextTimeAvailable() {
        if(level == 1){
            return startTime + 3 * 24 * 60 * 60 * 1000;
        }
        else if(level == 2){
            return (startTime + 3 * 24 * 60 * 60 * 1000);
        }
        else if(level == 3){
            return (long) (startTime + 1.5F  * 24 * 60 * 60 * 1000);
        }
        else if(level == 4){
            return (startTime + 24 *  60 * 60 * 1000);
        }
        else if(level == 5){
            return startTime + 18 * 60 * 60 * 1000L;
        }

        return startTime;
    }
    public float calculatePercent(){
        if(level == 1){
            return 0.1F;
        }
        else if(level == 2){
            return 0.2F;
        }
        else if(level == 3){
            return 0.3F;
        }
        else if(level == 4){
            return 0.5F;
        }
        else if(level == 5){
            return 1.0F;
        }

        return 0;
    }
    public Collosus(int party, int level, long startTime, long endTime, long nextTimeAvailable, float percent) {
        this.party = party;
        this.level = level;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nextTimeAvailable = nextTimeAvailable;
        this.percent = percent;
    }

    public int apply(int amount){
        if(amount == 0)
            return 0;
        return  (int)(amount * (1 - percent));
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
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

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getNextTimeAvailable() {
        return nextTimeAvailable;
    }

    public void setNextTimeAvailable(long nextTimeAvailable) {
        this.nextTimeAvailable = nextTimeAvailable;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
