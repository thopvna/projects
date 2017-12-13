package com.thopv.projects.ikariam.home.presentation.mapper;

import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class HomeTroopListMapper implements ListMapper<HomeTroop, BaseTroop> {

    @Override
    public List<BaseTroop> map(List<HomeTroop> homeTroops) {
        List<BaseTroop> list = new LinkedList<>();
        for(HomeTroop homeTroop : homeTroops){
            list.add(new BaseTroop(homeTroop.getUnitName(), homeTroop.getAmount()));
        }
        return list;
    }

}
