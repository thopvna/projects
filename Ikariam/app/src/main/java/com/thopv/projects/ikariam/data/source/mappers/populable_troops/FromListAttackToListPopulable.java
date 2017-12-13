package com.thopv.projects.ikariam.data.source.mappers.populable_troops;

import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromListAttackToListPopulable implements Mapper<List<AttackTroop>, List<PopulableTroop>> {
    @Override
    public List<PopulableTroop> map(List<AttackTroop> attackTroops) {
        List<PopulableTroop> populableTroops = new LinkedList<>();
        for(AttackTroop attackTroop : attackTroops){
            PopulableTroop populableTroop = new PopulableTroop(attackTroop.getUnitName()
                    , attackTroop.getAmount()
                    , PartyUtils.getBlueParty()
            );
            populableTroops.add(populableTroop);
        }
        return populableTroops;
    }
}
