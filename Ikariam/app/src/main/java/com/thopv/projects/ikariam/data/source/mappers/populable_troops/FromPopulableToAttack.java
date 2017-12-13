package com.thopv.projects.ikariam.data.source.mappers.populable_troops;

import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromPopulableToAttack implements Mapper<PopulableTroop, AttackTroop> {
    @Override
    public AttackTroop map(PopulableTroop entity) {
        return new AttackTroop.Builder()
                .setUnitName(entity.getUnitName())
                .setAmount(entity.getAmount())
                .build();
    }
}
