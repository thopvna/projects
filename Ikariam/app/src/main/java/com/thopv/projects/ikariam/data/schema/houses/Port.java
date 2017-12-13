package com.thopv.projects.ikariam.data.schema.houses;

import android.arch.persistence.room.Ignore;

/**
 * Created by thopv on 10/27/2017.
 */

public class Port {
    int level;
    @Ignore
    public Port(){

    }
    public Port(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
