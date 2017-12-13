package com.thopv.projects.ikariam.data.source.mappers.populable_troops;

import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromListHomeToListPopulable implements Mapper<List<HomeTroop>, List<PopulableTroop>> {
    @Override
    public List<PopulableTroop> map(List<HomeTroop> homeTroops) {
        List<PopulableTroop> populableTroops = new LinkedList<>();
        for(HomeTroop homeTroop : homeTroops){
            PopulableTroop populableTroop = new PopulableTroop(homeTroop.getUnitName()
                    , homeTroop.getAmount()
                    , homeTroop.getParty()
            );
            populableTroops.add(populableTroop);
        }
        return populableTroops;
    }
}
