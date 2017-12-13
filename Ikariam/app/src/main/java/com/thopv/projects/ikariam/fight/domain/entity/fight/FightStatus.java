package com.thopv.projects.ikariam.fight.domain.entity.fight;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thopv on 11/29/2017.
 */

@Entity
public class FightStatus {
    @PrimaryKey
    private long   id = 1;
    private long startTime;
    private boolean isFighting;
    private int turn;

    public FightStatus(long id, long startTime, boolean isFighting, int turn) {
        this.id = id;
        this.startTime = startTime;
        this.isFighting = isFighting;
        this.turn = turn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTurn() {
        return turn;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }
    synchronized public void newTurn(){
        turn++;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isFighting() {
        return isFighting;
    }
}
