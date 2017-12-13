package com.thopv.projects.ikariam.data.schema.houses;

import android.arch.persistence.room.Ignore;

public class TownHall {
    private int level;
    @Ignore
    public TownHall(){
        this.level = 1;
    }

    public TownHall(int level) {
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
