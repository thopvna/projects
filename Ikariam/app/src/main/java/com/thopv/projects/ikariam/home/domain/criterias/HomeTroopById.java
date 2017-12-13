package com.thopv.projects.ikariam.home.domain.criterias;

import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by jlaotsezu on 25/11/2017.
 */

public class HomeTroopById implements Repository.Criteria {
    private int party;
    private String unitName;

    public HomeTroopById(int party, String unitName) {
        this.party = party;
        this.unitName = unitName;
    }

    public int getParty() {
        return party;
    }

    public String getUnitName() {
        return unitName;
    }
}
