package com.thopv.projects.ikariam.home.domain.criterias;

import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/21/2017.
 */

public class AttackTroopById implements Repository.Criteria {
    private String unitName;

    public AttackTroopById(String unitName) {
        this.unitName = unitName;
    }


    public String getUnitName() {
        return unitName;
    }
}
