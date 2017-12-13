package com.thopv.projects.ikariam.home.domain.criterias;

import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/21/2017.
 */
public class MovingTroopsStartAt implements Repository.Criteria {
    private long startTime;

    public MovingTroopsStartAt(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
