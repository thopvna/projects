package com.thopv.projects.ikariam.home.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thopv.projects.ikariam.data.schema.houses.House;


/**
 * Created by jlaotsezu on 25/11/2017.
 */
@Entity(primaryKeys = {"party"})
public class Whale {
    private int party;
    private int level;
    private long startTime;
    private long endTime;
    private long nextTimeAvailable;
    private float extraPercent;
    @Ignore
    private Whale(int party, int townHallLevel){
        level = calculateLevel(townHallLevel);
        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        nextTimeAvailable = calculateNextTimeAvailable();
        this.party = party;
        extraPercent = calculateExtraPercent();
    }
    @Ignore
    public static Whale create(@NonNull House house){
        return new Whale(house.getParty(), house.getTownHall().getLevel());
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
    private long calculateEndTime(){
        return startTime + 4 * 1000 * 60 * 60;
    }
    private long calculateNextTimeAvailable() {
        if(level == 1){
            return startTime + 24 * 60 * 60 * 1000;
        }
        else if(level == 2){
            return startTime + 12 * 60 * 60 * 1000;
        }
        else if(level == 3){
            return startTime + 8 * 60 * 60 * 1000;
        }
        else if(level == 4){
            return startTime + 6 * 60 * 60 * 1000;
        }
        else if(level == 5){
            return startTime + 4 * 60 * 60 * 1000;
        }
        return startTime;
    }

    private float calculateExtraPercent(){
        if(level == 1){
            return 1.1F;
        }
        else if(level == 2){
            return 1.3F;
        }
        else if(level == 3){
            return 1.5F;
        }
        else if(level == 4){
            return 1.7F;
        }
        else{
            return 2F;
        }
    }
    public long apply(long startTime, long endTime){
        long currentTimeInMillis = System.currentTimeMillis();
        if(endTime < currentTimeInMillis){
            return endTime;
        }
        else {
            long wentTime = currentTimeInMillis - startTime;
            long remain = endTime - currentTimeInMillis;
            long afterEndTime =  startTime + wentTime + (long)(remain / extraPercent);
            Log.e(getClass().getSimpleName(), "Before End time: " + endTime);
            Log.e(getClass().getSimpleName(), "Went time: " + wentTime);
            Log.e(getClass().getSimpleName(), "Remain time: " + remain);
            Log.e(getClass().getSimpleName(), "Time reserve: " + remain / extraPercent);
            Log.e(getClass().getSimpleName(), "After End time: " + afterEndTime);
            return afterEndTime;
        }
    }
    public Whale( int party, int level, long startTime, long endTime, long nextTimeAvailable, float extraPercent) {
        this.party = party;
        this.level = level;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nextTimeAvailable = nextTimeAvailable;
        this.extraPercent = extraPercent;
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

    public int getParty() {
        return party;
    }

    public void setParty( int party) {
        this.party = party;
    }

    public float getExtraPercent() {
        return extraPercent;
    }

    public void setExtraPercent(float extraPercent) {
        this.extraPercent = extraPercent;
    }
}
