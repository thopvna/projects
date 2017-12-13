package com.thopv.projects.ikariam.fight.domain.entity.populate;

import com.thopv.projects.ikariam.R;

import java.util.Map;

/**
 * Created by thopv on 10/26/2017.
 */

public class FieldPopulater22_28 extends FieldPopulater15_21 {
    @Override
    public int addTopBlueSupportShip(PopulableTroop populableTroop) {
        int result =  super.addTopBlueSupportShip(populableTroop);


        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_right_three1, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_left_three1, populableTroop);

        return result;
    }

    @Override
    public int addTopBlueShip(PopulableTroop populableTroop) {
        return super.addTopBlueShip(populableTroop);
    }

    @Override
    public int addMiddleBlueShip(PopulableTroop populableTroop) {
        return super.addMiddleBlueShip(populableTroop);
    }

    @Override
    public int addBottomBlueShip(PopulableTroop populableTroop) {
        int result = super.addBottomBlueShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_right_two1, populableTroop);

        return result;
    }

    @Override
    public int addBottomSupportRightBlueShip(PopulableTroop populableTroop) {
        int result = super.addBottomSupportRightBlueShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_support_right_two1, populableTroop);

        return result;
    }

    @Override
    public int addBottomSupportLeftBlueShip(PopulableTroop populableTroop) {
        int result = super.addBottomSupportLeftBlueShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_support_left_two1, populableTroop);
        return result;
    }

    @Override
    public int addTopRedSupportShip(PopulableTroop populableTroop) {
        int result =  super.addTopRedSupportShip(populableTroop);


        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_right_three2, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_left_three2, populableTroop);

        return result;
    }

    @Override
    public int addTopRedShip(PopulableTroop populableTroop) {
        return super.addTopRedShip(populableTroop);
    }

    @Override
    public int addMiddleRedShip(PopulableTroop populableTroop) {
        return super.addMiddleRedShip(populableTroop);
    }

    @Override
    public int addBottomRedShip(PopulableTroop populableTroop) {
        int result = super.addBottomRedShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_right_two2, populableTroop);

        return result;
    }

    @Override
    public int addBottomSupportRightRedShip(PopulableTroop populableTroop) {
        int result = super.addBottomSupportRightRedShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_support_right_two2, populableTroop);

        return result;
    }
    @Override
    public Map<Integer, Integer> getFieldSpaces(){
        return SpacesInform.getField22Spaces();
    }
    @Override
    public int addBottomSupportLeftRedShip(PopulableTroop populableTroop) {
        int result = super.addBottomSupportLeftRedShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_support_left_two2, populableTroop);
        return result;
    }
}
