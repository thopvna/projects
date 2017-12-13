package com.thopv.projects.ikariam.home.domain.criterias;

import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/27/2017.
 */

public class CollosusedTroopsStartAt implements Repository.Criteria {
    private long startTime;

    public long getStartTime() {
        return startTime;
    }

    public CollosusedTroopsStartAt(long startTime) {
        this.startTime = startTime;
    }
}
