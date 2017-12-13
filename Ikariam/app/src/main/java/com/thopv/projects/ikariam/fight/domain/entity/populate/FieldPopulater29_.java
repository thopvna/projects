package com.thopv.projects.ikariam.fight.domain.entity.populate;

import com.thopv.projects.ikariam.R;

import java.util.Map;

/**
 * Created by thopv on 10/26/2017.
 */

public class FieldPopulater29_ extends FieldPopulater22_28 {
    @Override
    public Map<Integer, Integer> getFieldSpaces(){
        return SpacesInform.getField29Spaces();
    }
    @Override
    public int addBottomBlueShip(PopulableTroop populableTroop) {
        int result = super.addBottomBlueShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_left_two1, populableTroop);
        return result;
    }
    @Override
    public int addBottomRedShip(PopulableTroop populableTroop) {
        int result = super.addBottomRedShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_left_two2, populableTroop);
        return result;
    }
}
