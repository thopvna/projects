package com.thopv.projects.ikariam.data.schema.houses;

import android.arch.persistence.room.Ignore;

public class Shipyard {
    private int level;

    public Shipyard(int level) {
        this.level = level;
    }
    @Ignore
    public Shipyard(){
        this.level = 1;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
