package com.thopv.projects.ikariam.data.source.mappers.populable_troops;

import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromPopulableToHome implements Mapper<PopulableTroop, HomeTroop> {
    @Override
    public HomeTroop map(PopulableTroop entity) {
        return new HomeTroop(entity.getParty(), entity.getUnitName(), entity.getAmount());
    }
}
