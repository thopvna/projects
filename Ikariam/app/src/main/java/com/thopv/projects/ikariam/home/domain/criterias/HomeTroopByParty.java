package com.thopv.projects.ikariam.home.domain.criterias;

import com.thopv.projects.ikariam.data.source.repositories.Repository;

/**
 * Created by thopv on 11/21/2017.
 */
public class HomeTroopByParty implements Repository.Criteria {
    private int party;

    public int getParty() {
        return party;
    }

    public HomeTroopByParty(int party) {
        this.party = party;
    }
}
